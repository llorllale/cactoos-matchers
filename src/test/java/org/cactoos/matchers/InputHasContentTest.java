/**
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
package org.cactoos.matchers;

import org.cactoos.Input;
import org.cactoos.io.InputOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.InputHasContent;

/**
 * Test case for {@link InputHasContent}.
 *
 * @author Vedran Vatavuk (123vgv@gmail.com)
 * @version $Id$
 * @since 1.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class InputHasContentTest {

    @Test
    public void matchesInputContent() throws Exception {
        final String text = "Hello World!";
        final Input input = new InputOf(text);
        final InputHasContent matcher = new InputHasContent(text);
        MatcherAssert.assertThat(
            "Matcher does not match equal values",
            matcher.matchesSafely(input),
            new IsEqual<>(true)
        );
    }

    @Test
    public void failsIfContentDoesNotMatch() throws Exception {
        final Input input = new InputOf("hello");
        final InputHasContent matcher = new InputHasContent("world");
        MatcherAssert.assertThat(
            "Matcher matches values that are not equal",
            matcher.matchesSafely(input),
            new IsEqual<>(false)
        );
    }
}
