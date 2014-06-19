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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.arcbees.gquery.elastic.client.MutationObserver.DomMutationCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.HandlerRegistration;

import static java.lang.Math.max;
import static java.lang.Math.min;

import static com.google.gwt.query.client.GQuery.$;

public class ElasticImpl {
    /**
     * Object where we store some style info in order to avoid browser reflows
     */
    private static class StyleInfo {
        int span;
        double marginRight;
        double marginLeft;
        double borderTopWidth;
        double borderBottomWidth;
        double marginTop;
        double marginBottom;
        int height;
        double width;
        Integer floatColumn;
    }

    private class LayoutCommand implements RepeatingCommand {
        private boolean canceled;

        @Override
        public boolean execute() {
            if (!canceled) {
                update(false);
            }
            return false;
        }

        public void cancel() {
            canceled = true;
        }
    }

    private class ColumnHeightComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer col1, Integer col2) {
            int result = Double.compare(columnHeights.get(col1),  columnHeights.get(col2));

            if (result == 0) {
                return Integer.compare(col1, col2);
            }

            return result;
        }
    }

    private static final String STYLE_INFO_KEY = "__ELASTIC_STYLE_INFO";
    private static final String FIRST = "first";
    private static final String LAST = "last";
    private static final CssFeatureDetector CSS_FEATURE_DETECTOR = GWT.create(CssFeatureDetector.class);
    private static final String CSS_TRANSFORM = CSS_FEATURE_DETECTOR.getPrefixedTransform();
    private static final String CSS_TRANSLATE_3D = CSS_FEATURE_DETECTOR.getPrefixedTranslate3d();
    private static final String CSS_CALC = CSS_FEATURE_DETECTOR.getPrefixedCalc();

    private final ElasticOption options;

    private Element container;
    private LayoutCommand layoutCommand;
    // Deque interfaces not supported by gwt
    private PriorityQueue<Integer> columnPriorities;
    private List<Double> columnHeights;
    private boolean useTranslate3d;
    private boolean useCalc;
    private double columnWidth;
    private double containerPaddingBottom;
    private double containerPaddingTop;
    private double containerPaddingLeft;
    private double containerPaddingRight;
    private HandlerRegistration resizeHandlerRegistration;
    private MutationObserver mutationObserver;

    public ElasticImpl(Element container, ElasticOption options) {
        this.container = container;
        this.options = options;

        columnHeights = new ArrayList<Double>();
        columnPriorities = new PriorityQueue<Integer>(10, new ColumnHeightComparator());
        useTranslate3d = CSS_TRANSLATE_3D != null;
        useCalc = CSS_CALC != null;

        init();
    }

    void destroy() {
        // just unbind event to release resources. I don't think we have to destroy the layout
        if (resizeHandlerRegistration != null) {
            resizeHandlerRegistration.removeHandler();
            resizeHandlerRegistration = null;
        }

        if (mutationObserver != null) {
            mutationObserver.disconnect();
            mutationObserver = null;
        } else {
            $(container).off("DOMNodeInserted DOMNodeRemoved");
        }
    }

    void update(boolean fullUpdate) {
        int prevColumnNumber = columnHeights.size();
        columnHeights.clear();
        columnPriorities.clear();

        GQuery $container = $(container);
        // check if children returns text elements
        GQuery items = $container.children();

        containerPaddingLeft = $container.cur("paddingLeft", true);
        containerPaddingRight = $container.cur("paddingRight", true);
        containerPaddingTop = $container.cur("paddingTop", true);
        containerPaddingBottom = $container.cur("paddingBottom", true);

        double totalColumnWidth = $container.innerWidth() - containerPaddingLeft - containerPaddingRight;

        int colNumber = calculateNumberOfColumn(totalColumnWidth);

        columnWidth = (totalColumnWidth - ((colNumber - 1) * options.getInnerColumnMargin())) / colNumber;
        columnWidth = max(columnWidth, options.getMinimumColumnWidth());

        double initialTop = useTranslate3d ? 0 : containerPaddingTop;
        for (int i = 0; i < colNumber; i++) {
            columnHeights.add(initialTop);
            columnPriorities.add(i);
        }

        // Use four different loops in order to avoid browser reflows
        if (fullUpdate) {
            for (Element e : items.elements()) {
                initItem(e);
            }
        }

        if (!useCalc || prevColumnNumber != colNumber) {
            for (Element e : items.elements()) {
                setItemWidth(e, colNumber);
            }
        }

        for (Element e : items.elements()) {
            readItemHeight(e);
        }

        for (Element e : items.elements()) {
            placeItem(e, colNumber);
        }

        setHeightContainer();
    }

    private void init() {
        GQuery $container = $(container);

        $container.css("minHeight", $container.height() + "px");

        if ("static".equals($container.css("position", true))) {
            $container.css("position", "relative");
        }

        update(true);

        bind();
    }

    private void setHeightContainer() {
        double top = useTranslate3d ? containerPaddingTop : 0;
        double height = top + containerPaddingBottom + getMaxHeight(0, columnHeights.size());
        $(container).css("minHeight", height + "px");
    }

    private void readItemHeight(Element e) {
        StyleInfo si = getStyleInfo(e);
        si.height = e.getClientHeight();
    }

    private StyleInfo getStyleInfo(Element e) {
        return $(e).data(STYLE_INFO_KEY, StyleInfo.class);
    }

    private void placeItem(Element e, int numberOfCol) {
        StyleInfo si = getStyleInfo(e);
        int column;
        double minHeight;
        int span = min(si.span, numberOfCol);
        Integer floatColumn = si.floatColumn;

        if (floatColumn != null && floatColumn != 0) {
            floatColumn = floatColumn > 0 ? min(floatColumn, numberOfCol - span) : numberOfCol + floatColumn;
        }

        if (span == 1) {
            if (floatColumn == null) {
                column = columnPriorities.poll();
            } else {
                column = floatColumn;
                columnPriorities.remove(column);
            }
            minHeight = columnHeights.get(column);
        } else if (span >= numberOfCol) { // span all
            column = 0;
            minHeight = getMaxHeight(column, column + span);
            columnPriorities.clear();
        } else {
            if (floatColumn != null) {
                column = floatColumn;
                minHeight = getMaxHeight(column, column + span);
            } else {
                minHeight = Double.MAX_VALUE;
                column = 0;
                for (int i = 0; i <= columnHeights.size() - span; i++) {
                    double maxHeight = getMaxHeight(i, i + span);
                    if (maxHeight < minHeight) {
                        column = i;
                        minHeight = maxHeight;
                    }
                }
            }

            for (int i = column; i < column + span; i++) {
                columnPriorities.remove(i);
            }
        }

        if (useTranslate3d) {
            String translate3d;
            if (useCalc) {
                double weight = (double) column / span;
                String offset = (100 * weight) + "%" +
                        " + " + ((si.marginLeft + si.marginRight) * weight + options.getInnerColumnMargin() * column)
                        + "px" +
                        " - " + options.getInnerColumnMargin() * (span - 1) * weight + "px";
                translate3d = CSS_TRANSLATE_3D + "(" + CSS_CALC + "(" + offset + "), " + minHeight + "px, 0)";
            } else {
                translate3d = CSS_TRANSLATE_3D + "(" + ((columnWidth + options.getInnerColumnMargin()) * column) +
                        "px, " + minHeight + "px, 0)";
            }
            setPrefixedStyle(e, CSS_TRANSFORM, translate3d);
        } else {
            double left = (columnWidth + options.getInnerColumnMargin()) * column + containerPaddingLeft;
            $(e).css("top", minHeight + "px").css("left", left + "px");
        }

        double newHeight = minHeight + si.height + si.borderTopWidth + si.borderBottomWidth + si.marginBottom + si
                .marginTop + options.getInnerRowMargin();

        for (int i = column; i < column + span; i++) {
            columnHeights.set(i, newHeight);
            columnPriorities.add(i);
        }
    }

    private double getMaxHeight(int start, int end) {
        double maxHeight = columnHeights.get(start);
        for (int i = start + 1; i < end; i++) {
            double tmpHeight = columnHeights.get(i);
            if (tmpHeight > maxHeight) {
                maxHeight = tmpHeight;
            }
        }
        return maxHeight;
    }

    private int calculateNumberOfColumn(double totalColumnWidth) {
        int innerMargin = options.getInnerColumnMargin();
        int columnWidth = options.getMinimumColumnWidth();

        int columnNbr = (int) ((totalColumnWidth + innerMargin) / (columnWidth + innerMargin));

        return max(options.getMinimalNumberOfColumn(), min(columnNbr, options.getMaximalNumberOfColumn()));
    }

    private void setItemWidth(Element e, int nbrOfCol) {
        StyleInfo si = getStyleInfo(e);
        int span = min(si.span, nbrOfCol);
        String width;
        if (useCalc) {
            double weight = (double) span / nbrOfCol;
            si.width = 100 * weight;
            double innerMarginPart = (double) (options.getInnerColumnMargin() * (nbrOfCol - 1)) * weight;
            width = CSS_CALC + "(" + si.width + "%" +
                    " - " + (si.marginLeft + si.marginRight + innerMarginPart + (containerPaddingLeft
                    + containerPaddingRight) * weight) + "px" +
                    " + " + options.getInnerColumnMargin() * (span - 1) + "px)";
        } else {
            si.width = columnWidth * span + options.getInnerColumnMargin() * (span - 1) - si.marginLeft - si
                    .marginRight;
            width = si.width + "px";
        }

        $(e).css("width", width);
    }

    private StyleInfo initItem(Element e) {
        int span = getSpan(e);
        Integer floatColumn = null;

        String floatValue = e.getAttribute(Elastic.COLUMN_ATTRIBUTE);
        if (FIRST.equalsIgnoreCase(floatValue)) {
            floatColumn = 0;
        } else if (LAST.equalsIgnoreCase(floatValue)) {
            floatColumn = -span;
        } else {
            try {
                floatColumn = Integer.parseInt(floatValue) - 1;
            } catch (NumberFormatException ignored) {
            }
        }

        GQuery $e = $(e);

        StyleInfo styleInfo = new StyleInfo();
        styleInfo.span = getSpan(e);
        styleInfo.floatColumn = floatColumn;
        styleInfo.marginRight = $e.cur("marginRight", true);
        styleInfo.marginLeft = $e.cur("marginLeft", true);
        styleInfo.borderTopWidth = $e.cur("borderTopWidth", true);
        styleInfo.borderBottomWidth = $e.cur("borderBottomWidth", true);
        styleInfo.marginTop = $e.cur("marginTop", true);
        styleInfo.marginBottom = $e.cur("marginBottom", true);

        $e.data(STYLE_INFO_KEY, styleInfo);
        $e.css("position", "absolute");

        // TODO Ease next width computation but check the impact of this in the content of the item
        if (GQuery.browser.mozilla) {
            $e.css("moz-box-sizing", "border-box");
        } else {
            $e.css("box-sizing", "border-box");
        }

        return styleInfo;
    }

    private int getSpan(Element element) {
        String attributeValue = element.getAttribute(Elastic.SPAN_ATTRIBUTE);

        if (attributeValue != null && !attributeValue.isEmpty()) {
            if ("all".equals(attributeValue)) {
                return Integer.MAX_VALUE;
            }

            try {
                return max(1, Integer.parseInt(attributeValue));
            } catch (NumberFormatException ignored) {
            }
        }

        return 1;
    }

    private void bind() {
        resizeHandlerRegistration = Window.addResizeHandler(new ResizeHandler() {
            @Override
                public void onResize(ResizeEvent event) {
                if (options.isAutoResize()) {
                    layout();
                }
            }
            });

        if (MutationObserver.isSupported()) {
            mutationObserver = new MutationObserver(new DomMutationCallback() {
                @Override
                public void onNodesRemoved(JsArray<Node> removedNodes) {
                    onItemsRemoved();
                }

                @Override
                public void onNodesInserted(JsArray<Node> addedNodes, Node nextSibling) {
                    onItemsInserted(toElementList(addedNodes));
                }

                @Override
                public void onNodesAppended(JsArray<Node> addedNodes) {
                    onItemsAppended(toElementList(addedNodes));
                }
            });

            mutationObserver.observe(container);
        } else {
            // try old api with DomMutationEvent
            $(container).on("DOMNodeInserted", new Function() {
                @Override
                public boolean f(Event event) {
                    Node node = event.getEventTarget().cast();

                    if (node.getNodeType() != Node.ELEMENT_NODE || node.getParentElement() != container) {
                        return false;
                    }

                    final Element element = node.cast();
                    Element prevSibling = element.getPreviousSiblingElement();
                    Element nextSibling = element.getNextSiblingElement();

                    if (prevSibling != null && getStyleInfo(prevSibling) != null
                            && (nextSibling == null || getStyleInfo(nextSibling) == null)) {
                        onItemsAppended(new ArrayList<Element>() {{
                            this.add(element);
                        }});
                    } else {
                        onItemsInserted(new ArrayList<Element>() {{
                            this.add(element);
                        }});
                    }
                    return false;
                }
            }).on("DOMNodeRemoved", new Function() {
                @Override
                public boolean f(Event event) {
                    Node node = event.getEventTarget().cast();

                    if (node.getNodeType() != Node.ELEMENT_NODE || node.getParentElement() != container) {
                        return false;
                    }

                    onItemsRemoved();
                    return false;
                }
            });
        }
    }

    private void onItemsRemoved() {
        layout();
    }

    private void onItemsInserted(List<Element> newItems) {
        // use several loops in order to avoid browsers reflow
        for (Element e : newItems) {
            initItem(e);
        }

        for (Element e : newItems) {
            setItemWidth(e, columnHeights.size());
        }

        layout();
    }

    private void onItemsAppended(List<Element> newItems) {
        // use several loops in order to avoid browsers reflow
        for (Element e : newItems) {
            initItem(e);
        }

        for (Element e : newItems) {
            setItemWidth(e, columnHeights.size());
        }

        for (Element e : newItems) {
            readItemHeight(e);
        }

        for (Element e : newItems) {
            placeItem(e, columnHeights.size());
        }

        setHeightContainer();
    }

    private List<Element> toElementList(JsArray<Node> nodes) {
        List<Element> elements = new ArrayList<Element>();

        for (int i = 0; i < nodes.length(); i++) {
            Node n = nodes.get(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                elements.add(n.<Element>cast());
            }
        }

        return elements;
    }

    private void layout() {
        if (layoutCommand != null) {
            layoutCommand.cancel();
        }

        layoutCommand = new LayoutCommand();

        Scheduler.get().scheduleFixedPeriod(layoutCommand, 45);
    }

    /**
     * Cannot use <code>$(e).css(styleProperty, styleValue)</code> because GWT throws AssertionError in dev mode if
     * the <code>styleProperty</code> contains a hyphen.
     * <p/>
     * In our case we are sure to use this method on modern browser, so set the style is simple.
     */
    private native void setPrefixedStyle(Element e, String styleProperty, String styleValue) /*-{
        e.style[styleProperty] = styleValue;
    }-*/;
}
