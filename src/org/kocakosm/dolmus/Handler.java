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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A handler.
 *
 * @author Osman KOCAK
 */
final class Handler
{
	private final Object listener;
	private final Method method;

	Handler(Object listener, Method method)
	{
		this.listener = listener;
		this.method = method;
	}

	void handleEvent(Object event)
	{
		try {
			method.invoke(listener, event);
		} catch (InvocationTargetException e) {
			throw new EventHandlerException(method, e.getTargetException());
		} catch (Exception e) {
			throw new EventHandlerException(method, e);
		}
	}
}
