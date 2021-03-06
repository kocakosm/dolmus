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

/**
 * Dead event. This kind of event is fired when no handler has been registered
 * for a particular type of events.
 *
 * @author Osman KOCAK
 */
public final class DeadEvent
{
	private final Object event;

	/**
	 * Creates a new {@code DeadEvent}.
	 *
	 * @param event the source event.
	 */
	public DeadEvent(Object event)
	{
		this.event = event;
	}

	/**
	 * Returns the source event.
	 *
	 * @return the source event.
	 */
	public Object getEvent()
	{
		return event;
	}

	@Override
	public String toString()
	{
		return "DeadEvent (" + event + ')';
	}
}
