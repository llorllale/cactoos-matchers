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
 * Test case for {@link HasValuesMatching}.
 *
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class HasValuesMatchingTest {

    /**
     * Example of {@link HasValues} usage.
     */
    @Test
    public void matches() {
        MatcherAssert.assertThat(
            "The matcher give the positive result for the valid arguments",
            new ListOf<>(1, 2, 3),
            new HasValuesMatching<>(value -> value > 1 || value == 3)
        );
    }

    /**
     * Give the negative testing result for the invalid arguments.
     */
    @Test
    public void matchSafely() {
        MatcherAssert.assertThat(
            "The matcher give the negative result for the invalid arguments",
            new HasValuesMatching<String>(
                text -> text.contains("simple")
            ).matchesSafely(
                new ListOf<>("I'm", "short", "sentence"),
                new StringDescription()
            ),
            new IsEqual<>(false)
        );
    }

    /**
     * Matcher prints the actual value(s) properly in case of errors.
     * The actual/expected section are using only when testing is failed and
     *  we need to explain what exactly went wrong.
    */
    @Test
    public void describeActualValues() {
        final Description description = new StringDescription();
        new HasValuesMatching<Integer>(value -> value > 5)
            .matchesSafely(new ListOf<>(1, 2, 3), description);
        MatcherAssert.assertThat(
            "The matcher print the value which came for testing",
            description.toString(),
            new IsEqual<>(
                "No any elements from [1, 2, 3] matches by the function"
            )
        );
    }

    /**
     * Matcher prints the expected value(s) properly.
     * The user has the ability to specify the description for the function.
     */
    @Test
    public void describeExpectedValues() {
        final Description description = new StringDescription();
        new HasValuesMatching<Integer>(
            "Iterable has at least 1 element greater than 5",
            value -> value > 5
        ).describeTo(description);
        MatcherAssert.assertThat(
            "The matcher print the optional description of the scenario",
            description.toString(),
            new IsEqual<>("Iterable has at least 1 element greater than 5")
        );
    }
}
