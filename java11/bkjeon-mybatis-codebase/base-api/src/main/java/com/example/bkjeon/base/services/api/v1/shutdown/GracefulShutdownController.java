package com.example.bkjeon.base.services.api.v1.shutdown;

import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class GracefulShutdownController {

	@GetMapping
	public ResponseEntity excuteShutdownTest() throws InterruptedException {
		TimeUnit.SECONDS.sleep(20);
		return new ResponseEntity<>("Graceful Shutdown Process Finished !!", HttpStatus.OK);
	}

}
