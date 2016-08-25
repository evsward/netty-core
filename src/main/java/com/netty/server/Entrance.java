package com.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Entrance {
	private static Logger logger = LoggerFactory.getLogger(Entrance.class);

	private static ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext-ntserver.xml");

	public static void main(String[] args) throws Exception {

		logger.info("Server starting");

	}

	public static ApplicationContext getSpringContext() {
		return context;
	}
}
