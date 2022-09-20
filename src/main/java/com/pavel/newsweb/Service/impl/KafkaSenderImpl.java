package com.pavel.newsweb.Service.impl;


import com.pavel.newsweb.Service.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSenderImpl implements KafkaSender {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void SendMessage(String topic,Object message){
        kafkaTemplate.send(topic, message);
    }
}
