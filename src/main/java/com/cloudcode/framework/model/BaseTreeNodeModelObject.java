package com.cloudcode.framework.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class BaseTreeNodeModelObject<T> extends BaseModelObject implements Comparable<T>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2894731017244584655L;
	private String text;
	private String node;
	private int leaf;
	private int expanded;
	private String icon;
	@Transient
	private String iconCls;
	
	@Transient
	private List<T> children;
	
	@Column(name = "order_", length = 64, updatable = false)
	private String order;
	
	@Column(name = "TEXT", length = 64)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.name=text;
		this.text = text;
	}

	@Column(name = "NODE", length = 36)
	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	@Column(name = "LEAF", length = 1)
	public int getLeaf() {
		return leaf;
	}

	public void setLeaf(int leaf) {
		this.isParent=leaf==0;
		this.leaf = leaf;
	}

	@Column(name = "EXPANDED", length = 1)
	public int getExpanded() {
		return expanded;
	}

	public void setExpanded(int expanded) {
		this.expanded = expanded;
	}

	@Column(name = "ICON", length = 256)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Transient
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
 
	@Column(name = "order_", length = 64, updatable = false)
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	@Transient	
	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}
	
	@Transient
	private String name;
	@Transient
	private boolean isParent;
	@Transient
	public String getName() {
		return text;
	}

	public int compareTo(T arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Transient
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
