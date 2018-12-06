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

import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.text.TextOf;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to check that {@link Iterable} has particular elements.
 *
 * <p>Here is an example how {@link HasValues} can be used:</p>
 * <pre>
 *  MatcherAssert.assertThat(
 *     new ListOf<>(1, 2, 3),
 *     new HasValues<>(2)
 * );</pre>
 *
 * @param <X> Type of item.
 * @since 1.0.0
 * @todo #36:30min HasValuesMatching that accepts matching function.
 *  For example, the "non-null", "startWith", "endsWith", "greaterThan" etc.
 *  The HasValue matcher should work only with pre-defined set of expected items
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class HasValues<X> extends TypeSafeDiagnosingMatcher<Iterable<X>> {

    /**
     * The expected values within the collection.
     */
    private final Iterable<X> expected;

    /**
     * Ctor.
     * @param expected The expected values within unit test.
     */
    @SafeVarargs
    public HasValues(final X... expected) {
        this(new IterableOf<>(expected));
    }

    /**
     * Ctor.
     * @param expected The expected values within unit test.
     */
    public HasValues(final Iterable<X> expected) {
        super();
        this.expected = expected;
    }

    @Override
    public void describeTo(final Description dsc) {
        dsc.appendValue(new TextOf(this.expected));
    }

    @Override
    protected boolean matchesSafely(final Iterable<X> actual,
        final Description dsc) {
        dsc.appendValue(new TextOf(actual));
        return new ListOf<>(actual).containsAll(new ListOf<>(this.expected));
    }
}
