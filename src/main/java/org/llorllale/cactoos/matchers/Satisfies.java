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

import org.cactoos.Func;
import org.hamcrest.Matcher;

/**
 * Matches if an object satisfies the {@link Func}.
 *
 * @param <T> Type of the matched value
 * @since 1.0.0
 */
public final class Satisfies<T> extends MatcherEnvelope<T> {
    /**
     * Ctor.
     * @param func Function to verify
     */
    public Satisfies(final Func<? super T, Boolean> func) {
        this(new IsTrue(), "func application", func);
    }

    /**
     * Ctor.
     * @param matcher Matcher for feature
     * @param extractor Extractor for feature
     * @param <U> Feature type
     */
    public <U> Satisfies(
        final Matcher<? super U> matcher,
        final Func<? super T, ? extends U> extractor
    ) {
        this(matcher, "feature", extractor);
    }

    /**
     * Ctor.
     * @param matcher Matcher for feature
     * @param fdesc Description for feature
     * @param extractor Extractor for feature
     * @param <U> Feature type
     */
    public <U> Satisfies(
        final Matcher<? super U> matcher, final String fdesc,
        final Func<? super T, ? extends U> extractor
    ) {
        super(
            new MatcherOf<>(
                obj -> matcher.matches(extractor.apply(obj)),
                desc -> desc
                    .appendText(fdesc)
                    .appendText(" satisfies ")
                    .appendDescriptionOf(matcher),
                (obj, desc) -> {
                    desc.appendText(fdesc)
                        .appendText(" on ")
                        .appendValue(obj)
                        .appendText(" ");
                    matcher.describeMismatch(extractor.apply(obj), desc);
                }
            )
        );
    }
}
