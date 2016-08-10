package com.cloudcode.common.util.listener.thread;

import org.hibernate.event.spi.PostInsertEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostInsertThread extends Thread {
	private final Logger logger = LoggerFactory
			.getLogger(PostInsertThread.class);

	private PostInsertEvent event;

	public PostInsertThread(PostInsertEvent event) {
		this.event = event;
	}

	public void run() {
		onPostInsert(this.event);
	}

	public void onPostInsert(PostInsertEvent event) {

	}
}
