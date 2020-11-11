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

import org.cactoos.Text;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to test {@link org.hamcrest.Matcher} objects.
 *
 * <p>Here is an example:</p>
 * <pre>{@code
 *  new Assertion<>(
 *      "Can't describe a mismatch",
 *      new HasLines("Tom", "Mike"),
 *      new Mismatches<>(
 *          String.format("Tom%nJohn%n"),
 *          "Lines are <[Tom, Mike]>",
 *          "<[Tom, John]>"
 *      )
 *  ).affirm();
 *  }</pre>
 *
 * @param <X> Type of item.
 * @param <M> Type of tested matcher.
 * @since 1.0.0
 * @todo #106:30min Add extra tests for this class to validate all
 *  the different constructors (also add a constructor taking message
 *  as a String) and ensure they are coherent with how Assertion is
 *  working and throwing errors.
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class Mismatches<X, M extends Matcher<X>> extends
    TypeSafeDiagnosingMatcher<M> {

    /**
     * The testing arguments for the target matcher.
     */
    private final X args;

    /**
     * The expected mismatch message.
     */
    private final Text message;

    /**
     * Ctor.
     * @param args The testing arguments for the matcher.
     * @param expected The expected portion of the mismatch message.
     * @param actual The actual portion of the mismatch message.
     */
    public Mismatches(
        final X args,
        final String expected,
        final String actual
    ) {
        this(args, new TextOf(expected), new TextOf(actual));
    }

    /**
     * Ctor.
     * @param args The testing arguments for the matcher.
     * @param expected The expected portion of the mismatch message.
     * @param actual The actual portion of the mismatch message.
     */
    public Mismatches(
        final X args,
        final Text expected,
        final Text actual
    ) {
        this(
            args,
            new Joined(
                new TextOf(System.lineSeparator()),
                new TextOf(""),
                new Joined(new TextOf(""), new TextOf("Expected: "), expected),
                new Joined(new TextOf(""), new TextOf(" but was: "), actual)
            )
        );
    }

    /**
     * Ctor.
     * @param args The testing arguments for the matcher.
     * @param message The expected mismatch message.
     */
    public Mismatches(final X args, final Text message) {
        super();
        this.args = args;
        this.message = message;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("Mismatches ")
            .appendValue(this.args)
            .appendText(" with message ")
            .appendValue(this.message);
    }

    // @todo #106:30min Add a description in case the mismatch fails.
    //  And then introduce some tests to validate that the description
    //  is properly constructed.
    @Override
    protected boolean matchesSafely(final M matcher, final Description dsc) {
        boolean mismatch;
        try {
            new Assertion<>("", this.args, matcher).affirm();
            mismatch = false;
        } catch (final AssertionError err) {
            mismatch = err.getMessage().equals(
                new UncheckedText(this.message).asString()
            );
        }
        return mismatch;
    }
}
