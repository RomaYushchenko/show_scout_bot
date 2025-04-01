package com.ua.yushchenko;

import com.ua.yushchenko.common.kafka.config.KafkaConsumerConfig;
import com.ua.yushchenko.common.kafka.config.KafkaProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.ua.yushchenko.client")
@Import({
        KafkaProducerConfig.class,
        KafkaConsumerConfig.class
})
@PropertySources({
        @PropertySource("classpath:show-scout-kafka-topic.properties")
})
public class ShowScoutServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowScoutServiceApplication.class, args);
    }
}