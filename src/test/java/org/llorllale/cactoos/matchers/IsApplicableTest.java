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

import org.cactoos.func.FuncOf;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link IsApplicable}.
 *
 * @since 1.0
 * @checkstyle MagicNumber (100 line)
 */
final class IsApplicableTest {

    @Test
    void matches() {
        new Assertion<>(
            "matches function that produces same output from the given input",
            new IsApplicable<>(1, 1),
            new Matches<>(new FuncOf<>(x -> x))
        ).affirm();
    }

    @Test
    void mismatches() {
        new Assertion<>(
            "describes mismatch",
            new IsApplicable<>(1, 1),
            new Mismatches<>(
                new FuncOf<>(x -> 3 * x),
                "Func output matches <1>",
                "Func returned <3>"
            )
        ).affirm();
    }
}
