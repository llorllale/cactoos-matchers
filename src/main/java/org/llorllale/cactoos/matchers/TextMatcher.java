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

import org.cactoos.BiFunc;
import org.cactoos.Text;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Generic {@link Matcher} of {@link Text}.
 *
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class TextMatcher extends TypeSafeMatcher<Text> {

    /**
     * The matcher to test.
     */
    private final Matcher<String> matcher;

    /**
     * Ctor.
     * @param text The text to match against.
     * @param func Function that compares actual to expected value.
     * @param expected The description of the matcher's expected text.
     */
    public TextMatcher(
        final Text text,
        final BiFunc<String, String, Boolean> func,
        final String expected
    ) {
        this(text, func, expected, "Text with value");
    }

    /**
     * Ctor.
     * @param text The text to match against.
     * @param func Function that compares actual to expected value.
     * @param expected The prefix of the matcher's expected text.
     * @param actual The prefix of the actual text.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public TextMatcher(
        final Text text,
        final BiFunc<String, String, Boolean> func,
        final String expected,
        final String actual
    ) {
        this(
            new MatcherOf<>(
                act -> func.apply(act, text.asString()),
                desc -> desc
                    .appendText(expected)
                    .appendText(" ")
                    .appendValue(text.asString()),
                (act, desc) -> desc
                    .appendText(actual)
                    .appendText(" ")
                    .appendValue(act)
            )
        );
    }

    /**
     * Ctor.
     * @param mtchr The matcher to test.
     */
    public TextMatcher(final Matcher<String> mtchr) {
        super();
        this.matcher = mtchr;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendDescriptionOf(this.matcher);
    }

    @Override
    protected boolean matchesSafely(final Text text) {
        return this.matcher.matches(new UncheckedText(text).asString());
    }

    @Override
    protected void describeMismatchSafely(final Text text, final Description desc) {
        this.matcher.describeMismatch(new UncheckedText(text).asString(), desc);
    }
}
