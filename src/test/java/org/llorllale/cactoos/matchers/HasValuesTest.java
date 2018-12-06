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

import org.cactoos.list.ListOf;
import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for{@link HasValues}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle RegexpSinglelineCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class HasValuesTest {

    /**
     * Example of {@link HasValues} usage.
     */
    @Test
    public void matches() {
        MatcherAssert.assertThat(
            "The matcher check that [1,2,3] contains [2]",
            new ListOf<>(1, 2, 3),
            new HasValues<>(2)
        );
    }

    /**
     * Give the positive testing result for the valid arguments.
     */
    @Test
    public void matchSafely() {
        MatcherAssert.assertThat(
            "The matcher check that [a,b,c,e] contains [a,b]",
            new HasValues<>("a", "b").matchesSafely(
                new ListOf<>("a", "b", "c", "e"),
                new StringDescription()
            ),
            new IsEqual<>(true)
        );
    }

    /**
     * Matcher prints the actual value(s) properly.
     *
     * Once test is failed the hamcrest throw the {@link AssertionError}.
     * The error has a message with expected/actual results.
     *
     * This test is required to check that the result hamcrest message
     * has proper "actual section". For example the test
     * <pre>{@code
     * MatcherAssert.assertThat(
     *    new ListOf<>(1, 2, 3),
     *    new HasValues<>(5)
     * );
     * }</pre> will fail with the following message
     * <pre>{@code
     * java.lang.AssertionError:
     *   Expected: <5>
     *   but: <1, 2, 3>
     * }</pre>, where
     *  - the "actual section" is "<1, 2, 3>"
     *  - the "expected section" is "<5>".
     */
    @Test
    public void describeActualValues() {
        final Description description = new StringDescription();
        new HasValues<>(5).matchesSafely(new ListOf<>(1, 2, 3), description);
        MatcherAssert.assertThat(
            "The matcher print the value which came for testing",
            description.toString(),
            new IsEqual<>("<1, 2, 3>")
        );
    }

    /**
     * Matcher prints the expected value(s) properly.
     */
    @Test
    public void describeExpectedValues() {
        final Description description = new StringDescription();
        new HasValues<>(3, 4).describeTo(description);
        MatcherAssert.assertThat(
            "The matcher print the value which should be present in the "
                + "target iterable",
            description.toString(),
            new IsEqual<>("<3, 4>")
        );
    }
}
