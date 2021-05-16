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

import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.Scalar;
import org.cactoos.text.TextOf;

/**
 * Tests for {@link Assertion}.
 * @since 1.0.0
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings({"unchecked", "PMD.AvoidDuplicateLiterals",
    "PMD.TestClassWithoutTestCases"})
final class AssertionTest extends TestEnvelope {

    AssertionTest() {
        super(
            new Assertion<>(
                "Assertion can be affirmed if the operation being tested matches.",
                new Assertion<>(
                    "must affirm the assertion if the test's result is as expected",
                    new TextOf("abc123"),
                    new IsText("abc123")
                ),
                new Passes()
            ),
            new Assertion<>(
                "Assertion must be refuted if the operation being tested does not match.",
                new Assertion<>(
                    "must refute the assertion if the test's result is not as expected",
                    new TextOf("test"),
                    new IsText("no match")
                ),
                new FailsWith(
                    AssertionError.class
                )
            ),
            new Assertion<>(
                "Must not be affirmed if the operation being tested throws an unexpected error",
                new Assertion<Scalar<String>>(
                    "must fail if the test throws an unexpected error",
                    () -> {
                        throw new IllegalStateException();
                    },
                    new HasValue<>("no match")
                ),
                new FailsWith(
                    IllegalStateException.class
                )
            ),
            new Assertion<>(
                "Assertion can be affirmed if the operation being tested throws an expected error.",
                new Assertion<>(
                    "must affirm the assertion if the test throws the expected error",
                    () -> {
                        throw new IllegalStateException("this is a test");
                    },
                    new Throws<>("this is a test", IllegalStateException.class)
                ),
                new Passes()
            ),
            new AssertWith<>(
                new AtomicInteger(0),
                quantity -> new AssertAll(
                    "The scalar within Assertion executed is only once",
                    new Assertion<>(
                        "must match the exception",
                        () -> {
                            quantity.incrementAndGet();
                            throw new IllegalStateException("this is a test");
                        },
                        new Throws<>("this is a test", IllegalStateException.class)
                    ),
                    new Assertion<>(
                        "must execute scalar only once",
                        quantity::get,
                        new HasValue<>(1)
                    )
                )
            )
        );
    }
}
