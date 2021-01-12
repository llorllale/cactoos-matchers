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

import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Matches}.
 *
 * @since 1.0.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class MatchesTest {

    /**
     * Example of {@link Matches} usage.
     */
    @Test
    void matches() {
        new Assertion<>(
            "Matcher TextIs(abc) gives positive result for Text(abc)",
            new TextIs("abc"),
            new Matches<>(new TextOf("abc"))
        ).affirm();
    }

    /**
     * Gives negative testing result for the invalid arguments.
     */
    @Test
    void matchStatus() {
        new Assertion<>(
            "Matcher TextIs(abc) gives negative result for Text(def)",
            new Matches<>(new TextOf("def")).matches(new TextIs("abc")),
            new IsEqual<>(false)
        ).affirm();
    }

    /**
     * Matcher prints the actual value(s) properly.
     */
    @Test
    void describeActual() {
        final Description description = new StringDescription();
        new Matches<Text, TextIs>(new TextOf("expected")).matchesSafely(
            new TextIs("actual"), description
        );
        new Assertion<>(
            "describes the matcher",
            description.toString(),
            new IsEqual<>("Text with value \"actual\"")
        ).affirm();
    }

    /**
     * Matcher prints the expected value(s) properly.
     */
    @Test
    void describeExpected() {
        final Description description = new StringDescription();
        new Matches<>(new TextOf("expected")).describeTo(description);
        new Assertion<>(
            "describes the expected value",
            description.toString(),
            new IsEqual<>("<expected>")
        ).affirm();
    }
}
