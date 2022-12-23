package com.example.bkjeon.base.feign.user;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.bkjeon.base.feign.user.entity.FeignUser;
import com.example.bkjeon.base.feign.user.model.FeignUserRequest;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Exception
 */
@Component
@Slf4j
public class FeignUserClientFallback implements FallbackFactory<FeignUserClient>  {

	@Override
	public FeignUserClient create(Throwable throwable) {
		return new FeignUserClient() {
			@Override
			public List<FeignUser> getUserList(FeignUserRequest feignUserRequest) {
				log.error("param: {}, error ==> {}", feignUserRequest.toString(), throwable.toString());
				return null;
			}

			@Override
			public FeignUser getUser(Integer userId, String testCode) {
				log.error("param: userId ==> {}, testCode ==> {}, error ==> {}", userId, testCode, throwable.toString());
				return new FeignUser();
			}
		};
	}

}
