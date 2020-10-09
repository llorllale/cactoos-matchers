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
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Generic {@link Matcher} of {@link Text}.
 *
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 * @todo #165:30m Add a constructor to TextMatcher taking a Func<String, Boolean> in order to simplify this TextMatcher:
 *  new TextMatcher(
 *      new MatcherOf<>(
 *          (String act) -> act.endsWith(text.asString()),
 *              desc -> desc.appendText(text.asString()),
 *               (actual, desc) -> desc.appendValue(actual)
 *          ),
 *          "Text ending with "
 *      )
 */
public final class TextMatcher extends TypeSafeDiagnosingMatcher<Text> {

    /**
     * The matcher to test.
     */
    private final Matcher<String> matcher;

    /**
     * The description of the matcher's expected text.
     */
    private final String expected;

    /**
     * The description of the matcher's actual text.
     */
    private final String actual;

    /**
     * Ctor.
     * @param mtchr The matcher to test.
     * @param expected The description of the matcher's expected text.
     */
    public TextMatcher(
        final Matcher<String> mtchr, final String expected
    ) {
        this(mtchr, expected, "Text is ");
    }

    /**
     * Ctor.
     * @param mtchr The matcher to test.
     * @param expected The description of the matcher's expected text.
     * @param actual The description of the matcher's actual text.
     */
    public TextMatcher(
        final Matcher<String> mtchr, final String expected, final String actual
    ) {
        super();
        this.matcher = mtchr;
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText(this.expected).appendDescriptionOf(this.matcher);
    }

    @Override
    protected boolean matchesSafely(final Text text, final Description desc) {
        final String txt = new UncheckedText(text).asString();
        desc.appendText(this.actual).appendValue(txt);
        return this.matcher.matches(txt);
    }
}
