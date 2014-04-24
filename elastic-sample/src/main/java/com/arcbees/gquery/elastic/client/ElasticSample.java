package com.arcbees.gquery.elastic.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.query.client.Function;

import static com.arcbees.gquery.elastic.client.Elastic.Elastic;
import static com.google.gwt.query.client.GQuery.$;

/**
 * Example code BasePlugin plugin for GwtQuery
 */
public class ElasticSample implements EntryPoint {

    public void onModuleLoad() {

        $("#go").click(new Function() {
            @Override
            public void f() {
                ElasticOption option = new ElasticOption()
                        .withColumWidth(400)
                        .withMinimalNumberOfColumn(2);

                $("#container").as(Elastic).elastic(option);
            }
        });

    }
}
