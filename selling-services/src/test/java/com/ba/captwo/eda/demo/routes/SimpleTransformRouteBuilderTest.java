package com.ba.captwo.eda.demo.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * Created by u760245 on 12/07/2014.
 */
public class SimpleTransformRouteBuilderTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder()
            throws Exception {
        return new SimpleTransformRouteBuilder();
    }

    @Test
    public void testPayloadIsTransformed()
            throws InterruptedException {
        MockEndpoint mockOut = getMockEndpoint("mock:out");
        mockOut.setExpectedMessageCount(1);
        mockOut.message(0).body().isEqualTo("Modified: Cheese");
        template.sendBody("direct:in", "Cheese");
        assertMockEndpointsSatisfied();
    }
}
