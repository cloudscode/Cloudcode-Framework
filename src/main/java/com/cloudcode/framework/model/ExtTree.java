package com.cloudcode.framework.model;

public class ExtTree {
	private String id;
	private String text;
	private String icon;
	private int leaf = 0;
	private int expanded = 0;
	private String order;
	
	
	public String getName() {
		return name;
	}

	private String name;
	private boolean isParent=true;
	

	public void setExpanded(int expanded) {
		this.expanded = expanded;
	}


	public void setLeaf(int leaf) {
		this.isParent=leaf==0;
		this.leaf = leaf;
	}

	public int getLeaf() {
		return leaf;
	}

	public int getExpanded() {
		return expanded;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.name=text;
		this.text = text;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
