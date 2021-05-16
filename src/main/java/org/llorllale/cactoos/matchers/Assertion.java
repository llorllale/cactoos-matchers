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
import org.cactoos.text.TextOf;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

/**
 * An assertion that can be affirmed in tests.
 * <p>
 * <strong>Examples:</strong>
 * <p>
 * Testing a value:
 * <pre>
 * {@code
 *     public void test() {
 *         new Assertion<>(
 *             "must match the string",
 *             new TextOf("string"),
 *             new IsText("string")
 *         ).affirm();    // value is affirmed
 *     }
 * }
 * </pre>
 *
 * Testing an error (see {@link Throws}):
 * <pre>
 * {@code
 *     public void test() {
 *         new Assertion<>(
 *             "must match the error",
 *             () -> { throw new IllegalArgumentException("error msg"); },
 *             new Throws("error msg", IllegalArgumentException.class)
 *         ).affirm();    // error is affirmed
 *     }
 * }
 * </pre>
 *
 * @param <T> The type of the result returned by the operation under test
 * @since 1.0.0
 * @todo #18:30min Replace all uses of MatcherAssert.assertThat() with
 *  Assertion, and ban all overloads of the former in forbidden-apis.txt.
 *  We should also look into banning common matchers like Matchers.is(), etc.
 */
public final class Assertion<T> implements Assert {

    /**
     * Message.
     */
    private final String msg;

    /**
     * Tested value.
     */
    private final T test;

    /**
     * Matcher.
     */
    private final Matcher<? super T> matcher;

    /**
     * Ctor.
     * @param msg Assertive statement.
     * @param test Object under test.
     * @param matcher Tester.
     */
    public Assertion(
        final String msg, final T test, final Matcher<? super T> matcher
    ) {
        this.msg = msg;
        this.test = test;
        this.matcher = matcher;
    }

    @Override
    public void affirm() throws AssertionError {
        if (!this.matcher.matches(this.test)) {
            final Description text = new StringDescription();
            text
                .appendText(this.msg)
                .appendText(System.lineSeparator())
                .appendText("Expected: ")
                .appendDescriptionOf(this.matcher)
                .appendText(System.lineSeparator())
                .appendText("     but: ");
            this.matcher.describeMismatch(this.test, text);
            throw new AssertionError(text.toString());
        }
    }

    @Override
    public Text description() {
        return new TextOf(this.msg);
    }
}
