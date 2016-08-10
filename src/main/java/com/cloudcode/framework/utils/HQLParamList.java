package com.cloudcode.framework.utils;

import java.util.ArrayList;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

@SuppressWarnings("serial")
public class HQLParamList extends ArrayList<Object> {
	public HQLParamList addCondition(Object criterion) {
		this.add(criterion);
		return this;
	}

	public HQLParamList addConditionFilterEmpty(SimpleExpression criterion) {
		if (Check.isEmpty(Convert.toString(criterion.getValue())
				.replaceAll("%", "").replaceAll("null", ""))) {
			return this;
		}
		this.add(criterion);
		return this;
	}
	public HQLParamList addConditionIsNull(String properties) {
		this.add(Restrictions.or(Restrictions.isNull(properties), Restrictions.eq(properties, "")));
		return this;
	}
	
	public HQLParamList addConditionIsNotNull(String properties) {
		this.add(Restrictions.and(Restrictions.isNotNull(properties), Restrictions.ne(properties, "")));
		return this;
	}
	
}
