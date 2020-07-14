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
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * Matcher for the value.
 *
 * @param <X> Type of input
 * @param <Y> Type of output
 * @since 0.2
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class FuncApplies<X, Y> extends MatcherEnvelope<Func<X, Y>> {

    /**
     * Ctor.
     * @param inpt Input for the function
     * @param result The result expected
     */
    public FuncApplies(final X inpt, final Y result) {
        this(inpt, new IsEqual<>(result));
    }

    /**
     * Ctor.
     * @param input Input for the function
     * @param mtr Matcher of the text
     */
    public FuncApplies(final X input, final Matcher<Y> mtr) {
        super(
            new MatcherOf<>(
                func -> mtr.matches(
                    new UncheckedFunc<>(func).apply(input)
                ),
                desc -> desc.appendText("Func with ")
                    .appendDescriptionOf(mtr),
                (func, desc) -> desc.appendText("Func with ")
                    .appendValue(func.apply(input))
            )
        );
    }
}
