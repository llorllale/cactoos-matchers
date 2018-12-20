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
 * Test case for {@link NotBlank}.
 *
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class NotBlankTest {

    @Test
    public void blank() {
        new Assertion<>(
            "must not match an empty string",
            () -> new NotBlank().matchesSafely(
                "", new Description.NullDescription()
            ),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    public void notBlank() {
        new Assertion<>(
            "must match a non-empty string",
            () -> new NotBlank().matchesSafely(
                "-.$%", new Description.NullDescription()
            ),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    public void nonBlankMessage() {
        final Description desc = new StringDescription();
        new Assertion<>(
            "must match a non-empty string",
            () -> new NotBlank().matchesSafely("text", desc),
            new IsEqual<>(true)
        ).affirm();
        new Assertion<>(
            "must describe itself in terms of the text being matched against",
            desc::toString,
            new IsEqual<>("\"text\"")
        ).affirm();
    }
}
