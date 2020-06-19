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

import org.cactoos.scalar.Constant;
import org.cactoos.scalar.Unchecked;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link ScalarHasValue}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ScalarHasValueTest {

    @Test
    public void matchesWithExpectedString() {
        final String expected = "some text";
        new Assertion<>(
            "Must match with expected string",
            new Unchecked<>(() -> expected),
            new ScalarHasValue<>(expected)
        ).affirm();
    }

    @Test
    public void matchesAsExpectedWithMatcher() {
        final String expected = "text";
        new Assertion<>(
            "Must match a with the given Matcher",
            new Unchecked<>(() -> expected),
            new ScalarHasValue<>(new IsEqual<>(expected))
        ).affirm();
    }

    @Test
    public void hasMatcherDescriptionForFailedTest() {
        new Assertion<>(
            "correctly describes a mismatch",
            new ScalarHasValue<>(new IsEqual<>("something")),
            new Mismatches<>(
                new Constant<>("something else"),
                "Scalar with \"something\"",
                "\"something else\""
            )
        ).affirm();
    }
}
