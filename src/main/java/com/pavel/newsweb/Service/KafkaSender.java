package com.pavel.newsweb.Service;

public interface KafkaSender {
    void SendMessage(String topic,Object message);
}
