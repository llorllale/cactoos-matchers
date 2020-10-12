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
import org.junit.Test;

/**
 * Test case for {@link EndsWith}.
 *
 * @since 1.0.0
 */
public final class EndsWithTest {

    @Test
    public void matches() {
        new Assertion<>(
            "The matcher gives positive result for the valid arguments",
            new EndsWith("know it."),
            new Matches<>(new TextOf("I'm simple and I know it."))
        ).affirm();
    }

    @Test
    public void mismatches() {
        new Assertion<>(
            "The matcher gives negative result for the invalid arguments",
            new EndsWith("The sentence!"),
            new Mismatches<>(
                new TextOf("The sentence."),
                "Text ending with \"!\"",
                "Text is \"The sentence.\""
            )
        ).affirm();
    }
}
