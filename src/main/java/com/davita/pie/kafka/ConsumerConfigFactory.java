package com.davita.pie.kafka;

import kafka.consumer.ConsumerConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * Created by taroy on 4/25/16.
 */
@Component
public class ConsumerConfigFactory {

    private static final String ZK_CONNECT = "10.176.83.35:2181";

    private ConsumerConfig consumerConfig;

    @PostConstruct
    private void createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", ZK_CONNECT);
        props.put("group.id", "Physician-0");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        consumerConfig = new ConsumerConfig(props);
    }

    public ConsumerConfig getConsumerConfig() {
        return consumerConfig;
    }
}
