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

package com.arcbees.gquery.elastic.basicsample.client;

import com.arcbees.gquery.elastic.client.ElasticOption;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.Function;

import static com.arcbees.gquery.elastic.client.Elastic.Elastic;
import static com.google.gwt.query.client.GQuery.$;

/**
 * Example code for Elastic plugin for GwtQuery
 */
public class ElasticSample implements EntryPoint {
    public void onModuleLoad() {
        $("#go").click(new Function() {
            @Override
            public void f() {
                ElasticOption option = new ElasticOption()
                        .setMinimumColumWidth(400)
                        .setMinimalNumberOfColumn(2);

                $("#container").as(Elastic).elastic(option);
            }
        });
    }
}
