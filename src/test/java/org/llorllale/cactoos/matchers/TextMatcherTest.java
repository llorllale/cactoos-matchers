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

import java.io.StringReader;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TextMatcher}.
 *
 * @since 1.0.0
 * @checkstyle JavadocTypeCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class TextMatcherTest {
    @Test
    void matchesAReadOnceInput() {
        final String input = "aaaa";
        new Assertion<>(
            "must match on an input that can be read only once",
            new TextMatcher(new IsEqual<>(input)),
            new Matches<>(new TextOf(new StringReader(input)))
        ).affirm();
    }

    @Test
    void mismatches() {
        new Assertion<>(
            "mismatches text without the prefix",
            new TextMatcher(
                new TextOf("!"),
                (act, txt) -> act.startsWith(txt),
                "Text starting with"
            ),
            new Mismatches<>(
                new TextOf("The sentence."),
                "Text starting with \"!\"",
                "Text with value \"The sentence.\""
            )
        ).affirm();
    }

    @Test
    void matchesFromMatcher() {
        new Assertion<>(
            "must match with matcher",
            new TextMatcher(new IsBlank()),
            new Matches<>(new TextOf(""))
        ).affirm();
    }

    @Test
    void matchesFromFunc() {
        new Assertion<>(
            "must match with function",
            new TextMatcher(
                new TextOf("I am"),
                (act, txt) -> act.startsWith(txt),
                "Text starts with"
            ),
            new Matches<>(new TextOf("I am happy."))
        ).affirm();
    }
}
