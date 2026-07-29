package org.example.consumer;

import org.example.event.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {

    @KafkaListener(topics = "${kafka.topic.user-events}")
    public void listen(UserEvent event){
        System.out.println(event);
    }
}
