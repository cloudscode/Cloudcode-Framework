package com.cloudcode.common.util.listener.thread;

import org.hibernate.event.spi.PostUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostUpdateThread extends Thread {
	private final Logger logger = LoggerFactory
			.getLogger(PostUpdateThread.class);

	private PostUpdateEvent event;

	public PostUpdateThread(PostUpdateEvent event) {
		this.event = event;
	}

	public void run() {
		onPostUpdate(this.event);
	}

	public void onPostUpdate(PostUpdateEvent event) {

	}
}
