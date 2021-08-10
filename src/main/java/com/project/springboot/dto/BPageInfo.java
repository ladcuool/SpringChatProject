package com.project.springboot.dto;

import lombok.Data;

@Data
public class BPageInfo
{
	int listCount;
	int totalPage;
	int curPage;
	int pageCount;
	int startPage;
	int endPage;
	int totalCount;
}
