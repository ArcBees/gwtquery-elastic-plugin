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
    private int minimumColumnWidth;
    private int minNumberColumn;
    private int maxNumberColumn;
    private int innerColumnMargin;
    private int innerRowMargin;
    private boolean autoResize;

    public ElasticOption() {
        setDefaults();
    }

    /**
     * Set the minimal width in px for a column.
     * Default: 250
     */
    public ElasticOption setMinimumColumWidth(int columnWidth) {
        this.minimumColumnWidth = columnWidth;
        return this;
    }

    public int getMinimumColumnWidth() {
        return minimumColumnWidth;
    }

    public int getMinimalNumberOfColumn() {
        return minNumberColumn;
    }

    /**
     * Set the minimal number of column to display.
     * Default: 1
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
     * Default: infinity
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
     * Default: 10px
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
     * Default: 10px
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
        minimumColumnWidth = 250;
        minNumberColumn = 1;
        maxNumberColumn = Integer.MAX_VALUE;
        innerColumnMargin = 10;
        innerRowMargin = 10;
        autoResize = true;
    }
}
