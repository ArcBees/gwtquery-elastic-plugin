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

public class ElasticOption {
    static int MINIMUM_COLUMN_DEFAULT = 1;
    static int MAXIMUM_COLUMN_WIDTH_DEFAULT = -1;
    static int MINIMUM_COLUMN_WIDTH_DEFAULT = 250;
    static int INNER_COLUMN_MARGIN_DEFAULT = 10;
    static int INNER_ROW_MARGIN_DEFAULT = 10;

    private int minimumColumnWidth;
    private int maximumColumnWidth;
    private int minNumberColumn;
    private int maxNumberColumn;
    private int innerColumnMargin;
    private int innerRowMargin;
    private boolean autoResize;

    public ElasticOption() {
        setDefaults();
    }

    /**
     * Set the minimal width in px for a column. This value is used to know how many column can be display inside
     * the container.
     * <p/>
     * Default: {@value #MINIMUM_COLUMN_WIDTH_DEFAULT}
     */
    public ElasticOption setMinimumColumWidth(int columnWidth) {
        this.minimumColumnWidth = columnWidth;
        return this;
    }


    public int getMinimumColumnWidth() {
        return minimumColumnWidth;
    }

    /**
     * Set the maximal width in px for a column. When the column width reach this value, the width of the column is not
     * increased even if the width of the container increases.
     * <p/>
     * If you want to the width of the columns doesn't increase with the width of the container, set the
     * <code>maximumWidth</code> equals to the <code>minimumWidth</code>
     * <p/>
     * If you want that the columns take all the available space in the container and increase their width according to
     * the width of the container, set the <code>maximumWidth</code> to -1.
     * <p/>
     * If the maximumColumnWidth value is be lower than the value returned by {@link #getMinimumColumnWidth()}, this
     * last is taken in order to calculate the maximum width of the columns
     * Default: {@value #MAXIMUM_COLUMN_WIDTH_DEFAULT}
     */
    public void setMaximumColumnWidth(int maximumColumnWidth) {
        this.maximumColumnWidth = maximumColumnWidth;
    }

    public int getMaximumColumnWidth() {
        return maximumColumnWidth;
    }

    public int getMinimalNumberOfColumn() {
        return minNumberColumn;
    }

    /**
     * Set the minimal number of columns to display.
     * Default: {@value #MINIMUM_COLUMN_DEFAULT}
     */
    public ElasticOption setMinimalNumberOfColumn(int minNumberColumn) {
        this.minNumberColumn = minNumberColumn;
        return this;
    }

    public int getMaximalNumberOfColumn() {
        return maxNumberColumn;
    }

    /**
     * Set the maximum number of columns to display.
     * Default: {@value java.lang.Integer#MAX_VALUE}
     */
    public ElasticOption setMaximalNumberOfColumn(int maxNumberColumn) {
        this.maxNumberColumn = maxNumberColumn;
        return this;
    }

    public int getInnerColumnMargin() {
        return innerColumnMargin;
    }

    /**
     * Set the space between each columns in px. If you want to set outer margin,
     * just set padding on the items container.
     * Default: {@value #INNER_COLUMN_MARGIN_DEFAULT}
     */
    public ElasticOption setInnerColumnMargin(int innerMargin) {
        this.innerColumnMargin = innerMargin;
        return this;
    }

    public int getInnerRowMargin() {
        return innerRowMargin;
    }

    /**
     * Set the space between each rows in px. If you want to set outer margin,
     * just set padding on the items container.
     * Default: {@value #INNER_ROW_MARGIN_DEFAULT}
     */
    public ElasticOption setInnerRowMargin(int innerMargin) {
        this.innerRowMargin = innerColumnMargin;
        return this;
    }

    public boolean isAutoResize() {
        return autoResize;
    }

    /**
     * In autoResize mode, the plugin will automatically recompute the layout when the user is resizing the page.
     * Default: true
     */
    public ElasticOption setAutoResize(boolean autoResize) {
        this.autoResize = autoResize;
        return this;
    }

    private void setDefaults() {
        minimumColumnWidth = MINIMUM_COLUMN_WIDTH_DEFAULT;
        maximumColumnWidth = MAXIMUM_COLUMN_WIDTH_DEFAULT;
        minNumberColumn = MINIMUM_COLUMN_DEFAULT;
        maxNumberColumn = Integer.MAX_VALUE;
        innerColumnMargin = INNER_COLUMN_MARGIN_DEFAULT;
        innerRowMargin = INNER_ROW_MARGIN_DEFAULT;
        autoResize = true;
    }
}
