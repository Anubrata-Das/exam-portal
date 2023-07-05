package com.exam.model.exam;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long quesId;
	
	@Column(length = 3000)
	private String content;
	
	private String image;
	
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	
	private String answer;
	
	@Transient
	private String givenAnswer;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@Cascade(CascadeType.ALL)
	private Quiz quiz;
	

}
