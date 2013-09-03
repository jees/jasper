package org.jasper.event.game;

import org.jasper.event.Event;

/**
 * 
 * @author Owner
 *
 */
public final class GameLogicEvent extends Event<GamePulseEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.jasper.event.Event#dispatch(org.jasper.event.IEventHandler)
	 */
	@Override
	public void dispatch(GamePulseEvent handler) {
		handler.pulse(this);
	}

}