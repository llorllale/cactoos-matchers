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

import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedProc;
import org.cactoos.scalar.StickyScalar;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * An assertion that can be affirmed in tests.
 * <p>
 * Assertions are always performed on <em>operations</em>, as opposed to actual
 * values, in order to also enable asserting on errors thrown by the former.
 * <p>
 * <strong>Examples:</strong>
 * <p>
 * Testing a value:
 * <pre>
 * {@code
 *     public void test() {
 *         new Assertion(
 *             "must match the string",
 *             () -> new TextOf("string"),
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
 *         new Assertion(
 *             "must match the string",
 *             () -> { throw new IllegalArgumentException("error msg"); },
 *             new Throws("error msg", IllegalArgumentException.class)
 *         ).affirm();    // error is affirmed
 *     }
 * }
 * </pre>
 *
 * @param <T> The type of the result returned by the operation under test
 * @since 1.0.0
 * @todo #18:30min Replace all uses of MatcherAssert.assertThat() with
 *  Assertion, and ban all overloads of the former in forbidden-apis.txt.
 *  We should also look into banning common matchers like Matchers.is(), etc.
 */
@SuppressWarnings("PMD.AvoidCatchingGenericException")
public final class Assertion<T> {

    /**
     * Matcher result.
     */
    private final Scalar<Boolean> failed;

    /**
     * The description of the testing scenario.
     */
    private final Scalar<Description> scenario;

    /**
     * Ctor.
     * @param rsn Reason for refuting this assertion
     * @param test The behaviour to test
     * @param mtr Matcher to test behaviour
     */
    public Assertion(
        final String rsn, final Scalar<T> test, final Matcher<T> mtr
    ) {
        this(rsn, new StickyScalar<>(test), mtr);
    }

    /**
     * Ctor.
     * @param rsn Reason for refuting this assertion
     * @param test The behaviour to test
     * @param mtr Matcher to test behaviour
     */
    private Assertion(
        final String rsn, final StickyScalar<T> test, final Matcher<T> mtr
    ) {
        this(
            () -> !mtr.matches(test.value()),
            new Scenario(
                rsn, mtr, desc -> mtr.describeMismatch(test.value(), desc)
            )
        );
    }

    /**
     * Ctor.
     * @param rsn Reason for refuting this assertion
     * @param test The behaviour to test
     * @param mtr Matcher to test behaviour
     * @todo #53:30min Avoid the overloading ctor to accept subtype.
     *  Throws is a Matcher and thus there should be no need to give it a
     *  special treatment. The fact that we're currently being forced to do so
     *  smells, and might mean we may end up having to rethink these
     *  abstractions.
     */
    public Assertion(
        final String rsn, final Scalar<T> test, final Throws<T> mtr
    ) {
        this(rsn, new StickyScalar<>(test), mtr);
    }

    /**
     * Ctor.
     * @param rsn Reason for refuting this assertion
     * @param test The behaviour to test
     * @param mtr Matcher to test behaviour
     */
    private Assertion(
        final String rsn, final StickyScalar<T> test, final Throws<T> mtr
    ) {
        this(
            () -> !mtr.matches(test),
            new Scenario(rsn, mtr, desc -> mtr.describeMismatch(test, desc))
        );
    }

    /**
     * Ctor.
     * @param failed The failed status of testing
     * @param scenario The description of testing scenario
     */
    private Assertion(
        final Scalar<Boolean> failed, final Scalar<Description> scenario
    ) {
        this.failed = failed;
        this.scenario = scenario;
    }

    /**
     * Affirm this assertion.
     * @throws AssertionError if this assertion is refuted
     */
    public void affirm() throws AssertionError {
        try {
            if (this.failed.value()) {
                throw new AssertionError(this.scenario.value().toString());
            }
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new AssertionError("Unexpected error during test", ex);
        }
    }

    /**
     * The description of the testing scenario.
     */
    private static final class Scenario implements Scalar<Description> {

        /**
         * Reason for refuting this assertion.
         */
        private final String reason;

        /**
         * Matcher against which the behaviour will be tested.
         */
        private final Matcher<?> matcher;

        /**
         * Matcher description.
         */
        private final Proc<Description> mismatch;

        /**
         * Ctor.
         * @param reason Reason for refuting this assertion
         * @param matcher Matcher to test behaviour
         * @param mismatch Matcher mismatch.
         * @param <T> The type of matcher.
         */
        <T> Scenario(
            final String reason, final Matcher<T> matcher,
            final Proc<Description> mismatch
        ) {
            this.reason = reason;
            this.matcher = matcher;
            this.mismatch = mismatch;
        }

        @Override
        public Description value() {
            final Description description = new StringDescription();
            description.appendText(this.reason)
                .appendText(String.format("%nExpected: "))
                .appendDescriptionOf(this.matcher)
                .appendText(String.format("%n but was: "));
            new UncheckedProc<>(this.mismatch).exec(description);
            return description;
        }
    }
}
