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
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;

/**
 * Matcher to check that scalar throw the expected exception.
 *
 * <p>Here is an example how {@link Throws} can be used:</p>
 * <pre>{@code
 *  new Assertion<>(
 *       "The matcher check that [1,2,3] contains [2]",
 *       () -> {
 *            throw new IllegalArgumentException("No object(s) found.");
 *       },
 *       new Throws("No object(s) found.", IllegalArgumentException.class)
 *  ).affirm();
 * }</pre>
 *
 * @param <T> Type of the scalar's value
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class Throws<T> extends TypeSafeDiagnosingMatcher<Scalar<T>> {

    /**
     * The expected exception message.
     */
    private final Matcher<String> msg;

    /**
     * The expected exception type.
     */
    private final Class<? extends Throwable> type;

    /**
     * Ctor.
     * @param type The expected exception type.
     */
    public Throws(final Class<? extends Throwable> type) {
        this(new IsAnything<>(), type);
    }

    /**
     * Ctor.
     * @param msg The expected exception message.
     * @param type The expected exception type.
     */
    public Throws(final String msg, final Class<? extends Throwable> type) {
        this(new IsEqual<>(msg), type);
    }

    /**
     * Ctor.
     * @param msg The expected exception message.
     * @param type The expected exception type.
     */
    public Throws(
        final Matcher<String> msg,
        final Class<? extends Throwable> type
    ) {
        super();
        this.msg = msg;
        this.type = type;
    }

    @Override
    public void describeTo(final Description dsc) {
        dsc
            .appendText("Exception has type '")
            .appendText(this.type.getName())
            .appendText("' and message matches ")
            .appendDescriptionOf(this.msg);
    }

    @Override
    @SuppressWarnings(
        { "PMD.AvoidCatchingGenericException", "PMD.AvoidCatchingThrowable" }
    )
    protected boolean matchesSafely(
        final Scalar<T> obj,
        final Description dsc
    ) {
        // @checkstyle IllegalCatchCheck (20 lines)
        boolean matches;
        try {
            obj.value();
            matches = false;
            dsc.appendText("The exception wasn't thrown.");
        } catch (final Throwable cause) {
            if (this.type.isAssignableFrom(cause.getClass())
                && this.msg.matches(cause.getMessage())) {
                matches = true;
            } else {
                dsc
                    .appendText("Exception has type '")
                    .appendText(cause.getClass().getName())
                    .appendText("' and message '")
                    .appendText(cause.getMessage())
                    .appendText("'");
                matches = false;
            }
        }
        return matches;
    }
}
