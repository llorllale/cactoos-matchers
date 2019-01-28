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
import org.cactoos.scalar.UncheckedScalar;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test case for {@link ScalarHasValue}.
 *
 * @since 1.0
 * @todo #81:30min Replace all uses of MatcherAssert.assertThat() with
 *  Assertion. Ensure that the tests behavior wasn't changed during this
 *  refactoring. Each test should have single Assertion statement.
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ScalarHasValueTest {

    /**
     * A rule for handling an exception.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void matchesAsExpectedWithString() {
        final String expected = "some text";
        new Assertion<>(
            "must match with expected string",
            () -> new UncheckedScalar<>(() -> expected),
            new ScalarHasValue<>(expected)
        ).affirm();
    }

    @Test
    public void matchesAsExpectedWithMatcher() {
        final String expected = "text";
        new Assertion<>(
            "must match a Matcher",
            () -> new UncheckedScalar<>(() -> expected),
            new ScalarHasValue<>(new IsEqual<>(expected))
        ).affirm();
    }

    @Test
    public void hasMatcherDescriptionForFailedTest() {
        this.exception.expect(AssertionError.class);
        this.exception.expectMessage(
            new StringContains("Expected: Scalar with \"something\"")
        );
        new Assertion<>(
            "missing matcher description",
            () -> new Constant<>("something else"),
            new ScalarHasValue<>(new IsEqual<>("something"))
        ).affirm();
    }

    @Test
    public void hasMismatchDescriptionForFailedTest() {
        this.exception.expect(AssertionError.class);
        this.exception.expectMessage(
            new StringContains("but was: was \"actual\"")
        );
        new Assertion<>(
            "missing mismatch description",
            () -> new Constant<>("actual"),
            new ScalarHasValue<>(new IsEqual<>("expected"))
        ).affirm();
    }
}
