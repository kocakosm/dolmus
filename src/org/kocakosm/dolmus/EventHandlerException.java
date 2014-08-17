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

import java.lang.reflect.Method;

/**
 * This exception is used to wrap exceptions caught when calling handlers.
 *
 * @author Osman KOCAK
 */
public final class EventHandlerException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private final Method method;

	/**
	 * Creates a new {@code EventHandlerException}.
	 *
	 * @param method the invoked handler method.
	 * @param cause the error that caused this one.
	 */
	public EventHandlerException(Method method, Throwable cause)
	{
		super(cause);
		this.method = method;
	}

	/**
	 * Returns the invoked handler method.
	 *
	 * @return the invoked handler method.
	 */
	public Method getMethod()
	{
		return method;
	}
}
