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
 * Event bus.
 *
 * @author Osman KOCAK
 */
public interface EventBus
{
	/**
	 * Registers the given listener.
	 *
	 * @param listener the listener to register.
	 */
	void register(Object listener);

	/**
	 * Unregisters the given listener.
	 *
	 * @param listener the listener to unregister.
	 */
	void unregister(Object listener);

	/**
	 * Publishes the given event.
	 *
	 * @param event the event to publish.
	 */
	void publish(Object event);
}
