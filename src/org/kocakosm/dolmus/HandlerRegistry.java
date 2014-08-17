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

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handler registry.
 *
 * @author Osman KOCAK
 */
final class HandlerRegistry
{
	private final List<HandlerInfo> handlers;

	HandlerRegistry()
	{
		this.handlers = new CopyOnWriteArrayList<HandlerInfo>();
	}

	void register(Object listener)
	{
		for (HandlerInfo handler : handlers) {
			Object o = handler.getListener();
			if (o == null) {
				handlers.remove(handler);
			} else if (listener == o) {
				return;
			}
		}
		for (Method method : getHandlerMethods(listener)) {
			Class returnType = method.getReturnType();
			Class[] params = method.getParameterTypes();
			if (returnType != Void.TYPE || params.length != 1) {
				throw new IllegalArgumentException(
					"Invalid EventHandler method signature");
			}
			handlers.add(new HandlerInfo(params[0], listener, method));
		}
	}

	void unregister(Object listener)
	{
		for (HandlerInfo handler : handlers) {
			Object o = handler.getListener();
			if (o == null || o == listener) {
				handlers.remove(handler);
			}
		}
	}

	List<Handler> getMatchingHandlers(Object event)
	{
		List<Handler> matchingHandlers = new ArrayList<Handler>();
		for (HandlerInfo handler : handlers) {
			Object listener = handler.getListener();
			if (listener == null) {
				handlers.remove(handler);
			} else if (handler.getEventClass().isInstance(event)) {
				Method method = handler.getMethod();
				matchingHandlers.add(new Handler(listener, method));
			}
		}
		return matchingHandlers;
	}

	private Set<Method> getHandlerMethods(Object listener)
	{
		Class listenerClass = listener.getClass();
		Set<Class> classes = new HashSet<Class>();
		classes.add(listenerClass);
		classes.addAll(Reflection.getSuperTypes(listenerClass));
		Set<Method> methods = new HashSet<Method>();
		for (Class klass : classes) {
			methods.addAll(Reflection.getAnnotatedMethods(klass, EventHandler.class));
		}
		return methods;
	}

	private static final class HandlerInfo
	{
		private final Class eventClass;
		private final WeakReference listener;
		private final Method method;

		HandlerInfo(Class eventClass, Object listener, Method method)
		{
			this.eventClass = eventClass;
			this.listener = new WeakReference<Object>(listener);
			this.method = method;
		}

		Class getEventClass()
		{
			return eventClass;
		}

		Method getMethod()
		{
			return method;
		}

		Object getListener()
		{
			return listener.get();
		}
	}
}
