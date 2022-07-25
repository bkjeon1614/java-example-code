package com.example.bkjeon.base.services.api.v1.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/apiException")
public class ApiExceptionController {

	@GetMapping("returnException")
	public void returnException() {

	}

}
