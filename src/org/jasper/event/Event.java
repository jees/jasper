package org.jasper.event;

/**
 * 
 * @author Ares_
 *
 * @param <T> The event handler
 */
public abstract class Event<T extends IEventHandler> {

	/**
	 * We dispatch the even with stuff from the {@link IEventHandler}
	 * @param handler The {@link IEventHandler}
	 */
	public abstract void dispatch(T handler);
	
}