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
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matches if a text equals this string.
 * @since 1.0.0
 */
public final class TextIs extends TypeSafeMatcher<Text> {
    /**
     * Prefix for description.
     */
    private static final String PREFIX = "Text with value ";

    /**
     * Matcher of the text.
     */
    private final Matcher<String> matcher;

    /**
     * Actual result for comparison.
     */
    private String result;

    /**
     * Ctor.
     * @param text The text to match against
     */
    public TextIs(final String text) {
        this(new TextOf(text));
    }

    /**
     * Ctor.
     * @param text The text to match against
     */
    public TextIs(final Text text) {
        this(
            new MatcherOf<>(
                (String input) -> input.equals(text.asString()),
                text
            )
        );
    }

    /**
     * Ctor.
     * @param matcher The text matcher
     */
    public TextIs(final Matcher<String> matcher) {
        super();
        this.matcher = matcher;
    }

    @Override
    public boolean matchesSafely(final Text item) {
        this.result = new UncheckedText(item).asString();
        return this.matcher.matches(this.result);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(TextIs.PREFIX);
        description.appendDescriptionOf(this.matcher);
    }

    @Override
    public void describeMismatchSafely(
        final Text item,
        final Description description) {
        description.appendText(TextIs.PREFIX);
        description.appendValue(this.result);
    }
}
