package com.ak.Employee.kafkaConsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEventConsumer {

    @KafkaListener(topics = "employee-events", groupId = "employee_group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}