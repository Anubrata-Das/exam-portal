package com.exam.model.exam;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "quiz")
public class Quiz {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long qId;
	
	private String title;
	
	private String description;
	
	private String maxMarks;
	
	private String numberOfQuestions;
	
	private boolean active=false;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;
	
	@OneToMany(mappedBy = "quiz",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@JsonIgnore
	private Set<Question> questions = new HashSet<>();
	
	
}
