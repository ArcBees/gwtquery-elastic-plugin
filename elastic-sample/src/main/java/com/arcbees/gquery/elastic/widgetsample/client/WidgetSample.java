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

package com.arcbees.gquery.elastic.widgetsample.client;

import java.util.Random;

import com.arcbees.gquery.elastic.client.Elastic;
import com.arcbees.gquery.elastic.client.ElasticHtmlPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class WidgetSample implements EntryPoint {
    interface WidgetSampleUiBinder extends UiBinder<Widget, WidgetSample> {
    }

    private static WidgetSampleUiBinder uiBinder = GWT.create(WidgetSampleUiBinder.class);
    private static int ITEMS_INCREMENT = 10;
    private static String SIMPLE_CONTENT = ". Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor" +
            " in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
            "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum";

    private static String DOUBLE_CONTENT = ". Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce in " +
            "dictum " +
            "nunc. Phasellus sollicitudin pretium diam id viverra. In ut sem vitae augue imperdiet dictum. Nam at " +
            "vulputate ante. Nam ac sapien ante. Cras et metus sed augue hendrerit venenatis vel eget urna. Donec at " +
            "neque nisl.<br/>" +
            "Proin vitae diam lectus. Sed mauris velit, pretium vitae erat vitae, " +
            "tincidunt egestas odio. Sed a imperdiet magna, ut tempor arcu. Vivamus eu turpis neque. Phasellus eget " +
            "magna id augue cursus suscipit non vel dolor. Etiam sed posuere urna. Nulla a nibh sit amet lorem " +
            "molestie molestie sed sed eros. Donec gravida risus vel justo tincidunt ultricies.";
    private final Random rand = new Random();
    @UiField
    ScrollPanel scrollPanel;
    @UiField
    ElasticHtmlPanel htmlPanel;
    private int lastScrollPosition;
    private int singleItemNumber;
    private int itemsCounter = 10;

    public WidgetSample() {
        uiBinder.createAndBindUi(this);

        addNewItems(20);
    }

    @Override
    public void onModuleLoad() {
        RootPanel.get("main").add(scrollPanel);
    }

    @UiHandler("scrollPanel")
    void onScroll(ScrollEvent event) {
        int oldScrollPos = lastScrollPosition;
        lastScrollPosition = scrollPanel.getVerticalScrollPosition();
        if (oldScrollPos >= lastScrollPosition) {
            return;
        }

        int maxScrollTop = scrollPanel.getWidget().getOffsetHeight()
                - scrollPanel.getOffsetHeight() - 50;
        if (lastScrollPosition >= maxScrollTop) {
            addNewItems(ITEMS_INCREMENT);
        }
    }

    private void addNewItems(int itemsNumber) {
        for (int i = 0; i < itemsNumber; i++) {
            addItem();
        }
    }

    private void addItem() {
        HTML html = new HTML();

        if (singleItemNumber > 5 && rand.nextBoolean() && rand.nextBoolean()) {
            // add an item that span 2 column
            html.setHTML(itemsCounter + DOUBLE_CONTENT);
            html.getElement().setAttribute(Elastic.SPAN_ATTRIBUTE, "2");
            singleItemNumber = 0;
        } else {
            html.setHTML(itemsCounter + SIMPLE_CONTENT);
            singleItemNumber++;
        }

        htmlPanel.add(html);
        itemsCounter++;
    }
}
