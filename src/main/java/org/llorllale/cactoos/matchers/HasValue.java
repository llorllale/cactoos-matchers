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

import org.cactoos.Scalar;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * Matcher for the value.
 *
 * @param <T> Type of result
 * @since 0.2
 */
public final class HasValue<T> extends MatcherEnvelope<Scalar<? extends T>> {

    /**
     * Ctor.
     * @param value The value to match against
     */
    public HasValue(final T value) {
        this(new IsEqual<>(value));
    }

    /**
     * Ctor.
     * @param mtr Matcher of the value
     */
    public HasValue(final Matcher<? super T> mtr) {
        super(
            new MatcherOf<>(
                scalar -> mtr.matches(scalar.value()),
                desc -> desc.appendText("Scalar with ").appendDescriptionOf(mtr),
                (scalar, desc) -> mtr.describeMismatch(scalar.value(), desc)
            )
        );
    }
}
