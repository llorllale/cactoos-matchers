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

import org.cactoos.Text;
import org.cactoos.io.InputOf;
import org.cactoos.io.Md5DigestOf;
import org.cactoos.text.HexOf;
import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.junit.Test;

/**
 * Test case for {@link TextHasString}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class TextHasStringTest {

    @Test
    public void hasClearDescriptionForFailedTest() throws Exception {
        new Assertion<>(
            "Description is not clear ",
            () -> {
                final HexOf hex = new HexOf(
                    new Md5DigestOf(
                        new InputOf("Hello World!")
                    )
                );
                final Description description = new StringDescription();
                final TextHasString matcher = new TextHasString(
                    "ed076287532e86365e841e92bfc50d8c6"
                );
                matcher.matchesSafely(hex, description);
                return description.toString();
            },
            new StringContains("Text is \"ed076287532e86365e841e92bfc50d8c\"")
        ).affirm();
    }

    @Test
    public void matchesPrefix() {
        new Assertion<>(
            "must match text prefix",
            () -> new TextHasString("123").matches((Text) () -> "12345"),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    public void matchesSuffix() {
        new Assertion<>(
            "must match text suffix",
            () -> new TextHasString("345").matches((Text) () -> "12345"),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    public void matchesInTheMiddle() {
        new Assertion<>(
            "must match random substring in the middle of the text",
            () -> new TextHasString("234").matches((Text) () -> "12345"),
            new IsEqual<>(true)
        ).affirm();
    }
}
