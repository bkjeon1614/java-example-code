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