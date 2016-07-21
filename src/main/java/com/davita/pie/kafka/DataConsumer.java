package com.davita.pie.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.davita.pie.domain.DataEvent;
import com.davita.pie.domain.DataEvent_New;
import com.davita.pie.domain.Facility;
import com.davita.pie.domain.Physician;
import com.davita.pie.service.PhysicianService;
import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * Created by taroy on 4/25/16.
 */
public class DataConsumer implements Runnable {


    private KafkaStream<byte[], byte[]> kafkaStream;
    private int threadNumber;
    
    
    private MappingJackson2HttpMessageConverter converter;
    
    
    private PhysicianService physicianService;
    
    //private FacilityService facilityService;
    
    

    public DataConsumer(KafkaStream<byte[], byte[]> kafkaStream, int threadNumber,MappingJackson2HttpMessageConverter converter, PhysicianService physicianService) {
        this.threadNumber = threadNumber;
        this.kafkaStream = kafkaStream;
        this.converter = converter;
        this.physicianService = physicianService;
        //this.facilityService = facilityService;

    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        System.out.println("inside thread "+threadNumber);
        try {
        Thread.sleep(5000);
        while (it.hasNext()) {
            byte[] messageData = it.next().message();


                System.out.println("Thread:" + threadNumber + ".Consuming data: " + new String(messageData));
                ObjectMapper mapper = converter.getObjectMapper();
                DataEvent event = mapper.readValue(messageData, DataEvent.class);
//                if(event.getEntity() instanceof Physician){
//                	
//                	physicianService.process(event);
//                }else if (event.getEntity() instanceof Facility){
//                	facilityService.process(event);
//                }
                physicianService.process(event);


        }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Shutting down Thread: " + kafkaStream);
    }
}
