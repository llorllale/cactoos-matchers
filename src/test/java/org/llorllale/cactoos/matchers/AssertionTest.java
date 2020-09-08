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

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Scalar;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Tests for {@link Assertion}.
 * @since 1.0.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class AssertionTest {
    /**
     * Assertion can be affirmed if the operation being tested matches.
     */
    @Test
    public void affirmIfResultMatches() {
        final String expected = "abc123";
        new Assertion<>(
            "must affirm the assertion if the test's result is as expected",
            new TextOf(expected),
            new TextIs(expected)
        ).affirm();
    }

    /**
     * Assertion must be refuted if the operation being tested does not
     * match.
     *
     * @throws IOException if something goes wrong.
     */
    @Test
    public void refuteIfResultDoesNotMatch() throws IOException {
        Assertions.assertThrows(
            AssertionError.class,
            () -> new Assertion<>(
                "must refute the assertion if the test's result is not as expected",
                new TextOf("test"),
                new TextIs("no match")
            ).affirm(),
            new Joined(
                System.lineSeparator(),
                "must refute the assertion if the test's result is not as expected",
                "Expected: Text with value \"no match\"",
                " but was: Text is \"test\""
            ).asString()
        );
    }

    /**
     * Assertion cannot be affirmed if the operation being tested throws an
     * unexpected error.
     */
    @Test
    public void refuteIfErrorDoesNotMatch() {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new Assertion<Scalar<String>>(
                "must fail if the test throws an unexpected error",
                () -> {
                    throw new IllegalStateException();
                },
                new ScalarHasValue<>("no match")
            ).affirm()
        );
    }

    /**
     * Assertion can be affirmed if the operation being tested throws an
     * expected error.
     */
    @Test
    public void affirmIfErrorMatches() {
        new Assertion<>(
            "must affirm the assertion if the test throws the expected error",
            () -> {
                throw new IllegalStateException("this is a test");
            },
            new Throws<>("this is a test", IllegalStateException.class)
        ).affirm();
    }

    /**
     * The scalar within Assertion executed is only once.
     */
    @Test
    public void scalarIsExecutedOnce() {
        final AtomicInteger quantity = new AtomicInteger(0);
        new Assertion<>(
            "must match the exception",
            () -> {
                quantity.incrementAndGet();
                throw new IllegalStateException("this is a test");
            },
            new Throws<>("this is a test", IllegalStateException.class)
        ).affirm();
        new Assertion<>(
            "must execute scalar only once",
            quantity.get(),
            new IsEqual<>(1)
        ).affirm();
    }
}
