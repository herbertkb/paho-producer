package com.example.routes;

import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.LoggingLevel.INFO;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        AtomicInteger counter = new AtomicInteger(0);

        from("timer:timerProducer?period={{timerPeriod}}")
            .id("paho-producer")
            .process(exchange -> {
                exchange.getIn().setBody("messageId="+counter.getAndIncrement());
            })
            .log(INFO, "Sending: ${body}")
            .to("paho-mqtt5:{{topic}}"
                +"?brokerUrl={{mqtt-servers}}"
                +"&clientId={{clientId}}"
                +"&qos=1");
    }
}
