package com.example.bkjeon.base.services.api.v1.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.bkjeon.base.exception.error.BoardException;
import com.example.bkjeon.enums.exception.ErrorCode;
import com.example.bkjeon.dto.exception.ExceptionDTO;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/apiException")
public class ApiExceptionController {

	@ApiOperation("Exception 처리")
	@GetMapping("returnException")
	public void returnException() throws Exception {
		throw new Exception("Exception Error ~!");
	}

	@ApiOperation("사용자 에러 처리")
	@GetMapping("customException")
	public void returnCustomException() throws BoardException {
		// Board 관련 예시
		throw new BoardException("setBoardReply Error!", ErrorCode.BOARD_INSERT_FAILED);
	}

	@ApiOperation("Method 관련 테스트 이므로 Get 메소드만 성공")
	@RequestMapping("returnHttpRequestMethodNotSupportedException")
	public void returnHttpRequestMethodNotSupportedException(
		HttpServletRequest req) throws HttpRequestMethodNotSupportedException {

		if (!HttpMethod.GET.name().equals(req.getMethod())) {
			throw new HttpRequestMethodNotSupportedException("HttpRequestMethodNotSupportedException Error ~!");
		}
	}

	@ApiOperation("Enum Type Exception 처리 (ErrorCode ENUM 에 정의되어있는값을 넘겨야 성공 ex: BAD_REQUEST)")
	@GetMapping("handleMethodArgumentTypeMismatchException")
	public void handleMethodArgumentTypeMismatchException(
		@RequestParam("errorCode") ErrorCode errorCode) throws MethodArgumentTypeMismatchException {}


	@ApiOperation("BindException 처리")
	@PostMapping("returnBindException")
	public void returnBindException(
		@ModelAttribute ExceptionDTO exceptionDTO,
		BindingResult bindingResult
	) throws BindException {
		throw new BindException(bindingResult);
	}

}