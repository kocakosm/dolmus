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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * {@link SimpleEventBus} tests.
 *
 * @author Osman KOCAK
 */
public final class SimpleEventBusTest
{
	@Test
	public void testPublish()
	{
		List<Object> traces = new ArrayList<Object>();

		Listener1 listener1 = new Listener1(traces);
		Listener2 listener2 = new Listener2(traces);
		Listener3 listener3 = new Listener3(traces);

		EventBus bus = new SimpleEventBus();
		bus.register(listener1);
		bus.register(listener2);
		bus.register(listener3);

		bus.publish("Hello");
		assertEquals(Arrays.asList(listener1, listener2, listener3), traces);

		traces.clear();

		bus.publish(new StringBuilder("World"));
		assertEquals(Arrays.asList(listener2, listener3), traces);
	}

	@Test
	public void testDeadEvent()
	{
		List<Object> traces = new ArrayList<Object>();

		Listener1 listener1 = new Listener1(traces);
		Listener2 listener2 = new Listener2(traces);
		Listener3 listener3 = new Listener3(traces);
		Listener4 listener4 = new Listener4(traces);

		EventBus bus = new SimpleEventBus();
		bus.register(listener1);
		bus.register(listener2);
		bus.register(listener3);
		bus.register(listener4);

		bus.publish(new Object());
		assertEquals(Arrays.asList(listener2, listener4), traces);
	}

	private interface DeadEventHandler
	{
		@EventHandler
		void handle(DeadEvent dead);
	}

	private static class Listener1
	{
		protected final List<Object> traces;

		Listener1(List<Object> traces)
		{
			this.traces = traces;
		}

		@EventHandler
		public void handle(String event)
		{
			traces.add(this);
		}
	}

	private static class Listener2 implements DeadEventHandler
	{
		private final List<Object> traces;

		Listener2(List<Object> traces)
		{
			this.traces = traces;
		}

		@EventHandler
		public void handle(CharSequence event)
		{
			traces.add(this);
		}

		@Override
		public void handle(DeadEvent event)
		{
			traces.add(this);
		}
	}

	private static class Listener3 extends Listener1
	{
		Listener3(List<Object> traces)
		{
			super(traces);
		}

		@EventHandler
		public void handle(StringBuilder event)
		{
			traces.add(this);
		}
	}

	private static class Listener4 implements DeadEventHandler
	{
		private final List<Object> traces;

		Listener4(List<Object> traces)
		{
			this.traces = traces;
		}

		@Override
		public void handle(DeadEvent event)
		{
			traces.add(this);
		}
	}
}
