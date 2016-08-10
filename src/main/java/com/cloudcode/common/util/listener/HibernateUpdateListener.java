package com.cloudcode.common.util.listener;

import org.hibernate.event.internal.DefaultUpdateEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;

import com.cloudcode.common.util.listener.thread.PostUpdateThread;
import com.cloudcode.framework.utils.BeanFactoryHelper;
@Repository
public class HibernateUpdateListener extends DefaultUpdateEventListener
implements PostUpdateEventListener{

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory
			.getLogger(HibernateUpdateListener.class);

	 

	private TaskExecutor getTaskExecutor() {
		BeanFactory factory = BeanFactoryHelper.getBeanFactory();
		if (factory != null) {
			return (TaskExecutor) factory.getBean("taskExecutor");
		}
		return null;
	}



	public void onPostUpdate(PostUpdateEvent event) {
		if (getTaskExecutor() != null) {
			this.logger.info("onPostUpdate");
			 PostUpdateThread thread = new PostUpdateThread(event);
		      getTaskExecutor().execute(thread);
		}
		
	}



	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return false;
	}
}
