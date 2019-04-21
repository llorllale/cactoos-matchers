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

import org.cactoos.iterable.IterableOfBooleans;
import org.cactoos.iterable.IterableOfInts;
import org.cactoos.list.ListOf;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsNot;
import org.junit.Test;

/**
 * Test case for {@link HasSize}.
 *
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class HasSizeTest {

    @Test
    public void matchesIterableSize() {
        new Assertion2<>(
            "matches iterable with given size",
            new HasSize(2),
            new Matches<>(new IterableOfBooleans(true, false))
        ).affirm();
    }

    @Test
    public void doesNotMatchIterableSize() {
        new Assertion2<>(
            "does not match an iterable with a different size",
            new IsNot<>(new HasSize(2)),
            new Matches<>(new IterableOfInts(1))
        ).affirm();
    }

    @Test
    public void matchesEmptyCollection() {
        new Assertion2<>(
            "matches empty iterable if given size arg is 0",
            new HasSize(0),
            new Matches<>(new ListOf<>())
        ).affirm();
    }

    @Test
    public void describesMismatch() {
        final Description description = new StringDescription();
        new HasSize(2).describeMismatchSafely(new ListOf<>(), description);
        new Assertion2<>(
            "describes mismatch",
            new TextOf(description.toString()),
            new TextIs(
                new UncheckedText(
                    new FormattedText("has size <%d>", 0)
                ).asString()
            )
        ).affirm();
    }
}
