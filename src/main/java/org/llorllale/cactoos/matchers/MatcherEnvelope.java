/*
 * The MIT License (MIT)
 *
 * Copyright (c) for portions of project cactoos-matchers are held by
 * Yegor Bugayenko, 2017-2018, as part of project cactoos.
 * All other copyright for project cactoos-matchers are held by
 * George Aristy, 2018.
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

import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.func.UncheckedBiProc;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.func.UncheckedProc;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher Envelope.
 * @param <T> The type of the Matcher.
 * @since 1.0.0
 * @todo #120:30min Refactor other matchers to extend MatcherEnvelope.
 *  If you do not know how to do it please refer to InputHasContent
 *  class as the example.
 */
public abstract class MatcherEnvelope<T> extends TypeSafeMatcher<T> {

    /**
     * The matcher to test.
     */
    private final Matcher<T> origin;

    /**
     * Ctor.
     * @param match Function matches an actual object with expected one
     * @param description Procedure generates a description of the object
     * @param mismatch BiProcedure generates a description for situation when an
     *  actual object does not match to the expected one
     */
    protected MatcherEnvelope(
        final Func<T, Boolean> match,
        final Proc<Description> description,
        final BiProc<T, Description> mismatch
    ) {
        this(
            new TypeSafeMatcher<T>() {
                @Override
                public void describeTo(final Description desc) {
                    new UncheckedProc<>(description).exec(desc);
                }

                @Override
                protected void describeMismatchSafely(
                    final T item,
                    final Description desc
                ) {
                    new UncheckedBiProc<>(mismatch).exec(item, desc);
                }

                @Override
                protected boolean matchesSafely(final T item) {
                    return new UncheckedFunc<>(match).apply(item);
                }
            }
        );
    }

    /**
     * Ctor.
     * @param origin Encapsulated matcher.
     */
    protected MatcherEnvelope(final Matcher<T> origin) {
        super();
        this.origin = origin;
    }

    @Override
    public final void describeTo(final Description desc) {
        this.origin.describeTo(desc);
    }

    @Override
    protected final void describeMismatchSafely(final T item,
        final Description desc) {
        this.origin.describeMismatch(item, desc);
    }

    @Override
    protected final boolean matchesSafely(final T item) {
        return this.origin.matches(item);
    }
}
