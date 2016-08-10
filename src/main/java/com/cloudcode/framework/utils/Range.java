package com.cloudcode.framework.utils;

import java.io.Serializable;

public class Range implements Serializable{

	private static final long serialVersionUID = -3472933788362913672L;

	private int start;
	private int limit;
	
	public Range(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getEnd() {
		return start + limit;
	}
	
}
