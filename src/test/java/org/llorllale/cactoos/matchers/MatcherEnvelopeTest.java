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

import java.util.Random;
import org.cactoos.BiProc;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.text.Randomized;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test case for {@link MatcherEnvelope}.
 *
 * @since 1.0.0
 */
public final class MatcherEnvelopeTest {

    /**
     * A rule for handling an exception.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Randomized test integer to use in tests.
     */
    private final Integer testinteger = new Random().nextInt();

    /**
     * Randomized test string to use in tests.
     */
    private final String teststring = new Randomized().asString();

    /**
     * Tests that MatcherEnvelope delegates matchesSafely to the encapsulated
     * matcher. Uses one-argument constructor of MatcherEnvelope.
     */
    @Test
    public void decoratesMatchesSafely() {
        new Assertion<>(
            "must delegate match to encapsulated matcher",
                this.testinteger,
            new MatcherEnvelopeChild<>(new EncapsulatedTestMatcher())
        ).affirm();
    }

    /**
     * Tests that MatcherEnvelope delegates describeTo to the encapsulated
     * matcher. Uses one-argument constructor of MatcherEnvelope.
     */
    @Test
    public void decoratesDescribeTo() {
        this.exception.expect(AssertionError.class);
        this.exception.expectMessage(
            this.testinteger.toString()
        );
        new Assertion<>(
            "must delegate describeTo to encapsulated matcher",
            this.testinteger + 1,
            new MatcherEnvelopeChild<>(new EncapsulatedTestMatcher())
        ).affirm();
    }

    /**
     * Tests that MatcherEnvelope delegates describeMismatchSafely to the
     * encapsulated matcher. Uses multi-argument constructor of
     * MatcherEnvelope.
     */
    @Test
    public void decoratesDescribeMismatchSafely() {
        this.exception.expect(AssertionError.class);
        this.exception.expectMessage(
            this.teststring
        );
        new Assertion<>(
            "must delegate describeMismatchSafely to encapsulated matcher",
            this.testinteger,
            new MatcherEnvelopeChild<>(
                (item) -> false,
                (description) -> { },
                (item, description) -> description.appendText(this.teststring)
            )).affirm();
    }

    /**
     * Private child class to test MatcherEnvelope, which is abstract.
     * @param <T>
     */
    private class MatcherEnvelopeChild<T> extends MatcherEnvelope<T> {

        /**
         * Multi-argument constructor, just delegating to super.
         * @param match Function matches an actual object with expected one
         * @param description Procedure generates a description of the object
         * @param mismatch BiProcedure generates a description for situation
         *  when an actual object does not match to the expected one
         */
        MatcherEnvelopeChild(
            final Func<T, Boolean> match,
            final Proc<Description> description,
            final BiProc<T, Description> mismatch
        ) {
            super(match, description, mismatch);
        }

        /**
         * One-argument constructor, just delegating to super.
         * @param origin Matcher to encapsulate
         */
        MatcherEnvelopeChild(final Matcher<T> origin) {
            super(origin);
        }
    }

    /**
     * Test matcher to test one-argument constructor of MatcherEnvelope.
     */
    private class EncapsulatedTestMatcher extends TypeSafeMatcher<Integer> {
        @Override
        public void describeTo(final Description description) {
            description.appendValue(MatcherEnvelopeTest.this.testinteger);
        }

        @Override
        protected boolean matchesSafely(final Integer integer) {
            return integer.equals(MatcherEnvelopeTest.this.testinteger);
        }
    }
}
