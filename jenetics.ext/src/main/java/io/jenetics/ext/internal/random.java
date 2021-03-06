/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */
package io.jenetics.ext.internal;

import static java.lang.String.format;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 * @version 3.5
 * @since 3.5
 */
public class random {
	private random() {}

	/**
	 * Returns a pseudo-random, uniformly distributed int value between 0
	 * (inclusive) and the specified value (exclusive), drawn from the given
	 * random number generator's sequence.
	 *
	 * @param n the bound on the random number to be returned. Must be
	 *        positive.
	 * @param random the random engine used for creating the random number.
	 * @return the next pseudo-random, uniformly distributed int value
	 *         between 0 (inclusive) and n (exclusive) from the given random
	 *         number generator's sequence
	 * @throws IllegalArgumentException if n is smaller than 1.
	 * @throws NullPointerException if the given {@code random}
	 *         engine is {@code null}.
	 */
	public static long nextLong(final long n, final Random random) {
		if (n <= 0) {
			throw new IllegalArgumentException(format(
				"n is smaller than one: %d", n
			));
		}

		long bits;
		long result;
		do {
			bits = random.nextLong() & 0x7fffffffffffffffL;
			result = bits%n;
		} while (bits - result + (n - 1) < 0);

		return result;
	}

	/**
	 * Returns a pseudo-random, uniformly distributed int value between 0
	 * (inclusive) and the specified value (exclusive), drawn from the given
	 * random number generator's sequence.
	 *
	 * @param n the bound on the random number to be returned. Must be
	 *        positive.
	 * @param random the random engine used for creating the random number.
	 * @return the next pseudo-random, uniformly distributed int value
	 *         between 0 (inclusive) and n (exclusive) from the given random
	 *         number generator's sequence
	 * @throws IllegalArgumentException if n is smaller than 1.
	 * @throws NullPointerException if the given {@code random}
	 *         engine of the maximal value {@code n} is {@code null}.
	 */
	public static BigInteger nextBigInteger(
		final BigInteger n,
		final Random random
	) {
		if (n.compareTo(BigInteger.ONE) < 0) {
			throw new IllegalArgumentException(format(
				"n is smaller than one: %d", n
			));
		}

		BigInteger result = null;
		if (n.bitLength() <= Integer.SIZE - 1) {
			result = BigInteger.valueOf(random.nextInt(n.intValue()));
		} else if (n.bitLength() <= Long.SIZE - 1) {
			result = BigInteger.valueOf(nextLong(n.longValue(), random));
		} else {
			do {
				result = new BigInteger(n.bitLength(), random).mod(n);
			} while (result.compareTo(n) > 0);
		}

		return result;
	}

	/**
	 * Returns a pseudo-random, uniformly distributed int value between min
	 * and max (min and max included).
	 *
	 * @param min lower bound for generated long integer (inclusively)
	 * @param max upper bound for generated long integer (inclusively)
	 * @param random the random engine to use for calculating the random
	 *        long value
	 * @return a random long integer greater than or equal to {@code min}
	 *         and less than or equal to {@code max}
	 * @throws IllegalArgumentException if {@code min >= max}
	 * @throws NullPointerException if one of the given parameters
	 *         are {@code null}.
	 */
	public static BigInteger nextBigInteger(
		final BigInteger min,
		final BigInteger max,
		final Random random
	) {
		if (min.compareTo(max) >= 0) {
			throw new IllegalArgumentException(format(
				"min >= max: %d >= %d.", min, max
			));
		}

		final BigInteger n = max.subtract(min).add(BigInteger.ONE);
		return nextBigInteger(n, random).add(min);
	}

	public static BigInteger nextBigInteger(final Random random) {
		return new BigInteger(100, random);
	}

}
