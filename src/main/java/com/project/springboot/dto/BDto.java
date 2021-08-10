package com.project.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BDto
{
	int bId;
	String bName;
	String bTitle;
	String bContent;
	Timestamp bDate;
	int bIndent;
	String bfileName;
}
