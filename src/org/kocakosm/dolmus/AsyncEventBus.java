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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Asynchronous {@link EventBus} implementation.
 *
 * @author Osman KOCAK
 */
public final class AsyncEventBus implements EventBus
{
	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncEventBus.class);

	private final HandlerRegistry registry;
	private final ExecutorService executor;

	/**
	 * Creates a new {@code AsyncEventBus}. This method creates a thread
	 * pool that reuses a fixed number of threads operating off a shared
	 * unbounded queue. The number of threads in the pool is equal to the
	 * number of available processors {@code + 1}.
	 */
	public AsyncEventBus()
	{
		this(Executors.newFixedThreadPool(
			Runtime.getRuntime().availableProcessors() + 1));
	}

	/**
	 * Creates a new {@code AsyncEventBus}.
	 *
	 * @param executor the executor to use.
	 */
	public AsyncEventBus(ExecutorService executor)
	{
		this.executor = executor;
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
		executor.submit(new PublicationTask(event));
	}

	private final class PublicationTask implements Runnable
	{
		private final Object event;

		PublicationTask(Object event)
		{
			this.event = event;
		}

		@Override
		public void run()
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
				executor.submit(new NotificationTask(handler, event));
			}
		}
	}

	private final class NotificationTask implements Runnable
	{
		private final Handler handler;
		private final Object event;

		NotificationTask(Handler handler, Object event)
		{
			this.handler = handler;
			this.event = event;
		}

		@Override
		public void run()
		{
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
