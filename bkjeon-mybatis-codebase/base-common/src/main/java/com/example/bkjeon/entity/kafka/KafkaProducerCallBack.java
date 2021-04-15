package com.example.bkjeon.entity.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

@Slf4j
public class KafkaProducerCallBack implements Callback {

    // 비동기 전송에 대한 콜백 예외처리
    public void onCompletion(RecordMetadata metadata, Exception e) {
        if (metadata != null) {
            log.info("Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
        } else {
            log.error("onCompletion Error: " + e.getMessage());
        }
    }

}
