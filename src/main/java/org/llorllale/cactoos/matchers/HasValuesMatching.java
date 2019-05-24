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
import org.cactoos.scalar.Or;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to check that {@link Iterable} has elements matched by particular
 *  function.
 *
 * <p>Here is an example how {@link HasValuesMatching} can be used:</p>
 * <pre>
 *  MatcherAssert.assertThat(
 *     new ListOf<>(1, 2, 3),
 *     new HasValuesMatching<>(value -> value > 2 || value == 3)
 * );</pre>
 *
 * @param <X> Type of item.
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class HasValuesMatching<X> extends
    TypeSafeDiagnosingMatcher<Iterable<X>> {

    /**
     * The function to match at least one element within the {@link Iterable}.
     */
    private final Func<X, Boolean> fnc;

    /**
     * Ctor.
     * @param fnc The function to match at least one element within the
     *  {@link Iterable}.
     */
    public HasValuesMatching(final Func<X, Boolean> fnc) {
        super();
        this.fnc = fnc;
    }

    @Override
    public void describeTo(final Description dsc) {
        dsc.appendText("The function matches at least 1 element.");
    }

    @Override
    protected boolean matchesSafely(final Iterable<X> actual,
        final Description dsc) {
        dsc.appendText(
            new UncheckedText(
                new FormattedText(
                    "No any elements from [%s] matches by the function",
                    new TextOf(actual)
                )
            ).asString()
        );
        return new Unchecked<>(new Or(this.fnc, actual)).value();
    }
}
