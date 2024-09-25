Spring Boot + Spring Security + Mybatis + Thymeleaf + Gradle 로그인 기능 구현
=========


![spring_security](./document/image/spring_security.png)


## [Document]
- Tistory: https://bkjeon1614.tistory.com/76
- Github: https://github.com/bkjeon1614/java-example-code/tree/master/spring-boot-security-mybatis


## [Development Environment]
- IntelliJ IDEA Ultimate
- SpringBoot 2.1.2.RELEASE
- Java8
- Gradle
- [Lombok](https://bkjeon1614.tistory.com/75)


## [Project]


### 1. 프로젝트 생성

- File -> New -> Project 선택
  ![spring-security-1](./document/image/spring-security-1.png)
  
- Project 정보 입력
  ![spring-security-2](./document/image/spring-security-2.png)
  
- dependency 선택
  ![spring-security-3](./document/image/spring-security-3.png)
  
- Project Name 입력 및 경로 설정
  ![spring-security-4](./document/image/spring-security-4.png)


### 2. 테이블 생성

```
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `role` VALUES (1,'ADMIN');

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) DEFAULT 0,
  `login_id` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```


### 3. 의존성 설정

```
dependencies {
    ....
    
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    compileOnly 'org.projectlombok:lombok'
    implementation 'mysql:mysql-connector-java:5.1.47'
    
    // mybatis
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:1.1.1'
    
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}
```


### 4. application.yml 설정

> path: ./src/main/resources

```
spring:
profiles:
  active: local

---

spring:
profiles: local
datasource:
  url: jdbc:mysql://{host}/{db_name}?useSSL=false&characterEncoding=utf8
  username: {username}
  password: {password}
  driver-class-name: com.mysql.jdbc.Driver

mybatis:
  mapper-locations: classpath:mapper/**/*.xml

server:
  port: 8080
```


### 5. Entity

> path: ./src/main/java/com/bkjeon/example/entity/user

- Role.java
  ``` 
  package com.bkjeon.example.entity.user;
  
  import lombok.Getter;
  import lombok.Setter;
  
  @Getter
  @Setter
  public class Role {
  	private int id;
  	private String role;
  }
  ```
  
- User.java
  ``` 
  package com.bkjeon.example.entity.user;
  
  import lombok.Getter;
  import lombok.Setter;
  
  @Getter
  @Setter
  public class User {
  	private int id;
  	private String password;
  	private String passwordConfirm;
    private String loginId;
  	private String userName;
  	private int active;
  }
  ```
  
- UserRole.java
  ``` 
  package com.bkjeon.example.entity.user;
  
  import lombok.Getter;
  import lombok.Setter;
  
  @Getter
  @Setter
  public class UserRole {
  	private int userId;
  	private int roleId;
  }
  ```


### 6. Domain

> path: ./src/main/java/com/bkjeon/example/domain/user

- UserGrant.java
  ``` 
  package com.bkjeon.example.domain.user;
  
  import org.springframework.security.core.GrantedAuthority;
  
  public class UserGrant implements GrantedAuthority {
      @Override
      public String getAuthority() {
          return "ADMIN";
      }
  }
  ```

- UserPrincipal.java
  ``` 
  package com.bkjeon.example.domain.user;
  
  import com.bkjeon.example.entity.user.User;
  import java.util.Arrays;
  import java.util.Collection;
  import lombok.Getter;
  import lombok.ToString;
  import org.springframework.security.core.GrantedAuthority;
  import org.springframework.security.core.userdetails.UserDetails;
  
  @ToString
  @Getter
  public class UserPrincipal implements UserDetails {
  
      private User user;
  
      public UserPrincipal(User user) {
          this.user = user;
      }
  
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
          return Arrays.asList(new UserGrant());
      }
  
      @Override
      public String getPassword() {
          return user.getPassword();
      }
  
      @Override
      public String getUsername() {
          return user.getUserName();
      }
  
      @Override
      public boolean isAccountNonExpired() {
          return true;
      }
  
      @Override
      public boolean isAccountNonLocked() {
          return true;
      }
  
      @Override
      public boolean isCredentialsNonExpired() {
          return true;
      }
  
      @Override
      public boolean isEnabled() {
          return user.getActive() == 1;
      }
  
      public String getId() {
          return user.getLoginId();
      }
  
      public String getName() {
          return user.getUserName();
      }
  }
  ```


### 7. Service

> path: ./src/main/java/com/bkjeon/example/service/user

- UserService.java
  ```
  package com.bkjeon.example.service;
  
  import com.bkjeon.example.domain.user.UserPrincipal;
  import com.bkjeon.example.entity.user.Role;
  import com.bkjeon.example.entity.user.User;
  import com.bkjeon.example.entity.user.UserRole;
  import com.bkjeon.example.mapper.user.RoleMapper;
  import com.bkjeon.example.mapper.user.UserMapper;
  import com.bkjeon.example.mapper.user.UserRoleMapper;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.security.core.userdetails.UserDetails;
  import org.springframework.security.core.userdetails.UserDetailsService;
  import org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
  import org.springframework.stereotype.Service;
  
  @Service
  public class UserService implements UserDetailsService {
  
  	@Autowired
  	private UserMapper userMapper;
  
  	@Autowired
      private RoleMapper roleMapper;
  
  	@Autowired
  	private UserRoleMapper userRoleMapper;
  
      @Autowired
      private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  	public User findUserByLoginId(String loginId) {
  		return userMapper.findUserByLoginId(loginId);
  	}
  
  	public void saveUser(User user) {
  		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
          user.setActive(1);
          userMapper.setUserInfo(user);
  
          Role role = roleMapper.getRoleInfo("ADMIN");
  
  		UserRole userRole = new UserRole();
  		userRole.setRoleId(role.getId());
  		userRole.setUserId(user.getId());
  
  		userRoleMapper.setUserRoleInfo(userRole);
  	}
  
  	@Override
  	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  		User user = userMapper.findUserByLoginId(username);
  		return new UserPrincipal(user);
  	}
  
  } 
  ```


### 8. Mapper
> path: ./src/main/java/com/bkjeon/example/mapper/user

- RoleMapper.java
  ``` 
  package com.bkjeon.example.mapper.user;
  
  import com.bkjeon.example.entity.user.Role;
  import org.apache.ibatis.annotations.Mapper;
  import org.apache.ibatis.annotations.Param;
  import org.springframework.stereotype.Component;
  
  @Component
  @Mapper
  public interface RoleMapper {
      Role getRoleInfo(@Param("role") String role);
  }
  ```

- UserMapper.java
  ``` 
  package com.bkjeon.example.mapper.user;
  
  import com.bkjeon.example.entity.user.User;
  import org.apache.ibatis.annotations.Mapper;
  import org.apache.ibatis.annotations.Param;
  import org.springframework.stereotype.Component;
  
  @Component
  @Mapper
  public interface UserMapper {
  
      User findUserByLoginId(@Param("loginId") String loginId);
      int setUserInfo(@Param("param") User param);
  
  }
  ```

- UserRoleMapper.java
  ``` 
  package com.bkjeon.example.mapper.user;
  
  import com.bkjeon.example.entity.user.UserRole;
  import org.apache.ibatis.annotations.Mapper;
  import org.apache.ibatis.annotations.Param;
  import org.springframework.stereotype.Component;
  
  @Component
  @Mapper
  public interface UserRoleMapper {
  
      void setUserRoleInfo(@Param("param") UserRole param);
  
  }
  ```

> path: ./src/main/resources/mapper/user
  
- role.xml
  ```
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.bkjeon.example.mapper.user.RoleMapper">
  
      <resultMap id="Role" type="com.bkjeon.example.entity.user.Role">
          <result property="id" column="role_id" />
          <result property="role" column="role" />
      </resultMap>
  
      <select id="getRoleInfo" resultMap="Role">
          SELECT
              role_id, role
          FROM
              role
  
          <where>
              <if test=" role != null and role != '' ">
                  AND role = #{role}
              </if>
          </where>
  
      </select>
  
  </mapper>
  ```

- user.xml
  ```
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.bkjeon.example.mapper.user.UserMapper">
  
      <resultMap id="User" type="com.bkjeon.example.entity.user.User">
          <result property="id" column="user_id" />
          <result property="active" column="active" />
          <result property="loginId" column="login_id" />
          <result property="userName" column="user_name" />
          <result property="password" column="password" />
      </resultMap>
  
      <select id="findUserByLoginId" resultMap="User">
          SELECT
              user_id, active, login_id, user_name, password
          FROM
              user
  
          <where>
  
              <if test=" loginId != null and loginId != '' ">
                  AND login_id = #{loginId}
              </if>
  
          </where>
  
      </select>
  
      <insert id="setUserInfo" parameterType="com.bkjeon.example.entity.user.User" useGeneratedKeys="true" keyProperty="param.id">
          INSERT INTO user
          (
              active, login_id, user_name, password
          )
          VALUES
          (
              #{param.active}, #{param.loginId}, #{param.userName}, #{param.password}
          )
      </insert>
  
  </mapper>
  ```
  
- user_role.xml
  ```
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.bkjeon.example.mapper.user.UserRoleMapper">
  
      <resultMap id="UserRole" type="com.bkjeon.example.entity.user.UserRole">
          <result property="userId" column="user_id" />
          <result property="roleId" column="role_id" />
      </resultMap>
  
      <insert id="setUserRoleInfo" parameterType="map">
          INSERT INTO user_role
          (
              user_id, role_id
          )
          VALUES
          (
              #{param.userId}, #{param.roleId}
          );
      </insert>
  
  </mapper>
  ```


### 9. View

Spring Boot 에서는 기본적으로 resources 를 기본 경로로 지정한다. 하지만 확장성을 고려하여(내장 tomcat을 안쓸수도있음) webapp을 생성하여 그 안에 view관련 파일을 연동하였다.

> path: ./src/main/webapp/views/user

- login.html
  ```
  <!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
  <head>
    <title>WS Product Management System</title>
    <link rel="icon" th:href="@{/img/spring_icon.png}">
    <link rel="stylesheet" type="text/css" th:href="@{/css/login.css}" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>
  <body>
    <form th:action="@{/registration}" method="get">
      <button class="btn btn-md btn-warning btn-block" type="submit">Go To Registration Page</button>
    </form>

    <div class="container">
      <img th:src="@{/img/logo.png}" class="img-responsive center-block" width="300" height="300" alt="Logo" />
          <form th:action="@{/login}" method="POST" class="form-signin" id="login_form" name="login_form">
        <h3 class="form-signin-heading">Product Management System</h3>
        <br/>

        <input type="text" id="loginId" name="loginId"  th:placeholder="LoginId" class="form-control" /> <br/>
        <input type="password" th:placeholder="Password" id="password" name="password" class="form-control" /> <br />

              <div align="center" th:if="${param.error}">
                  <p style="font-size: 20; color: #FF1C19;">아이디 패스워드가 올바르지 않거나 비활성화된 회원입니다.</p>
              </div>

              <div align="center" th:if="${param.authError}">
                  <p style="font-size: 20; color: #FF1C19;">만료된 토큰이거나 토큰값을 갱신하여 주시길 바랍니다.</p>
              </div>

        <button class="btn btn-lg btn-primary btn-block" name="Submit" value="Login" type="Submit" th:text="Login"></button>
      </form>
    </div>
  </body>
  </html>
  ```
  
- registration.html
  ```
  <!DOCTYPE html>
  <html lang="en" xmlns="http://www.w3.org/1999/xhtml"
  	xmlns:th="http://www.thymeleaf.org">
  <head>
  	<title>Registration Form</title>
  	<link rel="icon" th:href="@{/img/spring_icon.png}">
  	<link rel="stylesheet" type="text/css" th:href="@{/css/registration.css}" />
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>
  <body>
  	<form th:action="@{/login}" method="get">
  		<button class="btn btn-md btn-warning btn-block" type="submit">Go To Login Page</button>
  	</form>	
  	
  	<div class="container">
  		<div class="row">
  			<div class="col-md-6 col-md-offset-3">
                  <form autocomplete="off" action="#" th:action="@{/registration}" th:object="${user}" method="post" class="form-horizontal" role="form">
  					<h2>Registration Form</h2>
  
  					<div class="form-group">
  						<div class="col-sm-9">
  						<label th:if="${#fields.hasErrors('loginId')}" th:errors="*{loginId}" class="validation-message"></label>
  						<input type="text" th:field="*{loginId}" placeholder="LoginId" class="form-control" />
  						</div>
  					</div>
  
  					<div class="form-group">
  						<div class="col-sm-9">
  							<label th:if="${#fields.hasErrors('userName')}" th:errors="*{userName}" class="validation-message"></label>
  							<input type="text" th:field="*{userName}" placeholder="UserName" class="form-control" />
  						</div>
  					</div>
  
  					<div class="form-group">
  						<div class="col-sm-9">
  							<input type="password" th:field="*{password}" placeholder="Password" class="form-control" />
  							<label th:if="${#fields.hasErrors('password')}" th:errors="*{password}" class="validation-message"></label>
  						</div>
  					</div>
  
                      <div class="form-group">
                          <div class="col-sm-9">
                              <input type="password" th:field="*{passwordConfirm}" placeholder="Password Confirm" class="form-control" />
  							<label th:if="${#fields.hasErrors('passwordConfirm')}" th:errors="*{passwordConfirm}" class="validation-message"></label>
                          </div>
                      </div>
  
  					<div class="form-group">
  						<div class="col-sm-9">
                              <button type="submit" class="btn btn-primary btn-block">Register User</button>
  						</div>
  					</div>
  					
  					<span th:utext="${successMessage}"></span>
  				</form>
  			</div>
  		</div>
  	</div>
  
  </body>
  </html>
  ```
  
- home.html
  ```
  <!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml"
        xmlns:th="http://www.thymeleaf.org">
  
  <head>
      <title>Admin Page</title>
      <link rel="stylesheet" type="text/css" th:href="@{/css/home.css}" />
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>
  
  <body>
  <div class="container">
  
      <form th:action="@{/logout}" method="get">
          <button class="btn btn-md btn-danger btn-block" name="registration"
                  type="Submit">Logout</button>
      </form>
  
      <div class="panel-group" style="margin-top:40px">
          <div class="panel panel-primary">
              <div class="panel-heading">
                  <span th:utext="${userName}"></span>
              </div>
              <div class="panel-body">
                  <img th:src="@{/img/welcome.png}" class="img-responsive center-block" width="400" height="400" alt="Beer" />
              </div>
              <p class="admin-message-text text-center" th:utext="${adminMessage}"></p>
          </div>
      </div>
  
  </div>
  </body>
  </html>
  ```
  
- access-denied.html
  ```
  <!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml"
        xmlns:th="http://www.thymeleaf.org">
  
  <head>
      <title>WS Product Management System</title>
      <link rel="icon" th:href="@{/img/spring_icon.png}">
      <link rel="stylesheet" type="text/css" th:href="@{/css/login.css}" />
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>
  
  <body>
  관리자만 접근이 가능합니다. ( gcijdfdo@gmail.com )
  </body>
  </html>
  ```      

> path: ./src/main/webapp/css

- login.css
  ```
  .wrapper {    
  	margin-top: 80px;
  	margin-bottom: 20px;
  }
  
  .form-signin {
    max-width: 420px;
    padding: 30px 38px 66px;
    margin: 0 auto;
    background-color: #eee;
    border: 3px dotted rgba(0,0,0,0.1);  
    }
  
  .form-signin-heading {
    text-align:center;
    margin-bottom: 30px;
  }
  
  .form-control {
    position: relative;
    font-size: 16px;
    height: auto;
    padding: 10px;
  }
  
  input[type="text"] {
    margin-bottom: 0px;
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
  }
  
  input[type="password"] {
    margin-bottom: 20px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  } 
  ```
  
- registration.css
  ``` 
  .validation-message {
  	font-style: normal;
  	font-size: 12px;
  	color: #FF1C19;
  }
  ```  

> path: ./src/main/webapp/img

- logo.png: 로그인 첫 페이지 로고 이미지
- spring_icon.png: 아이콘 이미지
- welcome.png: 로그인 성공 페이지에 표시할 이미지


### 10. Controller

> path: ./src/main/java/com/bkjeon/example/controller/view

- UserController.java
  ```
  package com.bkjeon.example.controller.view;
  
  import com.bkjeon.example.domain.user.UserPrincipal;
  import com.bkjeon.example.entity.user.User;
  import com.bkjeon.example.service.UserService;
  import javax.validation.Valid;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.security.core.Authentication;
  import org.springframework.security.core.context.SecurityContextHolder;
  import org.springframework.stereotype.Controller;
  import org.springframework.validation.BindingResult;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RequestMethod;
  import org.springframework.web.servlet.ModelAndView;
  
  @Controller
  public class UserController {
  
      @Autowired
      private UserService userService;
  
      @GetMapping(value = {"/", "login"})
      public ModelAndView getLoginPage() {
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("user/login");
          return modelAndView;
      }
  
      @GetMapping("registration")
      public ModelAndView getRegistrationPage() {
          ModelAndView modelAndView = new ModelAndView();
          User user = new User();
          modelAndView.addObject("user", user);
          modelAndView.setViewName("user/registration");
  
          return modelAndView;
      }
  
      @PostMapping("registration")
      public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
          ModelAndView modelAndView = new ModelAndView();
  
          User userExists = userService.findUserByLoginId(user.getLoginId());
          if (userExists != null) {
              bindingResult
                  .rejectValue("loginId", "error.loginId",
                      "There is already a user registered with the loginId provided");
          }
  
          if (bindingResult.hasErrors()) {
              modelAndView.setViewName("user/registration");
          } else {
              userService.saveUser(user);
              modelAndView.addObject("successMessage", "User has been registered successfully");
              modelAndView.addObject("user", new User());
              modelAndView.setViewName("user/registration");
          }
  
          return modelAndView;
      }
  
      @GetMapping("home")
      public ModelAndView home(){
          ModelAndView modelAndView = new ModelAndView();
  
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
  
          System.out.println(userPrincipal.toString());
  
          modelAndView.addObject("userName", "Welcome " + userPrincipal.getName() + " (" + userPrincipal.getId() + ")");
          modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
          modelAndView.setViewName("user/home");
          return modelAndView;
      }
  
      @GetMapping("exception")
      public ModelAndView getUserPermissionExceptionPage() {
          ModelAndView mv = new ModelAndView();
  
          mv.setViewName("user/access-denied");
  
          return mv;
      }
  
  } 
  ```


### 11. config

> path: ./src/main/java/com/bkjeon/example/config

- WebConfig.java
  ``` 
  package com.bkjeon.example.config;
  
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
  import org.springframework.web.servlet.config.annotation.EnableWebMvc;
  import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
  
  @Configuration
  @EnableWebMvc
  public class WebConfig implements WebMvcConfigurer {
  
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
          registry.addResourceHandler("/css/**").addResourceLocations("/css/");
          registry.addResourceHandler("/js/**").addResourceLocations("/js/");
          registry.addResourceHandler("/img/**").addResourceLocations("/img/");
      }
  
      @Bean
      public BCryptPasswordEncoder passwordEncoder() {
          BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
          return bCryptPasswordEncoder;
      }
  
  }
  ```

- ThymeleafConfiguration
  ``` 
  package com.bkjeon.example.config;
  
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.EnableWebMvc;
  import org.thymeleaf.spring5.SpringTemplateEngine;
  import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
  import org.thymeleaf.spring5.view.ThymeleafViewResolver;
  
  @Configuration
  @EnableWebMvc
  public class ThymeleafConfiguration {
      @Bean
      public SpringTemplateEngine templateEngine() {
          SpringTemplateEngine templateEngine = new SpringTemplateEngine();
          templateEngine.setTemplateResolver(thymeleafTemplateResolver());
          return templateEngine;
      }
  
      @Bean
      public SpringResourceTemplateResolver thymeleafTemplateResolver() {
          SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
          templateResolver.setPrefix("/views/"); // view 경로를 지정한다.
          templateResolver.setSuffix(".html");
          templateResolver.setTemplateMode("HTML5");
          templateResolver.setCacheable(false);
          return templateResolver;
      }
  
      @Bean
      public ThymeleafViewResolver thymeleafViewResolver() {
          ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
          viewResolver.setCharacterEncoding("UTF-8");
          viewResolver.setTemplateEngine(templateEngine());
          return viewResolver;
      }
  
  }
  ```

- SecurityConfiguration
  ``` 
  package com.bkjeon.example.config;
  
  import com.bkjeon.example.service.UserService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
  import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
  import org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.builders.WebSecurity;
  import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
  import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
  import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
  import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
  
  @Configuration
  @EnableWebSecurity
  public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  
      @Autowired
      private BCryptPasswordEncoder bCryptPasswordEncoder;
  
      @Autowired
      private UserService userService;
  
      @Bean
      public DaoAuthenticationProvider authenticationProvider(UserService userService) {
          DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
          authenticationProvider.setUserDetailsService(userService);
          authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
          return authenticationProvider;
      }
  
      @Override
      protected void configure(AuthenticationManagerBuilder auth) {
          auth.authenticationProvider(authenticationProvider(userService));
      }
  
      @Override
      protected void configure(HttpSecurity http) throws Exception {
          http
              .authorizeRequests()
                  .antMatchers("/").permitAll()
                  .antMatchers("/login").permitAll()
                  .antMatchers("/registration").permitAll()
                  .antMatchers("/home").hasAuthority("ADMIN") // ADMIN 권한의 유저만 /home 에 접근가능
              .anyRequest()
                  .authenticated()
                  .and().csrf().disable()
              .formLogin()
                  .loginPage("/login")
                  .failureUrl("/login?error=true")
                  .defaultSuccessUrl("/home")
                  .usernameParameter("loginId")
                  .passwordParameter("password")
              .and()
                  .logout()
                  .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              .and()
                  .exceptionHandling()
                  .accessDeniedPage("/access-denied");
      }
  
      @Override
      public void configure(WebSecurity web) {
          web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
      }
  
  }
  ```


### 결과

- Structure
  ![spring-security-5](./document/image/spring-security-5.png)
  ![spring-security-6](./document/image/spring-security-6.png)

- 로그인
  ![spring-security-7](./document/image/spring-security-7.png)
  ![spring-security-8](./document/image/spring-security-8.png)
  ![spring-security-9](./document/image/spring-security-9.png)

> SecurityConfiguration.java 에서 원하는 기능들을 커스텀하여 사용할 수 있다.













