package com.example.bkjeon.base.config.shutdown;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GracefulShutdownEventListener implements ApplicationListener<ContextClosedEvent> {

	private final GracefulShutdownTomcatConnector gracefulShutdownTomcatConnector;

	public GracefulShutdownEventListener(GracefulShutdownTomcatConnector gracefulShutdownTomcatConnector) {
		this.gracefulShutdownTomcatConnector = gracefulShutdownTomcatConnector;
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		gracefulShutdownTomcatConnector.getConnector().pause();

		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) gracefulShutdownTomcatConnector.getConnector()
			.getProtocolHandler()
			.getExecutor();

		threadPoolExecutor.shutdown();

		try {
			threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);
			log.info("Web Application Gracefully Stopped.");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
			log.error("Web Application Graceful Shutdown Failed.");
		}
	}

}
