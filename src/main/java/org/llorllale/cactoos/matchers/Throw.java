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

import org.cactoos.Scalar;
import org.cactoos.text.FormattedText;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to check that scalar throw the expected exception.
 *
 * <p>Here is an example how {@link Throw} can be used:</p>
 * <pre>
 *  MatcherAssert.assertThat(
 *       "The matcher check that [1,2,3] contains [2]",
 *       () -> {
 *            throw new IllegalArgumentException("No object(s) found.");
 *       },
 *       new Throw("No object(s) found.", IllegalArgumentException.class)
 *  );</pre>
 *
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class Throw extends TypeSafeDiagnosingMatcher<Scalar<?>> {

    /**
     * The expected exception message.
     */
    private final String msg;

    /**
     * The expected exception type.
     */
    private final Class<? extends Exception> type;

    /**
     * Ctor.
     * @param msg The expected exception message.
     * @param type The expected exception type.
     */
    public Throw(final String msg, final Class<? extends Exception> type) {
        super();
        this.msg = msg;
        this.type = type;
    }

    @Override
    public void describeTo(final Description dsc) {
        describe(dsc, this.type, this.msg);
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    protected boolean matchesSafely(final Scalar<?> obj,
        final Description dsc) {
        // @checkstyle IllegalCatchCheck (20 lines)
        boolean matches;
        try {
            obj.value();
            matches = false;
            dsc.appendText("The exception wasn't thrown.");
        } catch (final Exception cause) {
            describe(dsc, cause.getClass(), cause.getMessage());
            if (this.type.isAssignableFrom(cause.getClass())
                && this.msg.equals(cause.getMessage())) {
                matches = true;
            } else {
                matches = false;
            }
        }
        return matches;
    }

    /**
     * Add information about the exception to the hamcrest result message.
     *
     * @param dsc The description of the object.
     * @param type The exception type.
     * @param msg The exception message.
     */
    private static void describe(final Description dsc, final Class<?> type,
        final String msg) {
        dsc.appendText(
            new UncheckedText(
                new FormattedText(
                    "Exception has type '%s' and message '%s'",
                    type.getName(), msg
                )
            ).asString()
        );
    }
}
