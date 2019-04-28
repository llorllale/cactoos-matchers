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

import org.hamcrest.core.IsNot;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test case for {@link FuncApplies}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class FuncAppliesTest {

    /**
     * A rule for handling an exception.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void matchFuncs() {
        new Assertion<>(
            "matches function that produces same output from the given input",
            new FuncApplies<>(1, 1),
            new Matches<>(x -> x)
        ).affirm();
    }

    @Test
    public void mismatchFuncs() {
        new Assertion<>(
            // @checkstyle LineLength (1 line)
            "does not match function that produces different output from the given input",
            new FuncApplies<>(1, 1),
            // @checkstyle MagicNumber (1 line)
            new IsNot<>(new Matches<>(x -> 3 * x))
        ).affirm();
    }

    @Test
    public void describesMismatch() {
        this.exception.expect(AssertionError.class);
        this.exception.expectMessage(
            String.format(
                "Expected: Func with <1>%n but was: Func with <3>"
            )
        );
        new Assertion<>(
            "describes mismatch",
            // @checkstyle MagicNumber (1 line)
            x -> 3 * x,
            new FuncApplies<>(1, 1)
        ).affirm();
    }
}
