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

import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link MatchesBefore}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 line)
 */
final class MatchesBeforeTest {

    /**
     * Example of {@link MatchesBefore} usage.
     */
    @Test
    void matches() {
        final String val = "test";
        new Assertion<>(
            "must run in 1000 milliseconds maximum",
            new TextOf(val),
            new MatchesBefore<>(
                1000,
                new TextIs(val)
            )
        ).affirm();
    }

    @Test
    void mismatchesFromMatcher() {
        new Assertion<>(
            "must fail because of matcher",
            new MatchesBefore<>(1000, new TextIs("a")),
            new Mismatches<>(
                new TextOf("b"),
                "Text with value \"a\" runs in less than <1000L> milliseconds",
                "Text is \"b\""
            )
        ).affirm();
    }

    @Test
    void mismatchesFromTimeout() {
        final String val = "c";
        new Assertion<>(
            "must fail because of timeout",
            new MatchesBefore<>(10, new TextIs(val)),
            new Mismatches<>(
                () -> {
                    Thread.sleep(1000);
                    return val;
                },
                "Text with value \"c\" runs in less than <10L> milliseconds",
                "Timeout after <10L> milliseconds"
            )
        ).affirm();
    }

    @Test
    void mismatchesWhenExceptionThrown() {
        final String val = "c";
        new Assertion<>(
            "description must contain exception happened inside Text",
            new MatchesBefore<>(20, new TextIs(val)),
            new Mismatches<>(
                () -> {
                    throw new UnsupportedOperationException();
                },
                new TextOf(
                    "Text with value \"c\" runs in less than <20L> milliseconds"
                ),
                new Joined(
                    " ",
                    "Thrown <java.util.concurrent.ExecutionException:",
                    "java.lang.RuntimeException:",
                    "java.lang.UnsupportedOperationException>"
                )
            )
        ).affirm();
    }
}
