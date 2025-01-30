package com.example.paho.producer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicInteger;
import static org.apache.camel.LoggingLevel.INFO;

@Component
public class ProducerRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        AtomicInteger counter = new AtomicInteger(0);

        from("timer:timerProducer?period={{period}}")
            .id("paho-producer")
            .process(exchange -> {
                exchange.getIn().setBody("messageId="+counter.getAndIncrement());
            })
            .log(INFO, "Sending: ${body}")
            .to("paho-mqtt5:{{topic}}"
                +"?brokerUrl={{broker.url}}"
                +"&clientId={{client.id}}"
                +"&qos=1");
    }
}
