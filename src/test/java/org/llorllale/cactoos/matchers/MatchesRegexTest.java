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

import org.cactoos.text.TextOf;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

/**
 * Test case for {@link MatchesRegex}.
 *
 * @since 1.0.0
 */
public final class MatchesRegexTest {

    /**
     * Example of {@link MatchesRegex} usage.
     */
    @Test
    public void matchPositive() {
        new Assertion2<>(
            "matches text that satisfies regex",
            new MatchesRegex("^.*know\\sit\\.$"),
            new Matches<>(new TextOf("I'm simple and I know it."))
        ).affirm();
    }

    /**
     * Give the negative testing result for the invalid arguments.
     */
    @Test
    public void matchNegative() {
        new Assertion2<>(
            "does not match text that does not conform to the regex",
            new MatchesRegex("^.*!$"),
            new IsNot<>(new Matches<>(() -> "The sentence."))
        ).affirm();
    }

    /**
     * Matcher prints the actual value(s) properly in case of errors.
     * The actual/expected section are using only when testing is failed and
     *  we need to explain what exactly went wrong.
     */
    @Test
    public void describeActualValues() {
        final Description desc = new StringDescription();
        new MatchesRegex("").matchesSafely(new TextOf("ABC"), desc);
        new Assertion2<>(
            "includes the test object in the description",
            desc.toString(),
            new IsEqual<>("Text is \"ABC\"")
        ).affirm();
    }

    /**
     * Matcher prints the expected value(s) properly.
     * The user has the ability to specify the description for the function.
     */
    @Test
    public void describeExpectedValues() {
        final Description desc = new StringDescription();
        new MatchesRegex("^.*\\.$").describeTo(desc);
        new Assertion2<>(
            "describes the scenario",
            desc.toString(),
            new IsEqual<>("Text matches \"^.*\\.$\"")
        ).affirm();
    }
}
