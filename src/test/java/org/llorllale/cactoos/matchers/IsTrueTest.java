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

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link IsTrue}.
 *
 * @since 1.0.0
 */
public final class IsTrueTest {

    /**
     * Example of {@link IsTrue} usage.
     */
    @Test
    public void matchPositive() {
        new Assertion<>(
            "The matcher gives positive result for valid argument.",
            () -> true, new IsTrue()
        ).affirm();
    }

    /**
     * Give the negative testing result for the invalid arguments.
     */
    @Test
    public void matchNegative() {
        new Assertion<>(
            "The matcher gives negative result for invalid argument.",
            () -> new IsTrue().matchesSafely(false, new StringDescription()),
            new IsEqual<>(false)
        ).affirm();
    }

    /**
     * Matcher prints the actual value(s) properly in case of errors.
     * The actual/expected section are using only when testing is failed and
     *  we need to explain what exactly went wrong.
     */
    @Test
    public void describeActualValues() {
        new Assertion<>(
            "The matcher print the value which came for testing",
            () -> {
                final Description desc = new StringDescription();
                new IsTrue().matchesSafely(false, desc);
                return desc.toString();
            },
            new IsEqual<>("<false>")
        ).affirm();
    }

    /**
     * Matcher prints the expected value(s) properly.
     * The user has the ability to specify the description for the function.
     */
    @Test
    public void describeExpectedValues() {
        new Assertion<>(
            "The matcher print the details about expected value",
            () -> {
                final Description desc = new StringDescription();
                new IsTrue().describeTo(desc);
                return desc.toString();
            },
            new IsEqual<>("<true>")
        ).affirm();
    }
}
