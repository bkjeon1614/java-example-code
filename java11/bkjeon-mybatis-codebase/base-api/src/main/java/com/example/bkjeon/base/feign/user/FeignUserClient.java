package com.example.bkjeon.base.feign.user;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bkjeon.base.feign.user.entity.FeignUser;
import com.example.bkjeon.base.feign.user.model.FeignUserRequest;

@FeignClient(
	configuration = FeignUserClientConfig.class,
	fallbackFactory = FeignUserClientFallback.class,
	name = "feignUser",
	url = "${external.api.user.url}"
)
public interface FeignUserClient {

	@GetMapping("users")
	List<FeignUser> getUserList(@SpringQueryMap FeignUserRequest feignUserRequest);

	@GetMapping("users/{userId}")
	FeignUser getUser(@PathVariable("userId") Integer userId, @RequestParam("testCode") String testCode);

}
