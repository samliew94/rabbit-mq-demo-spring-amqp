package com.rabbitmqdemo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;

public class Receiver {

    @Value("${server.port:0}")
    String port;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        receive("Q1_"+in, 1);
    }
    
    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive("Q2_"+in, 2);
    }

    public void receive(String in, int instance) throws InterruptedException {

        doWork(in, instance);

        // System.out.println("Instance:" + port + " " + instance + " received " + in);
        // Thread.sleep(500);
        
    }
    
    private void doWork(String in, int instance) throws InterruptedException { 
        
        System.out.println("Instance:" + port + " " + instance + " received " + in);
        Thread.sleep(100);

    }

}
