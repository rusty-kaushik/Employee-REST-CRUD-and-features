package com.ak.Employee.kafkaProducer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventProducer {

    private static final String TOPIC = "employee-events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEvent(String key, String message) {
        kafkaTemplate.send(TOPIC, key, message);
    }
}