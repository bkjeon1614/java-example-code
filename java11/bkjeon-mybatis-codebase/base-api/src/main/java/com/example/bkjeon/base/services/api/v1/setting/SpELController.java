package com.example.bkjeon.base.services.api.v1.setting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.entity.setting.SpELSample2;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/spel")
@RequiredArgsConstructor
public class SpELController {

	@Value("#{2+1}")
	private int addValue;

	@Value("#{'bk' + 'jeon'}")
	private String bkjeonValue;

	@Value("#{3 eq 5}")
	private boolean booleanValue;

	@Value("literal value")
	private String literalValue;

	@Value("#{'${spel.value}' eq '100'}")
	private boolean resourceBooleanValue;

	@Value("#{spELSample.testValue}")
	private int testValue;

	@ApiOperation("@Value 애노테이션에서 SpEL 사용")
	@GetMapping("value")
	public void getValue() {
		log.info("addValue: {}", addValue);
		log.info("bkjeonValue: {}", bkjeonValue);
		log.info("booleanValue: {}", booleanValue);
		log.info("literalValue: {}", literalValue);
	}

	@ApiOperation("Property 를 사용한 방법 (SpEL 은 Property 를 가질 수 있지만 반대로는 불가능하다.)")
	@GetMapping("property")
	public void getProperty() {
		log.info("property: {}", resourceBooleanValue);
	}

	@ApiOperation("Bean Reference(빈 참조) 방법 (#{빈id.프로퍼티} 형식으로 참조)")
	@GetMapping("bean")
	public void getBean() {
		log.info("bean: {}", testValue);
	}

	@ApiOperation("Expression 사용")
	@GetMapping("expression")
	public void getExpression() {
		// ExpressionParser의 구현체 SpelExpressionParser로 SpEL의 내용을 파싱(Parsing) 하고
		// Expression의 getValue() 메서드를 이용해 파싱된 결과값을 Object 타입으로 얻을 수 있다.
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression = parser.parseExpression("2+1");

		// getValue() 호출 시 Class 정보를 넘기면 자동으로 타입 캐스팅이 가능함
		Object value = expression.getValue(Integer.class);
		log.info("expression: {}", value);
	}

	@ApiOperation("EvaluationContext 사용")
	@GetMapping("evaluationContext")
	public void getEvaluationContext() {
		// name, nationality 를 파라미터로 갖는 생성자
		SpELSample2 spELSample2 = new SpELSample2("bkjeon", "Seoul");

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name"); // name 프로퍼티

		// Context에 tesla객체를 넣어준다.
		EvaluationContext context = new StandardEvaluationContext(spELSample2);
		String name1 = (String) exp.getValue(context); // name = "bkjeon"
		log.info("name1: {}", name1);	// bkjeon

		// getValue 메서드 호출 시 StandardEvaluationContext를 사용하지 않고 객체를 직접 지정
		String name2 = (String) exp.getValue(spELSample2);
		log.info("name2: {}", name2);	// bkjeon
	}

}
