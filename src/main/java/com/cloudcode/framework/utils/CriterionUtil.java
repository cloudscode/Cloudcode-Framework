package com.cloudcode.framework.utils;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class CriterionUtil {

	public static void setCriterion(String strart,String end,Criteria criterion,String propertyName){
		if(!Check.isEmpty(strart) && !Check.isEmpty(end)){
			criterion.add(Restrictions.ge(propertyName, Integer.parseInt(strart)));
			criterion.add(Restrictions.le(propertyName, Integer.parseInt(end)));
		}else if(!Check.isEmpty(strart)){
			criterion.add(Restrictions.ge(propertyName, Integer.parseInt(strart)));
		}else if(!Check.isEmpty(end)){
			criterion.add(Restrictions.le(propertyName, Integer.parseInt(end)));
		}
	}
	public static void setCriterion(String strart,String end,HQLParamList hqlParamList,String propertyName){
		if(!Check.isEmpty(strart) && !Check.isEmpty(end)){
			hqlParamList.add(Restrictions.ge(propertyName, Integer.parseInt(strart)));
			hqlParamList.add(Restrictions.le(propertyName, Integer.parseInt(end)));
		}else if(!Check.isEmpty(strart)){
			hqlParamList.add(Restrictions.ge(propertyName, Integer.parseInt(strart)));
		}else if(!Check.isEmpty(end)){
			hqlParamList.add(Restrictions.le(propertyName, Integer.parseInt(end)));
		}
	}
}
