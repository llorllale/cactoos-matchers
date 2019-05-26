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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

/**
 * Test case for {@link Throws}.
 *
 * @since 1.0.0
 * @checkstyle StringLiteralsConcatenationCheck (200 lines)
 * @checkstyle JavadocMethodCheck (200 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ThrowsTest {

    /**
     * Example of {@link Throws} usage.
     */
    @Test
    public void matchPositive() {
        new Assertion<>(
            "matches scalar that throws the expected exception",
            new Throws<>("No object(s) found.", IllegalArgumentException.class),
            new Matches<>(
                () -> {
                    throw new IllegalArgumentException("No object(s) found.");
                }
            )
        ).affirm();
    }

    @Test
    public void matchNegative() {
        new Assertion<>(
            "mismatches scalar that doesn't throw any exception",
            new Throws<>("illegal arg", IllegalArgumentException.class),
            new IsNot<>(new Matches<>(() -> "no exception"))
        ).affirm();
    }

    @Test
    public void matchesSubtype() {
        new Assertion<>(
            "matches scalar that throws a subtype of the expected exception",
            new Throws<>("", IOException.class),
            new Matches<>(
                () -> {
                    throw new FileNotFoundException("");
                }
            )
        ).affirm();
    }

    @Test
    public void mismatchException() {
        new Assertion<>(
            // @checkstyle LineLength (1 line)
            "mismatches if exception thrown is not subtype of expected exception",
            new Throws<>("", IOException.class),
            new IsNot<>(
                new Matches<>(
                    () -> {
                        throw new IllegalArgumentException("");
                    }
                )
            )
        ).affirm();
    }

    @Test
    public void describeActualValues() {
        final Description description = new StringDescription();
        new Throws<>("NPE", Exception.class).matchesSafely(
            () -> {
                throw new IllegalArgumentException("No object(s) found.");
            },
            description
        );
        new Assertion<>(
            "describes the actual exception",
            description.toString(),
            new IsEqual<>(
                "Exception has type 'java.lang.IllegalArgumentException'"
                    + " and message 'No object(s) found.'"
            )
        ).affirm();
    }

    @Test
    public void describeExpectedValues() {
        final Description description = new StringDescription();
        new Throws<>("NPE", NullPointerException.class).describeTo(description);
        new Assertion<>(
            "describes the expected exception",
            description.toString(),
            new IsEqual<>(
                "Exception has type 'java.lang.NullPointerException'"
                    + " and message 'NPE'"
            )
        ).affirm();
    }

    @Test
    public void matchesMessageExpectation() {
        new Assertion<>(
            "matches message expectation",
            new Throws<>(
                IllegalArgumentException.class,
                msg -> msg.contains("some text")
            ),
            new Matches<>(
                () -> {
                    throw new IllegalArgumentException("some text and other");
                }
            )
        ).affirm();
    }

    @Test
    public void doesntMatchMessageExpectation() {
        new Assertion<>(
            "mismatches message expectation",
            new Throws<>(
                IllegalArgumentException.class,
                msg -> msg.contains("some text")
            ),
            new IsNot<>(
                new Matches<>(
                    () -> {
                        throw new IllegalArgumentException("different text");
                    }
                )
            )
        ).affirm();
    }

    @Test
    public void describesMessageExpectation() {
        final Description description = new StringDescription();
        new Throws<>(
            NullPointerException.class,
            Objects::nonNull,
            "message is whatever, just not null"
        ).describeTo(description);
        new Assertion<>(
            "describes the message expectation",
            description.toString(),
            new IsEqual<>(
                "Exception has type 'java.lang.NullPointerException'"
                    + " and message is whatever, just not null"
            )
        ).affirm();
    }
}
