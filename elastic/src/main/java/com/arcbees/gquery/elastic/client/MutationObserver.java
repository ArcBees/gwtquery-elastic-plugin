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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.query.client.GQuery;

public class MutationObserver {
    public static interface DomMutationCallback {
        public void onNodesRemoved(JsArray<Node> removedNodes);

        public void onNodesInserted(JsArray<Node> addedNodes, Node nextSibling);

        public void onNodesAppended(JsArray<Node> addedNodes);
    }

    protected static class NativeMutationObserver extends JavaScriptObject {
        static native NativeMutationObserver create(DomMutationCallback callback) /*-{
            return new MutationObserver(function (mutations) {
                var mutationsCount = mutations.length;
                for (var i = 0; i < mutationsCount; i++) {
                    if (mutations[i].removedNodes.length) {
                        callback.@com.arcbees.gquery.elastic.client.MutationObserver.DomMutationCallback::onNodesRemoved(Lcom/google/gwt/core/client/JsArray;)(mutations[i].removedNodes);
                    }

                    if (mutations[i].addedNodes.length) {
                        var nodes = mutations[i].addedNodes;
                        if (mutations[i].nextSibling) {
                            callback.@com.arcbees.gquery.elastic.client.MutationObserver.DomMutationCallback::onNodesInserted(Lcom/google/gwt/core/client/JsArray;Lcom/google/gwt/dom/client/Node;)(nodes, mutations[i].nextSibling)
                        } else {
                            callback.@com.arcbees.gquery.elastic.client.MutationObserver.DomMutationCallback::onNodesAppended(Lcom/google/gwt/core/client/JsArray;)(nodes);
                        }
                    }
                }
            });
        }-*/;

        static native boolean exist()  /*-{
            return !!$wnd.MutationObserver;
        }-*/;


        protected NativeMutationObserver() {
        }

        final native void observe(Element e) /*-{
            // only observe childList modification
            this.observe(e, {attributes: false, childList: true, characterData: false});
        }-*/;

        final native void disconnect() /*-{
            this.disconnect();
        }-*/;
    }

    public static boolean isSupported() {
        return !GQuery.browser.ie6 && !GQuery.browser.ie8 && !GQuery.browser.ie9 && NativeMutationObserver.exist();
    }

    private NativeMutationObserver mutator;

    public MutationObserver(DomMutationCallback callback) {
        mutator = NativeMutationObserver.create(callback);
    }

    public void observe(Element e) {
        mutator.observe(e);
    }

    public void disconnect() {
        mutator.disconnect();
    }
}
