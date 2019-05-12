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
import org.cactoos.Scalar;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to check that scalar throw the expected exception.
 *
 * <p>Here is an example how {@link Throws} can be used:</p>
 * <pre>
 *  MatcherAssert.assertThat(
 *       "The matcher check that [1,2,3] contains [2]",
 *       () -> {
 *            throw new IllegalArgumentException("No object(s) found.");
 *       },
 *       new Throws("No object(s) found.", IllegalArgumentException.class)
 *  );</pre>
 *
 * @param <T> Type of the scalar's value
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 * @checkstyle MemberNameCheck (200 lines)
 * @checkstyle ParameterNameCheck (200 lines)
 * @todo #108:30min Refactor 'Throws' to eliminate fields with compound names.
 *  There are two fields with compound names in this class:
 *  'messageExpectation' and 'expectationDescription'. Compound names indicate
 *  that the scope is too large and so cohesion too low. Create a new nested
 *  class, maybe 'MessageExpectation', and move these fields and related
 *  logic to this class.
 */
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.LongVariable"})
public final class Throws<T> extends TypeSafeDiagnosingMatcher<Scalar<T>> {

    /**
     * The expectation which the exception message must match.
     */
    private final UncheckedFunc<String, Boolean> messageExpectation;

    /**
     * The description of the message expectation to be displayed in case of
     * mismatch.
     */
    private final String expectationDescription;

    /**
     * The expected exception type.
     */
    private final Class<? extends Exception> type;

    /**
     * Ctor.
     * @param msg The expected exception message.
     * @param type The expected exception type.
     */
    public Throws(
        final String msg, final Class<? extends Exception> type
    ) {
        this(
            type,
            actual -> actual.equals(msg),
            new UncheckedText(
                new FormattedText("message '%s'", msg)
            ).asString()
        );
    }

    /**
     * Ctor.
     * @param type The expected exception type.
     * @param expectation The expectation which the exception message must
     *  match.
     */
    public Throws(
        final Class<? extends Exception> type,
        final Func<String, Boolean> expectation
    ) {
        this(type, expectation, "expectation for message.");
    }

    /**
     * Ctor.
     * @param type The expected exception type.
     * @param messageExpectation The expectation which the exception message
     *  must match.
     * @param expectationDescription The description of the message
     *  expectation.
     */
    public Throws(
        final Class<? extends Exception> type,
        final Func<String, Boolean> messageExpectation,
        final String expectationDescription
    ) {
        super();
        this.type = type;
        this.messageExpectation = new UncheckedFunc<>(messageExpectation);
        this.expectationDescription = expectationDescription;
    }

    @Override
    public void describeTo(final Description dsc) {
        Throws.describe(dsc, this.type, this.expectationDescription);
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    protected boolean matchesSafely(
        final Scalar<T> obj, final Description dsc
    ) {
        // @checkstyle IllegalCatchCheck (20 lines)
        boolean matches;
        try {
            obj.value();
            matches = false;
            dsc.appendText("The exception wasn't thrown.");
        } catch (final Exception cause) {
            Throws.describe(
                dsc,
                cause.getClass(),
                new UncheckedText(
                    new FormattedText("message '%s'", cause.getMessage())
                ).asString()
            );
            matches = this.type.isAssignableFrom(cause.getClass())
                && this.messageExpectation.apply(cause.getMessage());
        }
        return matches;
    }

    /**
     * Add information about the exception to the hamcrest result message.
     *
     * @param dsc The description of the object.
     * @param exception The exception type.
     * @param messageExpectation The description of the message expectation.
     *  expectation.
     */
    private static void describe(
        final Description dsc,
        final Class<?> exception,
        final String messageExpectation
    ) {
        dsc.appendText(
            new UncheckedText(
                new FormattedText(
                    "Exception has type '%s' and %s",
                    exception.getName(), messageExpectation
                )
            ).asString()
        );
    }
}
