package com.example.bkjeon.base.services.api.v1.kafka;

import com.example.bkjeon.common.model.ApiResponseMessage;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/kafka")
public class KafkaProducerExample {

    private final KafkaProducerService kafkaProducerService;

    @ApiOperation("동기전송")
    @PostMapping("sendMessageSync")
    public ApiResponseMessage producerSendMessageSync() {
        return kafkaProducerService.producerSendMessageSync();
    }

    @ApiOperation("비동기전송")
    @PostMapping("sendMessageAsync")
    public ApiResponseMessage producerSendMessageAsync() {
        return kafkaProducerService.producerSendMessageAsync();
    }

    @ApiOperation("Key를 지정하여 특정 파티션으로 메세지 전송")
    @PostMapping("sendKeyMessage")
    public ApiResponseMessage producerSendKeyMessage() {
        return kafkaProducerService.producerSendKeyMessage();
    }

    @ApiOperation("수동커밋")
    @PostMapping("sendMessageManualCommit")
    public ApiResponseMessage consumerSendMessageManualCommit() {
        return kafkaProducerService.consumerSendMessageManualCommit();
    }

    @ApiOperation("특정 파티션에서만 메세지를 가져옴")
    @GetMapping("specificPartitionMessage")
    public ApiResponseMessage getSpecificPartitionMessage() {
        return kafkaProducerService.getSpecificPartitionMessage();
    }

}
