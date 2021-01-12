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
import org.cactoos.map.MapOf;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link HasEntry}.
 *
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (200 lines)
 */
@SuppressWarnings("unchecked")
final class HasEntryTest {

    /**
     * Example of {@link HasEntry} usage.
     */
    @Test
    void matches() {
        new Assertion<>(
            "must match an entry in the map",
            new HasEntry<>("a", 1),
            new Matches<>(
                new MapOf<>(
                    new MapEntry<>("a", 1),
                    new MapEntry<>("b", 2)
                )
            )
        ).affirm();
    }

    @Test
    void doesNotMatchAMissingEntry() {
        new Assertion<>(
            "must not match a missing entry in the map",
            new HasEntry<>("c", 1),
            new Mismatches<>(
                new MapOf<>(new MapEntry<>("a", 1)),
                "has entry \"c\"=<1>",
                "has no entry for \"c\""
            )
        ).affirm();
    }

    @Test
    void doesNotMatchAnIncorrectEntry() {
        new Assertion<>(
            "must not match an existing entry in the map",
            new HasEntry<>("b", 1),
            new Mismatches<>(
                new MapOf<>(new MapEntry<>("b", 2)),
                "has entry \"b\"=<1>",
                "has entry \"b\"=<2>"
            )
        ).affirm();
    }
}
