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

import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;

/**
 * Test case for {@link MatcherEnvelope}.
 *
 * @since 1.0.0
 * @checkstyle EmptyLinesCheck (500 line)
 */
public final class MatcherEnvelopeTest {

    /**
     * Ensures that MatcherEnvelope encapsulates given test.
     */
    @Test
    public void encapsulatesTest() {
        new Assertion<>(
            "Doesn't encapsulate the test",
            () -> true,
            new MatcherEnvelope<Boolean>(
                ipt -> ipt,
                desc -> {

                },
                (res, desc) -> {

                }
            ) {

            }
        ).affirm();
    }

    /**
     * Ensures that MatcherEnvelope can describe itself.
     */
    @Test
    public void describesItself() {
        final String message = "description";
        final Description description = new StringDescription();
        new MatcherEnvelope<Boolean>(
            ipt -> ipt,
            desc -> desc.appendText(message),
            (res, desc) -> {

            }
        ) {

        }.describeTo(description);
        new Assertion<>(
            "Given strings are not equal",
            () -> new TextOf(message),
            new TextIs(description.toString())
        ).affirm();
    }

    /**
     * Ensures that MatcherEnvelope can describe a mismatch.
     */
    @Test
    public void describesMismatch() {
        final String message = new UncheckedText(
            new FormattedText(
                "Mismatch, expected: %d, actual: %d",
                1,
                    2
            )
        ).asString();
        final Description description = new StringDescription();
        new MatcherEnvelope<Integer>(
            ipt -> ipt == 1,
            desc -> {

            },
            (res, desc) -> desc.appendText(message)
        ) {

        }.describeMismatchSafely(2, description);
        new Assertion<>(
            "Given texts are not equal",
            () -> new TextOf(message),
            new TextIs(description.toString())
        ).affirm();
    }
}
