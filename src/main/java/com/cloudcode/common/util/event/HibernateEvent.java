/**
 * Project Name:Cloudcode-Framework
 * File Name:HibernateEvent.java
 * Package Name:com.cloudcode.framework.event
 * Date:2016-7-12下午4:15:38
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.cloudcode.common.util.event;

import javax.annotation.PostConstruct;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudcode.common.util.listener.HibernateDeleteListener;
import com.cloudcode.common.util.listener.HibernateInsertListener;
import com.cloudcode.common.util.listener.HibernateUpdateListener;

/**
 * ClassName:HibernateEvent <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-7-12 下午4:15:38 <br/>
 * @author   cloudscode   ljzhuanjiao@Gmail.com 
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Component
public class HibernateEvent {
	 @Autowired
	    private SessionFactory sessionFactory;

	    @Autowired
	    private HibernateInsertListener insertlistener;
	    @Autowired
	    private HibernateDeleteListener dellistener;
	    @Autowired
	    private HibernateUpdateListener updatelistener;
	    
	    @PostConstruct
	    public void registerListeners() {
	    	System.out.println("*****registerListeners*****");
	        EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
	        registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(insertlistener);
	        registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(updatelistener);
	        registry.getEventListenerGroup(EventType.POST_COMMIT_DELETE).appendListener(dellistener);
	    }
}

