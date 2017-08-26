package com.yitianyike.calendar.pullserver.startup;

public interface Server {

	public abstract void init();

	public abstract void start() throws Exception;

	public abstract void stop();

	public abstract void status();
	
}