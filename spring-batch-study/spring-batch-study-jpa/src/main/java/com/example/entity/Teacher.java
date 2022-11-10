package com.example.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String subject;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
	private List<Student> students = new ArrayList<>();

	@Builder
	public Teacher(String name, String subject) {
		this.name = name;
		this.subject = subject;
	}

	public Teacher(Long id, String name, String subject) {
		this.id = id;
		this.name = name;
		this.subject = subject;
	}

}
