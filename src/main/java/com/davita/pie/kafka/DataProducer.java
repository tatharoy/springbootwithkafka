package com.davita.pie.kafka;

import com.davita.pie.domain.DataEvent;
import com.davita.pie.domain.DataEvent_New;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.StringWriter;

/**
 * Created by taroy on 4/25/16.
 */
@Component
public class DataProducer {

    @Autowired
    private ProducerConfigFactory producerConfigFactory;

    @Autowired
    MappingJackson2HttpMessageConverter converter;

    /** The logger. */
    private final Logger log = LoggerFactory.getLogger(DataProducer.class);

    private final String TOPIC = "PHYSICIANTOPIC";

    private Producer<String, String> producer;


    public DataProducer() {

    }

    @PostConstruct
    public void startProducing() {
        ProducerConfig producerConfig = producerConfigFactory.getProducerConfig();
        producer = new Producer<String, String>(producerConfig);

        //produce();
    }

    public void sendMessage(DataEvent resource){
        log.info("message is being pushed to kafka topic");
        String video = new String("");
        ObjectMapper mapper = converter.getObjectMapper();
        StringWriter writer =  new StringWriter();

            try {
             
                    mapper.writeValue(writer,resource);
                    video =  writer.toString();
                    log.info("the json for Entity {}",video);
                    KeyedMessage<String, String> data =
                            new KeyedMessage<String, String>(
                                    TOPIC,
                                    video);
                    producer.send(data);
                



            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



    }
    
    public void sendMessage(DataEvent_New resource){
        log.info("message is being pushed to kafka topic");
        String video = new String("");
        ObjectMapper mapper = converter.getObjectMapper();
        StringWriter writer =  new StringWriter();

            try {
             
                    mapper.writeValue(writer,resource);
                    video =  writer.toString();
                    log.info("the json for Entity {}",video);
                    KeyedMessage<String, String> data =
                            new KeyedMessage<String, String>(
                                    TOPIC,
                                    video);
                    producer.send(data);
                



            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



    }

    private void produce() {
        for (int i = 0; i < 5; i++) {
            String video = new String("Final Message for document_" +i);
            try {


                KeyedMessage<String, String> data =
                        new KeyedMessage<String, String>(
                                TOPIC,
                                String.valueOf(i),
                                video);
                //producer.send(data);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        producer.close();
    }

    @PreDestroy
    public void destroy() {
        if (producer != null) {
            producer.close();
        }
    }
}
