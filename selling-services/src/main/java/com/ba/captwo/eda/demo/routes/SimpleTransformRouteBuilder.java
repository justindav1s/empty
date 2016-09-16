package com.ba.captwo.eda.demo.routes;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by u760245 on 12/07/2014.
 */
public class SimpleTransformRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:in")
                .transform(simple("Modified: ${body}"))
                .to("mock:out");
    }
}
