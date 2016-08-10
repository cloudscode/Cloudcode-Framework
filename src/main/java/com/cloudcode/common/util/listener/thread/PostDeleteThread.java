package com.cloudcode.common.util.listener.thread;

import org.hibernate.event.spi.PostDeleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostDeleteThread extends Thread {
	private final Logger logger = LoggerFactory
			.getLogger(PostDeleteThread.class);

	private PostDeleteEvent event;

	public PostDeleteThread(PostDeleteEvent event) {
		this.event = event;
	}

	public void run() {
		onPostDelete(this.event);
	}

	public void onPostDelete(PostDeleteEvent event) {

	}
}
