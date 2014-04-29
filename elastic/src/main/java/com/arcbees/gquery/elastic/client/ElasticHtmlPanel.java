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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HTMLPanel;

import static com.google.gwt.query.client.GQuery.$;

public class ElasticHtmlPanel extends HTMLPanel {
    private ElasticOption options = new ElasticOption();

    public ElasticHtmlPanel(String html) {
        super(html);
    }

    public ElasticHtmlPanel(SafeHtml safeHtml) {
        super(safeHtml);
    }

    public ElasticHtmlPanel(String tag, String html) {
        super(tag, html);
    }

    public ElasticOption getOptions() {
        return options;
    }

    public void setOptions(ElasticOption options) {
        this.options = options;

        if (isAttached()) {
            update();
        }
    }

    /**
     * Set the minimal width in px for a column and update the layout accordingly.
     * Default: 250
     */
    public void setMinimumColumnWidth(int columnWidth) {
        setMinimumColumnWidth(columnWidth, true);
    }

    /**
     * Set the minimal width in px for a column and update the layout if <code>updateLayout</code> equals to
     * <code>true</code>
     * Default: 250
     */
    public void setMinimumColumnWidth(int columnWidth, boolean updateLayout) {
        options.setMinimumColumWidth(columnWidth);

        if (updateLayout) {
            update();
        }
    }

    /**
     * Set the minimal number of column to display and update the layout accordingly.
     * Default: 1
     */
    public void setMinimalNumberOfColumn(int minNumberColumn) {
        setMinimalNumberOfColumn(minNumberColumn, true);
    }

    /**
     * Set the minimal number of column to display and update the layout if <code>updateLayout</code> equals to
     * <code>true</code>
     * Default: 1
     */
    public void setMinimalNumberOfColumn(int minNumberColumn, boolean updateLayout) {
        options.setMinimalNumberOfColumn(minNumberColumn);

        if (updateLayout) {
            update();
        }
    }

    /**
     * Set the maximum number of columns to display and update the layout accordingly.
     * Default: infinity
     */
    public void setMaximalNumberOfColumn(int maxNumberColumn) {
        setMaximalNumberOfColumn(maxNumberColumn, true);
    }

    /**
     * Set the maximum number of columns to display and update the layout if <code>updateLayout</code> equals to
     * <code>true</code>
     * Default: infinity
     */
    public void setMaximalNumberOfColumn(int maxNumberColumn, boolean updateLayout) {
        options.setMaximalNumberOfColumn(maxNumberColumn);

        if (updateLayout) {
            update();
        }
    }

    /**
     * Set the space between each columns in px and update the layout accordingly. If you want to set outer margin,
     * just set padding on the items container.
     * Default: 10px
     */
    public void setInnerColumnMargin(int innerMargin) {
        setInnerColumnMargin(innerMargin, true);
    }

    /**
     * Set the space between each columns in px and update the layout if <code>updateLayout</code> equals to
     * <code>true</code> If you want to set outer margin, just set padding on the items container.
     * Default: 10px
     */
    public void setInnerColumnMargin(int innerMargin, boolean updateLayout) {
        options.setInnerColumnMargin(innerMargin);

        if (updateLayout) {
            update();
        }
    }

    /**
     * Set the space between each rows in px and update the layout accordingly. If you want to set outer margin,
     * just set padding on the items container.
     * Default: 10px
     */
    public void setInnerRowMargin(int innerMargin) {
        setInnerRowMargin(innerMargin, true);
    }

    /**
     * Set the space between each rows in px and update the layout if <code>updateLayout</code> equals to
     * <code>true</code> If you want to set outer margin, just set padding on the items container.
     * Default: 10px
     */
    public void setInnerRowMargin(int innerMargin, boolean updateLayout) {
        options.setInnerRowMargin(innerMargin);

        if (updateLayout) {
            update();
        }
    }

    /**
     * In autoResize mode, the plugin will automatically recompute the layout when the user is resizing the page.
     * Default: true
     */
    public void setAutoResize(boolean autoResize) {
        options.setAutoResize(autoResize);
    }

    public void update() {
        if (isAttached()) {
            elastic().updateLayout();
        }
    }

    @Override
    protected void onLoad() {
        // we need to be sure that all style are injected into the dom before to call the plugin
        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                elastic().elastic(options);
            }
        });
    }

    @Override
    protected void onUnload() {
        elastic().destroy();
    }

    private Elastic elastic() {
        return $(this).as(Elastic.Elastic);
    }
}
