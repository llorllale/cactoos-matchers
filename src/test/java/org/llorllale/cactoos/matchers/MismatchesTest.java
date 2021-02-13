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
 * Test case for {@link Mismatches}.
 *
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class MismatchesTest {

    /**
     * Example of {@link Mismatches} usage.
     */
    @Test
    void example() {
        new Assertion<>(
            "Must mismatch properly",
            new IsText("abc"),
            new Mismatches<>(
                new TextOf("def"),
                "Text with value \"abc\"",
                "Text with value \"def\""
            )
        ).affirm();
    }

    /**
     * Matcher prints nothing when mismatches with right message.
     */
    @Test
    void mismatchesWithRightMessage() {
        final Description description = new StringDescription();
        new Mismatches<Text, IsText>(
            new TextOf("e"),
            "Text with value \"actual\"",
            "Text with value \"e\""
        ).matchesSafely(
            new IsText("actual"), description
        );
        new Assertion<>(
            "no description when mismatches with right message",
            description.toString(),
            new IsEqual<>("")
        ).affirm();
    }

    /**
     * Matcher prints description when mismatches with wrong message.
     */
    @Test
    void mismatchesWithWrongMessage() {
        final Description description = new StringDescription();
        new Mismatches<Text, IsText>(
            new TextOf("c"),
            "Text with value \"c\"",
            "Text with value \"c\""
        ).matchesSafely(
            new IsText("expec"), description
        );
        new Assertion<>(
            "describes when mismatches with the wrong message",
            description.toString(),
            new IsEqual<>("\nExpected: Text with value \"expec\"\n but was: Text with value \"c\"")
        ).affirm();
    }

    /**
     * Matcher prints the actual value(s) properly.
     */
    @Test
    void describeActual() {
        final Description description = new StringDescription();
        new Mismatches<Text, IsText>(
            new TextOf("e"),
            new TextOf("Mismatches <a> with message <e>")
        ).matchesSafely(
            new IsText("e"), description
        );
        new Assertion<>(
            "describes the matcher",
            description.toString(),
            new IsEqual<>("\nExpected: Text with value \"e\"\n but was: Text with value \"e\"")
        ).affirm();
    }

    @Test
    void matches() {
        new Assertion<>(
            "Must fail to mismatch",
            new Mismatches<>(new TextOf("a"), new TextOf("expected")),
            new Mismatches<>(
                new IsText("a"),
                "Mismatches <a> with message <expected>",
                "\nExpected: Text with value \"a\"\n but was: Text with value \"a\""
           )
        ).affirm();
    }
}
