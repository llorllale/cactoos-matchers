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

package org.llorllale.cactoos.matchers.text;

import java.util.Collection;
import java.util.Iterator;
import org.cactoos.BiFunc;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.collection.CollectionOf;
import org.cactoos.collection.Mapped;
import org.cactoos.func.UncheckedBiFunc;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.UncheckedScalar;
import org.cactoos.text.TextOf;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Allows to check that text has lines considering platform-dependent line
 *  separator.
 *
 * @since 0.12
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class HasLines extends TypeSafeDiagnosingMatcher<String> {

    /**
     * OS dependent line separator.
     */
    private final Scalar<String> lseparator;

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
     * @param lines The expected lines to be present.
     */
    public HasLines(final Text... lines) {
        this(new Mapped<>(Text::asString, lines));
    }

    /**
     * Ctor.
     * @param lines The expected lines to be present.
     */
    public HasLines(final String... lines) {
        this(new IterableOf<>(lines));
    }

    /**
     * Ctor.
     * @param lines The expected lines to be present.
     */
    public HasLines(final Iterator<String> lines) {
        this(new IterableOf<>(lines));
    }

    /**
     * Ctor.
     * @param lines The expected lines.
     */
    public HasLines(final Iterable<String> lines) {
        this(Collection::containsAll, new CollectionOf<>(lines));
    }

    /**
     * Ctor.
     * @param fnc The function to match the actual/expected lines.
     * @param lines The expected lines.
     */
    public HasLines(
        final BiFunc<Collection<String>, Collection<String>, Boolean> fnc,
        final String... lines
    ) {
        this(fnc, new CollectionOf<>(lines));
    }

    /**
     * Ctor.
     * @param fnc The function to match the actual/expected lines.
     * @param lines The expected lines.
     */
    public HasLines(
        final BiFunc<Collection<String>, Collection<String>, Boolean> fnc,
        final Text... lines
    ) {
        this(fnc, new Mapped<>(Text::asString, lines));
    }

    /**
     * Ctor.
     * @param fnc The function to match the actual/expected lines.
     * @param lines The expected lines.
     */
    public HasLines(
        final BiFunc<Collection<String>, Collection<String>, Boolean> fnc,
        final Collection<String> lines
    ) {
        this(fnc, System::lineSeparator, lines);
    }

    /**
     * Ctor.
     * @param fnc The function to match the actual/expected lines.
     * @param ltor OS dependent line separator.
     * @param lines The expected lines.
     */
    public HasLines(
        final BiFunc<Collection<String>, Collection<String>, Boolean> fnc,
        final Scalar<String> ltor,
        final Collection<String> lines
    ) {
        super();
        this.fnc = new UncheckedBiFunc<>(fnc);
        this.lseparator = new UncheckedScalar<>(ltor);
        this.expected = lines;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendValue(new TextOf(this.expected));
    }

    @Override
    protected boolean matchesSafely(final String text, final Description desc) {
        final Collection<String> actual = new CollectionOf<>(
            text.split(new UncheckedScalar<>(this.lseparator).value())
        );
        desc.appendValue(new TextOf(actual));
        return new UncheckedBiFunc<>(this.fnc).apply(actual, this.expected);
    }
}
