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

import static org.kocakosm.dolmus.Reflection.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * {@link Reflection}'s unit tests.
 *
 * @author Osman KOCAK
 */
public final class TestReflection
{
	@Test
	public void testGetSuperTypes()
	{
		assertEquals(asSet(), getSuperTypes(A.class));
		assertEquals(asSet(A.class), getSuperTypes(B.class));
		assertEquals(asSet(A.class, B.class), getSuperTypes(C.class));
		assertEquals(asSet(), getSuperTypes(D.class));
		assertEquals(asSet(D.class, B.class, A.class), getSuperTypes(E.class));
		assertEquals(asSet(Object.class), getSuperTypes(F.class));
		assertEquals(asSet(Object.class, F.class), getSuperTypes(G.class));
		assertEquals(asSet(Object.class, G.class, F.class), getSuperTypes(H.class));
		assertEquals(asSet(Object.class, H.class, G.class, F.class, D.class, A.class), getSuperTypes(I.class));
		assertEquals(asSet(Object.class, H.class, G.class, F.class, E.class, D.class, B.class, A.class), getSuperTypes(J.class));
	}

	private <T> Set<T> asSet(T... objects)
	{
		return new HashSet<T>(Arrays.asList(objects));
	}

	interface A {}
	interface B extends A {}
	interface C extends A, B {}
	interface D {}
	interface E extends D, B {}
	class F {}
	class G extends F {}
	class H extends G {}
	class I extends H implements D, A {}
	class J extends H implements E, A {}
}
