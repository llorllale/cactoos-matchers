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

import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link HasLines}.
 *
 * @since 1.0.0
 */
final class HasLinesTest {

    /**
     * Example of {@link HasLines} behavior for positive results.
     */
    @Test
    void matches() {
        new Assertion<>(
            "matches lines containing the given strings",
            new HasLines("A", "C"),
            new Matches<>(String.format("A%nB%nC%n"))
        ).affirm();
    }

    /**
     * Example of {@link HasLines} behavior for positive results.
     */
    @Test
    void matchesWithText() {
        new Assertion<>(
            "matches lines containing the given Text",
            new HasLines(new TextOf("A"), new TextOf("D")),
            new Matches<>(String.format("A%nB%nD%n"))
        ).affirm();
    }

    /**
     * Example of {@link HasLines} behavior for negative results:
     *  - The matcher should fail in the case of incorrect input arguments;
     *  - The matcher should explain the mismatch between comparing objects.
     */
    @Test
    void failed() {
        new Assertion<>(
            "must not match lines that do not contain the given strings",
            new HasLines("Tom", "Mike"),
            new Mismatches<>(
                String.format("Tom%nJohn%n"),
                "Lines are <[Tom, Mike]>",
                "<[Tom, John]>"
            )
        ).affirm();
    }

}
