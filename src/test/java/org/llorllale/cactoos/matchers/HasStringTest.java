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

import org.cactoos.text.TextOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link HasString}.
 *
 * @since 0.29
 * @checkstyle ClassDataAbstractionCoupling (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class HasStringTest {

    @Test
    void matchesPrefix() {
        new Assertion<>(
            "matches prefix",
            new HasString("123"),
            new Matches<>(new TextOf("12345"))
        ).affirm();
    }

    @Test
    void matchesSuffix() {
        new Assertion<>(
            "matches suffix",
            new HasString("345"),
            new Matches<>(new TextOf("12345"))
        ).affirm();
    }

    @Test
    void matchesInTheMiddle() {
        new Assertion<>(
            "matches substring",
            new HasString("234"),
            new Matches<>(new TextOf("12345"))
        ).affirm();
    }

    @Test
    void mismatch() {
        new Assertion<>(
            "does not match text not containing the given string",
            new HasString("xyz"),
            new IsNot<>(new Matches<>(new TextOf("abc")))
        ).affirm();
    }

    @Test
    void describesMismatch() {
        new Assertion<>(
            "describes mismatch correctly",
            new HasString("xyz456"),
            new Mismatches<>(
                new TextOf("abc123"),
                "Text containing \"xyz456\"",
                "Text with value \"abc123\""
            )
        ).affirm();
    }
}
