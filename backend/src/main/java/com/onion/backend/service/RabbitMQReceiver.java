package com.onion.backend.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service
public class RabbitMQReceiver {

    @RabbitListener(queues = "onion-notification")
    public void receive(String message) {

        Timer timer = new Timer();

        // 10초 후에 실행될 작업을 Timer에 등록
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Received Message: " + message);
            }
        }, 5000); // 5초
    }
}
