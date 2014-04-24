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
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.plugins.Plugin;

public class Elastic extends GQuery {
    public static final Class<Elastic> Elastic = GQuery.registerPlugin(Elastic.class, new Plugin<Elastic>() {
        public Elastic init(GQuery gq) {
            return new Elastic(gq);
        }
    });
    static String ELASTIC_DATA_KEY = "__GQUERY_ARCBEES_ELASTIC";

    public Elastic(GQuery gq) {
        super(gq);
    }

    private static boolean isSupported() {
        return !GQuery.browser.ie6 && !GQuery.browser.ie8;
    }

    public Elastic elastic() {
        return elastic(new ElasticOption());
    }

    public Elastic elastic(ElasticOption options) {
        for (Element e : elements()) {
            GQuery $e = $(e);
            if (isSupported() && $e.data(ELASTIC_DATA_KEY) == null) {
                ElasticImpl impl = new ElasticImpl(e, options);
                $e.data(ELASTIC_DATA_KEY, impl);
            }
        }
        return this;
    }

    public Elastic destroy() {
        for (Element e : elements()) {
            ElasticImpl impl = getImpl(e);
            if (impl != null) {
                impl.destroy();
            }
        }
        return this;
    }

    public Elastic updateLayout() {
        for (Element e : elements()) {
            ElasticImpl impl = getImpl(e);
            if (impl != null) {
                impl.update();
            }
        }
        return this;
    }

    private ElasticImpl getImpl(Element e) {
        return $(e).data(ELASTIC_DATA_KEY, ElasticImpl.class);
    }
}
