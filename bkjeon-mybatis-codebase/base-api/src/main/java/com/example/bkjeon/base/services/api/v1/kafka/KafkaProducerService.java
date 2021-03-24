package com.example.bkjeon.base.services.api.v1.kafka;

import com.example.bkjeon.common.enums.ResponseResult;
import com.example.bkjeon.common.model.ApiResponseMessage;
import com.example.bkjeon.feature.kafka.KafkaProducerCallBack;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
            "메세지 전송에 성공하였습니다. (동기)"
        );
        Producer<String, String> producer = new KafkaProducer<>(getKafkaProperties());

        try {
            RecordMetadata metadata = producer.send(
                new ProducerRecord<>("bkjeon-topic", "Hello Bkjeon Sync Message")
            ).get();

            // 에러가 없으면 메세지가 기록된 offset을 알 수 있는 RecoardMetadata를 얻을 수 있다.
            log.info("Partition: %d, Offset: %d", metadata.partition(), metadata.offset());
        } catch (Exception e) {
            log.error("producerSendMessageSync ERROR {}", e.getMessage());
            result.setResult(ResponseResult.FAIL);
            result.setMessage(e.getMessage());
            return result;
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
            "메세지 전송에 성공하였습니다. (비동기)"
        );

        Producer<String, String> producer = new KafkaProducer<>(getKafkaProperties());

        try {
            // KafkaProducerCallBack
            producer.send(
                new ProducerRecord<>("bkjeon-topic", "Hello Bkjeon Async Message"),
                new KafkaProducerCallBack()
            );
        } catch (Exception e) {
            log.error("producerSendMessageAsync ERROR {}", e.getMessage());
            result.setResult(ResponseResult.FAIL);
            result.setMessage(e.getMessage());
            return result;
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
            "메세지 전송에 성공하였습니다. (Key 사용)"
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
            log.error("producerSendKeyMessage ERROR {}", e.getMessage());
            result.setResult(ResponseResult.FAIL);
            result.setMessage(e.getMessage());
            return result;
        } finally {
            producer.close();
        }

        return result;
    }

    public ApiResponseMessage consumerSendMessageManualCommit() {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "메세지 전송에 성공하였습니다. (수동커밋)"
        );

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getKafkaCommitProperties());
        consumer.subscribe(Arrays.asList("bkjeon-topic"));
        try {
            while (true) {
                // poll을 호출해 bkjeon-topic으로부터 메세지를 가져옴
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record: records) {
                    System.out.printf(
                        "Topic: %s, Partiton: %s, Offset: %d, Key: %s, Value: %s\n",
                        record.topic(),
                        record.partition(),
                        record.offset(),
                        record.key(),
                        record.value()
                    );
                }
                try {
                    // 메세지를 모두 가져온 후 커밋
                    // 만약 데이터베이스에 저장하는 내용을 추가할 경우 consumer.commitSync(); 위에 명시 즉, 저장 후 수동커밋
                    consumer.commitSync();
                } catch (CommitFailedException e) {
                    log.error("consumerSendMessageManualCommit CommitFailedException ERROR {}", e);
                    result.setResult(ResponseResult.FAIL);
                    result.setMessage(e.getMessage());
                    return result;
                }
            }
        } catch (Exception e) {
            log.error("consumerSendMessageManualCommit Exception ERROR {}", e.getMessage());
            result.setResult(ResponseResult.FAIL);
            result.setMessage(e.getMessage());
        } finally {
            consumer.close();
        }

        return result;
    }

    public ApiResponseMessage getSpecificPartitionMessage() {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "조회에 성공하였습니다. (특정 파티션에서만 메세지를 가져옴)"
        );

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getKafkaSpecificPartitionProperties());
        String topic = "bkjeon-topic";

        // 토픽의 파티션을 정의
        TopicPartition partition0 = new TopicPartition(topic, 0);
        TopicPartition partition1 = new TopicPartition(topic, 1);

        // 배열로 파티션 0, 1 할당
        consumer.assign(Arrays.asList(partition0, partition1));

        // 특정 오프셋으로부터 메세지 가져오기
        // seek(파티션 번호, 오프셋 번호)를 정의하면 컨슈머가 다음 poll()하는 위치를 지정할 수 있다.
        // 즉, 0번과 1번 파티션의 2번 오프셋부터 메세지를 가져오게 된다.
        consumer.seek(partition0, 2);
        consumer.seek(partition1, 2);

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record: records) {
                    System.out.printf(
                        "Topic: %s, Partiton: %s, Offset: %d, Key: %s, Value: %s\n",
                        record.topic(),
                        record.partition(),
                        record.offset(),
                        record.key(),
                        record.value()
                    );
                }
                try {
                    consumer.commitSync();
                } catch (CommitFailedException e) {
                    log.error("consumerSendMessageManualCommit CommitFailedException ERROR {}", e);
                    result.setResult(ResponseResult.FAIL);
                    result.setMessage(e.getMessage());
                    return result;
                }
            }
        } catch (Exception e) {
            log.error("getSpecificPartitionMessage Exception ERROR {}", e.getMessage());
            result.setResult(ResponseResult.FAIL);
            result.setMessage(e.getMessage());
        } finally {
            consumer.close();
        }

        return result;
    }

    private Properties getKafkaSpecificPartitionProperties() {
        // Properties Objest Start
        Properties props = new Properties();

        // 브로커 리스트 정의
        props.put("bootstrap.servers", brokers);
        props.put("group.id", "bkjeon-partition");

        // 수동커밋 설정
        props.put("enable.auto.commit", "false");

        props.put("auto.offset.reset", "latest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    private Properties getKafkaCommitProperties() {
        // Properties Objest Start
        Properties props = new Properties();

        // 브로커 리스트 정의
        props.put("bootstrap.servers", brokers);
        props.put("group.id", "bkjeon-manual");

        // 수동커밋 설정
        props.put("enable.auto.commit", "false");

        props.put("auto.offset.reset", "latest");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
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
