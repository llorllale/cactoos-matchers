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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Matcher Envelope.
 * @param <T> The type of the Matcher.
 * @since 1.0.0
 */
public abstract class MatcherEnvelope<T> extends BaseMatcher<T> {

    /**
     * The matcher to test.
     */
    private final Matcher<? super T> origin;

    /**
     * Ctor.
     * @param origin Encapsulated matcher.
     */
    protected MatcherEnvelope(final Matcher<? super T> origin) {
        super();
        this.origin = origin;
    }

    @Override
    public final void describeTo(final Description desc) {
        this.origin.describeTo(desc);
    }

    @Override
    public final boolean matches(final Object actual) {
        return this.origin.matches(actual);
    }

    @Override
    public final void describeMismatch(final Object item, final Description description) {
        this.origin.describeMismatch(item, description);
    }
}
