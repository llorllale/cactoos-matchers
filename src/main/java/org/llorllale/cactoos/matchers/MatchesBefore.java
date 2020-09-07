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

import org.cactoos.func.Timed;
import org.hamcrest.Matcher;

/**
 * Matcher to check that scalar finishes before some timeout.
 *
 * This is a {@link Matcher} alternative to JUnit's {@code Timeout} annotation.
 *
 * <p>Here is an example how {@link MatchesBefore} can be used:</p>
 * <pre>{@code
 *  new Assertion<>(
 *       "must run in 5 seconds maximum",
 *       new TextOf("test"),
 *       new MatchesBefore(
 *           5000,
 *           new TextIs("test")
 *       )
 *  ).affirm();
 * }</pre>
 *
 * @param <T> Type of the scalar's value
 * @since 1.0.0
 */
public final class MatchesBefore<T> extends MatcherEnvelope<T> {
    /**
     * Ctor.
     * @param millisec Timeout.
     * @param matcher Matcher.
     */
    public MatchesBefore(final long millisec, final Matcher<T> matcher) {
        super(
            new MatcherOf<>(
                new Timed<>(matcher::matches, millisec),
                desc -> desc
                    .appendDescriptionOf(matcher)
                    .appendText(" runs in less than ")
                    .appendValue(millisec).appendText(" milliseconds"),
                matcher::describeMismatch
            )
        );
    }
}
