package com.davita.pie.kafka;

import static kafka.consumer.Consumer.createJavaConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;


import com.davita.pie.service.PhysicianService;



@Component
public class ConsumerThreadPool {

    private static final String TOPIC = "PHYSICIANTOPIC";
    private static final Integer NUM_THREADS = 1;

    @Autowired
    private ConsumerConfigFactory consumerConfigFactory;
    
    @Autowired
    private PhysicianService physicianService;
    
   /* @Autowired
    private FacilityService facilityService;*/
    
    @Autowired
    MappingJackson2HttpMessageConverter converter;

    private ConsumerConnector consumer;
    private ExecutorService threadPool;

    public ConsumerThreadPool() {
        threadPool = Executors.newFixedThreadPool(NUM_THREADS);
    }

    @PostConstruct
    public void startConsuming() {
        ConsumerConfig consumerConfig = consumerConfigFactory.getConsumerConfig();
        consumer = createJavaConsumerConnector(consumerConfig);

        consume();
    }

    public void consume() {

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(TOPIC, NUM_THREADS);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(TOPIC);

        int threadNumber = 0;
        for (final KafkaStream<byte[], byte[]> stream : streams) {
            threadPool.submit(new DataConsumer(stream, threadNumber,converter,physicianService));
            threadNumber++;
        }
    }
}