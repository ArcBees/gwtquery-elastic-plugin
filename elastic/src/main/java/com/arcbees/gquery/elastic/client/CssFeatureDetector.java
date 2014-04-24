/**
 * Copyright 2014 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gquery.elastic.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

public class CssFeatureDetector {
    /**
     * Implementation for ie6-8
     */
    public static class CssFeatureDetectorTrident extends CssFeatureDetector {
        @Override
        public String getPrefixedCalc() {
            return null;
        }

        @Override
        public String getPrefixedTransform() {
            return null;
        }

        @Override
        public String getPrefixedTranslate3d() {
            return null;
        }
    }

    private static final String[] PREFIXES = {"", "-webkit-", "-moz-", "-ms-", "-o-"};
    private static final String TRANSFORM = "transform";
    private static final String TRANSLATE_3D = "translate3d";
    private static final String CALC = "calc";

    private String prefixedTransform;
    private boolean prefixedTransformComputed;
    private String prefixedTranslate3d;
    private boolean prefixedTranslate3dComputed;
    private String prefixedCalc;
    private boolean prefixedCalcComputed;

    public String getPrefixedTransform() {
        if (prefixedTransformComputed) {
            return prefixedTransform;
        }
        prefixedTransformComputed = true;

        for (String prefix : PREFIXES) {
            String transform = prefix + TRANSFORM;
            String property = getDocumentComputedStyle(transform);
            if (property != null && !property.isEmpty()) {
                prefixedTransform = transform;
                return transform;
            }
        }

        return null;
    }

    public String getPrefixedCalc() {
        if (prefixedCalcComputed) {
            return prefixedCalc;
        }
        prefixedCalcComputed = true;

        String transform = getPrefixedTransform();
        String translate3d = getPrefixedTranslate3d();

        if (transform == null || translate3d == null) {
            return null;
        }

        Element div = DOM.createDiv();

        for (String prefix : PREFIXES) {
            String calc = prefix + CALC;
            String lineStyle = transform + ":" + translate3d + "(" + calc + "(1px)," + calc + "(1px)," +
                    "" + calc + "(1px));";
            if (setStyle(div, lineStyle)) {
                prefixedCalc = calc;
                return calc;
            }
        }

        return null;
    }

    public String getPrefixedTranslate3d() {
        if (prefixedTranslate3dComputed) {
            return prefixedTranslate3d;
        }
        prefixedTranslate3dComputed = true;

        String transform = getPrefixedTransform();

        if (transform == null) {
            return null;
        }

        Element div = DOM.createDiv();

        for (String prefix : PREFIXES) {
            String translate3d = prefix + TRANSLATE_3D;
            String lineStyle = transform + ":" + translate3d + "(1px, 0, 0);";
            if (setStyle(div, lineStyle)) {
                prefixedTranslate3d = translate3d;
                return translate3d;
            }
        }

        return null;
    }

    private native boolean setStyle(Element e, String style) /*-{
        e.style.cssText = style;
        return !!e.style.length;
    }-*/;

    private native String getDocumentComputedStyle(String property) /*-{
        try {
            var cStyle = $doc.defaultView.getComputedStyle($doc.body, '');
            return cStyle && cStyle.getPropertyValue ? cStyle.getPropertyValue(property) : null;
        } catch (e) {
            return null;
        }
    }-*/;
}
