package com.cloudcode.framework.redis.impl;

import org.springframework.stereotype.Repository;

import com.cloudcode.framework.model.ModelObject;
import com.cloudcode.framework.redis.RedisModelObjectDao;

@Repository
public class BaseRedisDaoImpl<T extends ModelObject> implements
		RedisModelObjectDao {
	
}
