package org.jasper.event.game;

import org.jasper.event.IEventHandler;

/**
 * Used for pulsing events every 600ms
 * @author Ares_
 *
 */
public abstract class GamePulseEvent implements IEventHandler {

	/**
	 * We process the game pulse every 600ms.
	 * @param event The game logic event
	 */
	public abstract void pulse(GameLogicEvent event);
	
	/*
	 * (non-Javadoc)
	 * @see org.jasper.event.IEventHandler#dispose()
	 */
	@Override
	public boolean dispose() {
		// TODO Auto-generated method stub
		return false;
	}
	
}