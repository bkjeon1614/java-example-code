package com.example.bkjeon.base.services.api.v1.kafka;

import com.example.bkjeon.common.enums.ResponseResult;
import com.example.bkjeon.common.model.ApiResponseMessage;
import com.example.bkjeon.feature.kafka.KafkaProducerCallBack;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@Service
public class KafkaProducerService {

    @Value("${kafka.brokers}")
    private String brokers;

    /**
     * topic create command: /home/bkjeon/app/kafka/bin/kafka-topics.sh --zookeeper 192.168.0.200:2181,192.168.0.201:2181,192.168.0.202:2181/bkjeon-kafka --replication-factor 3 --partitions 1 --topic bkjeon-topic --create
     * @return
     */
    public ApiResponseMessage producerSendMessageSync() {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "메세지 전송에 성공하였습니다. (동기)",
            null
        );
        Producer<String, String> producer = new KafkaProducer<>(getKafkaProperties());

        try {
            RecordMetadata metadata = producer.send(
                new ProducerRecord<>("bkjeon-topic", "Hello Bkjeon Sync Message")
            ).get();

            // 에러가 없으면 메세지가 기록된 offset을 알 수 있는 RecoardMetadata를 얻을 수 있다.
            log.info("Partition: %d, Offset: %d", metadata.partition(), metadata.offset());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("producerSendMessageSync ERROR {}", e.getMessage());
                result.setResult(ResponseResult.FAIL);
                result.setMessage(e.getMessage());
                return result;
            }
        } finally {
            producer.close();
        }

        return result;
    }

    /**
     * topic create command: /home/bkjeon/app/kafka/bin/kafka-topics.sh --zookeeper 192.168.0.200:2181,192.168.0.201:2181,192.168.0.202:2181/bkjeon-kafka --replication-factor 3 --partitions 1 --topic bkjeon-topic --create
     * @return
     */
    public ApiResponseMessage producerSendMessageAsync() {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "메세지 전송에 성공하였습니다. (비동기)",
            null
        );

        Producer<String, String> producer = new KafkaProducer<>(getKafkaProperties());

        try {
            // KafkaProducerCallBack
            producer.send(
                new ProducerRecord<>("bkjeon-topic", "Hello Bkjeon Async Message"),
                new KafkaProducerCallBack()
            );
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("producerSendMessageAsync ERROR {}", e.getMessage());
                result.setResult(ResponseResult.FAIL);
                result.setMessage(e.getMessage());
                return result;
            }
        } finally {
            producer.close();
        }

        return result;
    }

    /**
     * topic create command: /home/bkjeon/app/kafka/bin/kafka-topics.sh --zookeeper 192.168.0.200:2181,192.168.0.201:2181,192.168.0.202:2181/bkjeon-kafka --replication-factor 1 --partitions 2 --topic bkjeon-topic-partitions --create
     * @return
     */
    public ApiResponseMessage producerSendKeyMessage() {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "메세지 전송에 성공하였습니다. (Key 사용)",
            null
        );

        Producer<String, String> producer = new KafkaProducer<>(getKafkaProperties());
        String exampleTopic = "bkjeon-topic-partitions";
        String oddKey = "1";
        String evenKey = "2";

        try {
            for (int i=1; i<11; i++) {
                if (i % 2 == 1) {
                    producer.send(
                        new ProducerRecord<>(
                            exampleTopic,
                            oddKey,
                            String.format("%d - Send OddKey Message - key=" + oddKey, i)
                        )
                    );
                } else {
                    producer.send(
                        new ProducerRecord<>(
                            exampleTopic,
                            evenKey,
                            String.format("%d - Send EventKey Message - key=" + evenKey, i)
                        )
                    );
                }
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("producerSendKeyMessage ERROR {}", e.getMessage());
                result.setResult(ResponseResult.FAIL);
                result.setMessage(e.getMessage());
                return result;
            }
        } finally {
            producer.close();
        }

        return result;
    }

    private Properties getKafkaProperties() {
        // Properties Objest Start
        Properties props = new Properties();

        // 브로커 리스트 정의
        props.put("bootstrap.servers", brokers);

        // 옵션 추가
        props.put("acks", "1");
        props.put("compression.type", "gzip");

        // 메세지의 키와 값에 문자열을 사용할 예정이므로 내장된 StringSerializer를 지정
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

}
