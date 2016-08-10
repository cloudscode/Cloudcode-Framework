package com.cloudcode.framework.utils;

import java.util.ArrayList;

import org.hibernate.criterion.Order;

@SuppressWarnings("serial")
public class HQLOrderList extends ArrayList<Order> {
	public HQLOrderList addCondition(Order order) {
		this.add(order);
		return this;
	}
}
