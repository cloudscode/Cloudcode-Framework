package com.cloudcode.common.util.listener;

import org.hibernate.event.internal.DefaultSaveEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Repository;

import com.cloudcode.common.util.listener.thread.PostInsertThread;
import com.cloudcode.framework.utils.BeanFactoryHelper;
@Repository
public class HibernateInsertListener extends DefaultSaveEventListener implements
		PostInsertEventListener {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory
			.getLogger(HibernateInsertListener.class);

	public void onPostInsert(PostInsertEvent event) {

		if (getTaskExecutor() != null) {
			this.logger.info("onPostInsert");
			 PostInsertThread thread = new PostInsertThread(event);
			 getTaskExecutor().execute(thread);
		}

	}

	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return false;
	}

	private TaskExecutor getTaskExecutor() {
		BeanFactory factory = BeanFactoryHelper.getBeanFactory();
		if (factory != null) {
			return (TaskExecutor) factory.getBean("taskExecutor");
		}
		return null;
	}
}
