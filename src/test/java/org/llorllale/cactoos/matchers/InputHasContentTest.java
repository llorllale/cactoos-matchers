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

import org.cactoos.io.InputOf;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

/**
 * Test case for {@link org.llorllale.cactoos.matchers.InputHasContent}.
 *
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class InputHasContentTest {

    @Test
    public void matchesInputContent() {
        final String text = "Hello World!";
        new Assertion2<>(
            "matches input with equal contents",
            new InputHasContent(text),
            new Matches<>(new InputOf(text))
        ).affirm();
    }

    @Test
    public void mismatchInputContent() {
        new Assertion2<>(
            "does not match input with different contents",
            new InputHasContent("hello"),
            new IsNot<>(new Matches<>(new InputOf("world")))
        ).affirm();
    }

    @Test
    public void describesMismatch() {
        final Description description = new StringDescription();
        new InputHasContent("world").describeMismatchSafely(
            new InputOf("hello"), description
        );
        new Assertion2<>(
            "includes the specified contents when describing a mismatch",
            description.toString(),
            new IsEqual<>("has content \"hello\"")
        ).affirm();
    }

    @Test
    public void describesExpectedValues() {
        final Description description = new StringDescription();
        new InputHasContent("world").describeTo(description);
        new Assertion2<>(
            "includes the specified contents when describing itself",
            description.toString(),
            new IsEqual<>("has content \"world\"")
        ).affirm();
    }

}
