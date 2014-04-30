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

    /**
     * Set the minimal width in px for a column and update the layout accordingly.
     * <p>
     * Default: {@value com.arcbees.gquery.elastic.client.ElasticOption#MINIMUM_COLUMN_WIDTH_DEFAULT}
     */
    public void setMinimumColumnWidth(int columnWidth) {
        setMinimumColumnWidth(columnWidth, true);
    }

    /**
     * Set the minimal width in px for a column and update the layout if <code>updateLayout</code> equals to
     * <code>true</code>
     * <p>
     * Default: {@value com.arcbees.gquery.elastic.client.ElasticOption#MINIMUM_COLUMN_WIDTH_DEFAULT}
     */
    public void setMinimumColumnWidth(int columnWidth, boolean updateLayout) {
        options.setMinimumColumWidth(columnWidth);

        if (updateLayout) {
            update();
        }
    }

    /**
     * Set the minimal number of columns to display and update the layout accordingly.
     * <p>
     * Default: {@value com.arcbees.gquery.elastic.client.ElasticOption#MINIMUM_COLUMN_DEFAULT}
     */
    public void setMinimalNumberOfColumn(int minNumberColumn) {
        setMinimalNumberOfColumn(minNumberColumn, true);
    }

    /**
     * Set the minimal number of columns to display and update the layout if <code>updateLayout</code> equals to
     * <code>true</code>
     * <p>
     * Default: {@value com.arcbees.gquery.elastic.client.ElasticOption#MINIMUM_COLUMN_DEFAULT}
     */
    public void setMinimalNumberOfColumn(int minNumberColumn, boolean updateLayout) {
        options.setMinimalNumberOfColumn(minNumberColumn);

        if (updateLayout) {
            update();
        }
    }

    /**
     * Set the maximum number of columns to display and update the layout accordingly.
     * <p>
     * Default: {@value java.lang.Integer#MAX_VALUE}
     */
    public void setMaximalNumberOfColumn(int maxNumberColumn) {
        setMaximalNumberOfColumn(maxNumberColumn, true);
    }

    /**
     * Set the maximum number of columns to display and update the layout if <code>updateLayout</code> equals to
     * <code>true</code>
     * <p>
     * Default: {@value java.lang.Integer#MAX_VALUE}
     */
    public void setMaximalNumberOfColumn(int maxNumberColumn, boolean updateLayout) {
        options.setMaximalNumberOfColumn(maxNumberColumn);

        if (updateLayout) {
            update();
        }
    }

    /**
     * Set the space between each column in px and update the layout accordingly. If you want to set outer margin,
     * just set padding on the items container.
     * <p>
     * Default: {@value com.arcbees.gquery.elastic.client.ElasticOption#INNER_COLUMN_MARGIN_DEFAULT}
     */
    public void setInnerColumnMargin(int innerMargin) {
        setInnerColumnMargin(innerMargin, true);
    }

    /**
     * Set the space between each column in px and update the layout if <code>updateLayout</code> equals to
     * <code>true</code> If you want to set outer margin, just set padding on the items container.
     * <p>
     * Default: {@value com.arcbees.gquery.elastic.client.ElasticOption#INNER_COLUMN_MARGIN_DEFAULT}
     */
    public void setInnerColumnMargin(int innerMargin, boolean updateLayout) {
        options.setInnerColumnMargin(innerMargin);

        if (updateLayout) {
            update();
        }
    }

    /**
     * Set the space between each row in px and update the layout accordingly. If you want to set outer margin,
     * just set padding on the items container.
     * <p>
     * Default: {@value com.arcbees.gquery.elastic.client.ElasticOption#INNER_ROW_MARGIN_DEFAULT}
     */
    public void setInnerRowMargin(int innerMargin) {
        setInnerRowMargin(innerMargin, true);
    }

    /**
     * Set the space between each row in px and update the layout if <code>updateLayout</code> equals to
     * <code>true</code> If you want to set outer margin, just set padding on the items container.
     * <p>
     * Default: {@value com.arcbees.gquery.elastic.client.ElasticOption#INNER_ROW_MARGIN_DEFAULT}
     */
    public void setInnerRowMargin(int innerMargin, boolean updateLayout) {
        options.setInnerRowMargin(innerMargin);

        if (updateLayout) {
            update();
        }
    }

    /**
     * In autoResize mode, the plugin will automatically recompute the layout when the user is resizing the page.
     * <p>
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
        // we need to be sure that all styles are injected into the dom before calling the plugin
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
