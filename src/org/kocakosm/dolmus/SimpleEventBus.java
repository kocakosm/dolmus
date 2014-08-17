/*----------------------------------------------------------------------------*
 * This file is part of Dolmus.                                               *
 * Copyright (C) 2013 Osman KOCAK <kocakosm@gmail.com>                        *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify it    *
 * under the terms of the GNU Lesser General Public License as published by   *
 * the Free Software Foundation, either version 3 of the License, or (at your *
 * option) any later version.                                                 *
 * This program is distributed in the hope that it will be useful, but        *
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY *
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public     *
 * License for more details.                                                  *
 * You should have received a copy of the GNU Lesser General Public License   *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *----------------------------------------------------------------------------*/

package org.kocakosm.dolmus;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple {@link EventBus} implementation.
 *
 * @author Osman KOCAK
 */
public final class SimpleEventBus implements EventBus
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventBus.class);

	private final HandlerRegistry registry;

	/** Creates a new {@code SimpleEventBus}. */
	public SimpleEventBus()
	{
		this.registry = new HandlerRegistry();
	}

	@Override
	public void register(Object listener)
	{
		registry.register(listener);
	}

	@Override
	public void unregister(Object listener)
	{
		registry.unregister(listener);
	}

	@Override
	public void publish(Object event)
	{
		List<Handler> handlers = registry.getMatchingHandlers(event);
		if (handlers.isEmpty()) {
			if (event instanceof DeadEvent) {
				LOGGER.info("Missing handler for " + event);
			} else {
				publish(new DeadEvent(event));
			}
		}
		for (Handler handler : handlers) {
			try {
				handler.handleEvent(event);
			} catch (EventHandlerException e) {
				if (event instanceof EventHandlerException) {
					LOGGER.error("Publication error " + e);
				} else {
					publish(e);
				}
			}
		}
	}
}
