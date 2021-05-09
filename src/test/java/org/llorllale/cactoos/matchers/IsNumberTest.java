/*
 * The MIT License (MIT)
 *
 * Copyright (c) for portions of project cactoos-matchers are held by
 * Yegor Bugayenko, 2017-2018, as part of project cactoos.
 * All other copyright for project cactoos-matchers are held by
 * George Aristy, 2018-2020.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.llorllale.cactoos.matchers;

import org.cactoos.scalar.MaxOf;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link IsNumber}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class IsNumberTest {

    @Test
    void matchesDouble() {
        new Assertion<>(
            "must match a double",
            new IsNumber(Double.POSITIVE_INFINITY),
            new Matches<>(Double.POSITIVE_INFINITY)
        ).affirm();
    }

    @Test
    void mismatchesDouble() {
        new Assertion<>(
            "must mismatch a double",
            new IsNumber(Double.POSITIVE_INFINITY),
            new Mismatches<>(
                1234,
                "a value equal to <Infinity> when compared by <NumberComparator{}>",
                "<1234> was less than <Infinity> when compared by <NumberComparator{}>"
            )
        ).affirm();
    }

    @Test
    void matchesFloat() {
        new Assertion<>(
            "must match a integer",
            new IsNumber(10f),
            new Matches<>(10f)
        ).affirm();
    }

    @Test
    void matchesLong() {
        new Assertion<>(
            "must match a long",
            new IsNumber(10L),
            new Matches<>(10L)
        ).affirm();
    }

    @Test
    void matchesInteger() {
        new Assertion<>(
            "must match an integer",
            new IsNumber(10),
            new Matches<>(10)
        ).affirm();
    }

    @Test
    void matchesNumber() {
        new Assertion<>(
            "must match max value via integer",
            new IsNumber(12),
            new Matches<>(new MaxOf(12L, 11L))
        ).affirm();
    }
}
