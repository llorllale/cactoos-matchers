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

import org.cactoos.scalar.UncheckedScalar;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * An assertion that can be affirmed in tests.
 * <p>
 * <strong>Examples:</strong>
 * <p>
 * Testing a value:
 * <pre>
 * {@code
 *     public void test() {
 *         new Assertion2<>(
 *             "must match the string",
 *             new TextOf("string"),
 *             new TextIs("string")
 *         ).affirm();    // value is affirmed
 *     }
 * }
 * </pre>
 *
 * Testing an error (see {@link Throws}):
 * <pre>
 * {@code
 *     public void test() {
 *         new Assertion2<>(
 *             "must match the error",
 *             () -> { throw new IllegalArgumentException("error msg"); },
 *             new Throws("error msg", IllegalArgumentException.class)
 *         ).affirm();    // error is affirmed
 *     }
 * }
 * </pre>
 *
 * @param <T> The type of the result returned by the operation under test
 * @since 1.0.0
 * @todo #110:30min Continue phasing out the original Assertion class and
 *  replacing with Assertion2 instead. When done, delete the original
 *  Assertion and rename Assertion2 to 'Assertion'.
 */
public final class Assertion2<T> {
    /**
     * Whether this assertion is refuted.
     */
    private final UncheckedScalar<Boolean> refuted;
    /**
     * Refutation error.
     */
    private final UncheckedScalar<AssertionError> error;

    /**
     * Ctor.
     * @param msg Assertive statement.
     * @param test Object under test.
     * @param matcher Tester.
     */
    public Assertion2(
        final String msg, final T test, final Matcher<T> matcher
    ) {
        this.refuted = new UncheckedScalar<>(() -> !matcher.matches(test));
        this.error = new UncheckedScalar<>(
            () -> {
                final Description text = new StringDescription();
                text.appendText(msg)
                    .appendText(String.format("%nExpected: "))
                    .appendDescriptionOf(matcher)
                    .appendText(String.format("%n but was: "));
                matcher.describeMismatch(test, text);
                return new AssertionError(text.toString());
            }
        );
    }

    /**
     * Affirm this assertion.
     * @throws AssertionError if this assertion is refuted
     */
    public void affirm() throws AssertionError {
        if (this.refuted.value()) {
            throw this.error.value();
        }
    }
}
