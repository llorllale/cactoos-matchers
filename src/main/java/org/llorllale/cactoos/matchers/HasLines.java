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

import java.util.Collection;
import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.collection.CollectionOf;
import org.cactoos.collection.Mapped;
import org.cactoos.func.UncheckedBiFunc;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Allows to check that text has lines considering platform-dependent line
 *  separator.
 *
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class HasLines extends TypeSafeMatcher<String> {

    /**
     * The function to split the text which came for testing.
     */
    private final UncheckedFunc<String, Collection<String>> split;

    /**
     * The expected lines.
     */
    private final Collection<String> expected;

    /**
     * The function to match the actual/expected lines.
     */
    private final BiFunc<Collection<String>, Collection<String>, Boolean> fnc;

    /**
     * Ctor.
     * @param lns The expected lines to be present.
     */
    public HasLines(final Text... lns) {
        this(new Mapped<>(Text::asString, lns));
    }

    /**
     * Ctor.
     * @param lns The expected lines to be present.
     */
    public HasLines(final String... lns) {
        this(new IterableOf<>(lns));
    }

    /**
     * Ctor.
     * @param lns The expected lines to be present.
     */
    public HasLines(final Iterable<String> lns) {
        this(Collection::containsAll, System::lineSeparator, new ListOf<>(lns));
    }

    /**
     * Ctor.
     * @param fnc The function to match the actual/expected lines.
     * @param stor OS dependent line separator.
     * @param lns The expected lines to be present.
     */
    public HasLines(
        final BiFunc<Collection<String>, Collection<String>, Boolean> fnc,
        final Scalar<String> stor,
        final Collection<String> lns
    ) {
        super();
        this.fnc = fnc;
        this.expected = lns;
        this.split = new UncheckedFunc<>(
            text -> new CollectionOf<>(text.split(stor.value()))
        );
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("Lines are ").appendValue(this.expected);
    }

    @Override
    protected boolean matchesSafely(final String text) {
        return new UncheckedBiFunc<>(this.fnc).apply(
            this.split.apply(text), this.expected
        );
    }

    @Override
    protected void describeMismatchSafely(
        final String text, final Description description
    ) {
        description.appendValue(this.split.apply(text));
    }
}
