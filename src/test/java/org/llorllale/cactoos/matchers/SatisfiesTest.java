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

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Satisfies}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class SatisfiesTest {

    @Test
    void exampleFunc() {
        new Assertion<>(
            "Must satisfies",
            1,
            new Satisfies<>(x -> x > 0)
        ).affirm();
    }

    @Test
    void matchesFunc() {
        new Assertion<>(
            "Must match",
            new Satisfies<>(x -> x > 0),
            new Matches<>(1)
        ).affirm();
    }

    @Test
    void mismatchesFunc() {
        new Assertion<>(
            "must mismatch",
            new Satisfies<>(x -> x > 1),
            new Mismatches<>(
                0,
                "func application satisfies <true>",
                "func application on <0> was <false>"
            )
        ).affirm();
    }

    @Test
    void example() {
        new Assertion<>(
            "Must satisfies on an extracted feature",
            "one string",
            new Satisfies<>(
                new IsEqual<>(10),
                String::length
            )
        ).affirm();
    }

    @Test
    void matches() {
        new Assertion<>(
            "Must match an extracted feature",
            new Satisfies<>(
                new IsEqual<>(8),
                String::length
            ),
            new Matches<>("1 string")
        ).affirm();
    }

    @Test
    void mismatches() {
        new Assertion<>(
            "must mismatch an extracted feature",
            new Satisfies<>(
                new IsEqual<>(4),
                "length",
                String::length
            ),
            new Mismatches<>(
                "the string",
                "length satisfies <4>",
                "length on \"the string\" was <10>"
            )
        ).affirm();
    }

    @Test
    void mismatchesNoDesc() {
        new Assertion<>(
            "must mismatch with default descriptions",
            new Satisfies<>(
                new IsEqual<>(5),
                String::length
            ),
            new Mismatches<>(
                "some string",
                "feature satisfies <5>",
                "feature on \"some string\" was <11>"
            )
        ).affirm();
    }
}
