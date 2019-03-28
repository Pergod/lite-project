package com.sinolife.model;


import java.util.List;

@SuppressWarnings("rawtypes")
public class QueryResult {
	
	private List list;   
	private int totalrecord;    
	
	public List getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public int getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}
}
