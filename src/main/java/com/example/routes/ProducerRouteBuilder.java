package com.example.routes;

import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.LoggingLevel.INFO;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        AtomicInteger counter = new AtomicInteger(0);

        from("timer:timerProducer?period=#property:timerPeriod")
            .id("paho-producer")
            .process(exchange -> {
                exchange.getIn().setBody("messageId="+counter.getAndIncrement());
            })
            .log(INFO, "Sending: ${body}")
            .to("paho-mqtt5:#property:topic"
                +"?brokerUrl=#property:mqtt-servers"
                +"&clientId=$property:clientId"
                +"&qos=1");
    }
}
