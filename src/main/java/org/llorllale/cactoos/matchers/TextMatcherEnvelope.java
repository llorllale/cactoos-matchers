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
import org.cactoos.text.UncheckedText;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * The Text-based {@link TypeSafeDiagnosingMatcher} envelope.
 *
 * @since 1.0.0
 * @todo #16:30min Extends the envelope in TextIs, TextHasString, StartsWith,
 *  EndsWith. Most of operations with text like startsWith, endsWith, contains,
 *  equals, etc has the same algorithm. There is only difference in the function
 *  which should be applied to text.
 */
public abstract class TextMatcherEnvelope extends
    TypeSafeDiagnosingMatcher<Text> {

    /**
     * The description of the matcher's actual/expected values.
     */
    private final String description;

    /**
     * The matcher to test.
     */
    private final Matcher<Text> matcher;

    /**
     * Ctor.
     * @param mtchr The matcher to test.
     * @param desc The description of the matcher's actual/expected values.
     */
    public TextMatcherEnvelope(final Matcher<Text> mtchr, final String desc) {
        super();
        this.matcher = mtchr;
        this.description = desc;
    }

    @Override
    public final void describeTo(final Description desc) {
        desc.appendText(this.description).appendDescriptionOf(this.matcher);
    }

    @Override
    protected final boolean matchesSafely(final Text text,
        final Description desc) {
        desc.appendText(this.description).appendValue(
            new UncheckedText(text).asString()
        );
        return this.matcher.matches(text);
    }

}
