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

import org.cactoos.map.MapEntry;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link IsEntry}.
 *
 * @since 1.0.0
 * @checkstyle ClassDataAbstractionCoupling (100 lines)
 */
final class IsEntryTest {
    /**
     * Negative case.
     */
    @Test
    void doesNotMatchEntry() {
        new Assertion<>(
            "must not match the entry",
            new IsEntry<>(
                new IsEqual<>("q"),
                new IsEqual<>("w")
            ),
            new Mismatches<>(
                new MapEntry<>("k", "v"),
                "key \"q\", value \"w\"",
                "key was \"k\", value was \"v\""
            )
        ).affirm();
    }

    /**
     * Positive case.
     */
    @Test
    void matchesEntry() {
        new Assertion<>(
            "must match the entry",
            new IsEntry<>(
                new IsEqual<>("k"),
                new IsEqual<>("v")
            ),
            new Matches<>(new MapEntry<>("k", "v"))
        ).affirm();
    }

    /**
     * Test for mismatch description readability.
     */
    @Test
    void describesCorrectly() {
        new Assertion<>(
            "must mismatch with a description",
            new IsEntry<>("c", "3"),
            new Mismatches<>(
                new MapEntry<>("b", "2"),
                "key \"c\", value \"3\"",
                "key was \"b\", value was \"2\""
            )
        ).affirm();
    }
}
