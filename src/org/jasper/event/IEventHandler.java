package org.jasper.event;

/**
 * An interface handling the events
 * @author Ares_
 *
 */
public interface IEventHandler {

	/**
	 * Should we dispose of the event handler after execution?
	 * @return True if we should or false if we shouldn't
	 */
	public boolean dispose();
	
}