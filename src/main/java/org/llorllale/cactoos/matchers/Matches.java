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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to test {@link org.hamcrest.Matcher} objects.
 *
 * <p>Here is an example:</p>
 * <pre>
 * <code>
 *  &#64;Test
 *  public void matches() {
 *      new Assertion&#60;&#62;(
 *          "must match",
 *          new TextIs("abc"),
 *          new Matches&#60;&#62;(new TextOf("abc"))
 *      ).affirm();
 *  }
 * </code>
 * </pre>
 *
 * @param <X> Type of item.
 * @param <M> Type of tested matcher.
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class Matches<X, M extends Matcher<X>> extends
    TypeSafeDiagnosingMatcher<M> {

    /**
     * The testing arguments for the target matcher.
     */
    private final X args;

    /**
     * Ctor.
     * @param args The testing arguments for the matcher.
     */
    public Matches(final X args) {
        super();
        this.args = args;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendValue(this.args);
    }

    @Override
    protected boolean matchesSafely(final M matcher, final Description dsc) {
        matcher.describeTo(dsc);
        return matcher.matches(this.args);
    }
}
