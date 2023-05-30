package com.example.bkjeon.base.services.api.v1.feign;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.base.feign.user.FeignUserClient;
import com.example.bkjeon.base.feign.user.entity.FeignUser;
import com.example.bkjeon.base.feign.user.model.FeignUserRequest;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/feign")
public class FeignExampleController {

	private final FeignUserClient feignUserClient;

	@ApiOperation("Feign 을 통한 User 목록 조회")
	@GetMapping("users")
	public List<FeignUser> getUserList() {
		FeignUserRequest feignUserRequest = FeignUserRequest.builder().testCode("bkjeon").build();
		return feignUserClient.getUserList(feignUserRequest);
	}

	@ApiOperation("Feign 을 통한 User 상세 조회")
	@GetMapping("users/{userId}")
	public FeignUser getUser(@PathVariable Integer userId) {
		return feignUserClient.getUser(userId, "bkjeon");
	}

}
