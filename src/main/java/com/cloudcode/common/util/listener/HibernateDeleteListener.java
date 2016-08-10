package com.cloudcode.common.util.listener;

import org.hibernate.event.internal.DefaultDeleteEventListener;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;

import com.cloudcode.common.util.listener.thread.PostDeleteThread;
import com.cloudcode.framework.utils.BeanFactoryHelper;
@Repository
public class HibernateDeleteListener extends DefaultDeleteEventListener
implements PostDeleteEventListener{

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory
			.getLogger(HibernateDeleteListener.class);
	private TaskExecutor getTaskExecutor() {
		BeanFactory factory = BeanFactoryHelper.getBeanFactory();
		if (factory != null) {
			return (TaskExecutor) factory.getBean("taskExecutor");
		}
		return null;
	}



	public void onPostDelete(PostDeleteEvent event) {
		if (getTaskExecutor() != null) {
			this.logger.info("onPostDelete");
			 PostDeleteThread thread = new PostDeleteThread(event);
		     getTaskExecutor().execute(thread);
		}
	}



	public boolean requiresPostCommitHanding(EntityPersister persister) {
		
		return false;
	}


 
}
