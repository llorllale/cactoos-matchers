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

import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.proc.UncheckedBiProc;
import org.cactoos.proc.UncheckedProc;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Func as Matcher.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Type of object to match
 * @since 0.12
 */
public final class MatcherOf<T> extends TypeSafeMatcher<T> {

    /**
     * Matches an actual object with expected one.
     */
    private final Func<T, Boolean> match;

    /**
     * Generates a description of the object.
     */
    private final Proc<Description> description;

    /**
     * Generates a description for situation when an actual object does not
     * match to the expected one.
     */
    private final BiProc<T, Description> mismatch;

    /**
     * Ctor.
     * @param match Matches an actual object with expected one
     * @param description Generates a description of the object
     * @param mismatch Generates a description for situation when an actual
     *  object does not match to the expected one
     */
    public MatcherOf(
        final Func<T, Boolean> match,
        final Proc<Description> description,
        final BiProc<T, Description> mismatch
    ) {
        this.match = match;
        this.description = description;
        this.mismatch = mismatch;
    }

    @Override
    public void describeTo(final Description desc) {
        new UncheckedProc<>(this.description).exec(desc);
    }

    @Override
    public void describeMismatchSafely(
        final T item,
        final Description desc
    ) {
        new UncheckedBiProc<>(this.mismatch).exec(item, desc);
    }

    @Override
    public boolean matchesSafely(final T item) {
        return new UncheckedFunc<>(this.match).apply(item);
    }
}
