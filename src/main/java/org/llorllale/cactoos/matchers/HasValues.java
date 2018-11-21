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

import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.UncheckedScalar;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to check the {@link Iterable} by particular function.
 *
 * @param <X> Type of item.
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class HasValues<X> extends TypeSafeDiagnosingMatcher<Iterable<X>> {

    /**
     * The function to check the {@link Iterable}.
     */
    private final UncheckedFunc<X, Boolean> fnc;

    /**
     * Ctor.
     * @param exp The expected values within unit test.
     * @todo #/DEV Add the opportunity to print the exp values to the
     *  description in order to avoid abstract message like
     *  "The function applied to X,Y,Z is failed".
     */
    @SafeVarargs
    public HasValues(final X... exp) {
        this(x -> new ListOf<>(exp).contains(x));
    }

    /**
     * Ctor.
     * @param fnc The function to check the {@link Iterable}.
     */
    public HasValues(final Func<X, Boolean> fnc) {
        super();
        this.fnc = new UncheckedFunc<>(fnc);
    }

    @Override
    public void describeTo(final Description dsc) {
        dsc.appendText("No description");
    }

    @Override
    protected boolean matchesSafely(final Iterable<X> actual,
        final Description dsc) {
        dsc.appendText("The function applied to ")
            .appendValue(new ListOf<>(actual))
            .appendText(" is failed");
        return new UncheckedScalar<>(
            new Or(this.fnc, actual)
        ).value();
    }
}
