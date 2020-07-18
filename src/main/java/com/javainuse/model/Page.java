package com.javainuse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity	
@Table(name="pages")
@Data
@ToString
@EqualsAndHashCode
public class Page {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Size(min=2,message="title must be atleast 2 characters long")
	private String title;
	private String slug;
	private String content;
	private int sorting;

}
