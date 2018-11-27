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
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher to test {@link org.hamcrest.Matcher} objects.
 *
 * <p>Here is a few examples how we are testing matchers
 *  without/with {@link IsMatcherOf}:</p>
 * <pre>{@code
 *  @Test
 *  public void matchesSafelyWithout() {
 *      final Description expected = new StringDescription();
 *      final Description actual = new StringDescription();
 *      final HasValues<Integer> hvalues = new HasValues<>(5);
 *      hvalues.describeTo(expected);
 *      MatcherAssert.assertThat(
 *          "The testing status is 'false'",
 *          hvalues.matchesSafely(new ListOf<>(1, 2, 3), actual),
 *          new IsEqual<>(false)
 *      );
 *      MatcherAssert.assertThat(
 *          "The actual result message in hamcrest terms is correct",
 *          actual.toString(),
 *          new IsEqual<>("The function applied to <[1, 2, 3]> is failed")
 *      );
 *  }
 *
 *  @Test
 *  public void matchesSafelyWith() {
 *      MatcherAssert.assertThat(
 *          new HasValues<>(5),
 *          new IsMatcherOf<>(
 *              false, new ListOf<>(1, 2, 3),
 *               "The function applied to <[1, 2, 3]> is failed"
 *          )
 *       );
 *   }
 *
 * }</pre>
 *
 * @param <X> Type of item.
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class IsMatcherOf<X> extends
    TypeSafeDiagnosingMatcher<Matcher<X>> {

    /**
     * The expected testing status within matcher.
     */
    private final boolean status;

    /**
     * The expected message with testing details from target matcher.
     */
    private final String exp;

    /**
     * The actual message with testing details from target matcher.
     */
    private final String act;

    /**
     * The testing arguments for the target matcher.
     */
    private final X margs;

    /**
     * Ctor.
     * @param status The expected testing status within matcher.
     * @param margs The testing arguments for the matcher.
     */
    public IsMatcherOf(final boolean status, final X margs) {
        this(status, margs, "");
    }

    /**
     * Ctor.
     * @param status The expected testing status within matcher.
     * @param margs The testing arguments for the matcher.
     * @param act The actual message with testing details from target matcher.
     */
    public IsMatcherOf(final boolean status, final X margs, final String act) {
        this(status, margs, act, "");
    }

    /**
     * Ctor.
     * @param status The expected testing status within matcher.
     * @param margs The testing arguments for the matcher.
     * @param act The actual message with testing details from target matcher.
     * @param exp The expected message with testing details from target matcher.
     * @checkstyle ParameterNumberCheck (5 lines)
     */
    public IsMatcherOf(final boolean status, final X margs, final String act,
        final String exp) {
        super();
        this.status = status;
        this.margs = margs;
        this.act = act;
        this.exp = exp;
    }

    @Override
    public void describeTo(final Description desc) {
        this.describe(this.status, this.exp, this.act, desc);
    }

    @Override
    protected boolean matchesSafely(final Matcher<X> mtcr,
        final Description dsc) {
        final Description expected = new StringDescription();
        final Description actual = new StringDescription();
        mtcr.describeMismatch(this.margs, actual);
        mtcr.describeTo(expected);
        final boolean matched;
        if (this.exp.trim().isEmpty() || this.act.trim().isEmpty()) {
            matched = this.status == mtcr.matches(this.margs);
            this.describe(matched, "", "", dsc);
        } else {
            matched = this.status == mtcr.matches(this.margs)
                && this.act.equals(actual.toString())
                && this.exp.equals(expected.toString());
            this.describe(matched, actual.toString(), expected.toString(), dsc);
        }
        return matched;
    }

    /**
     * Describe testing result of target matcher in hamcrest terms.
     * @param sttus The expected testing status within matcher
     * @param ectd The expected message with testing details from target matcher
     * @param actl The actual message with testing details from target matcher
     * @param desc The hamcrest description of the testing scenario
     * @checkstyle ParameterNumberCheck (5 lines)
     * @checkstyle NonStaticMethodCheck (5 lines)
     */
    private void describe(final boolean sttus, final String ectd,
        final String actl, final Description desc) {
        desc.appendText("Testing status is ").appendValue(sttus);
        if (!ectd.trim().isEmpty()) {
            desc.appendText(", expected message is ").appendValue(ectd);
        }
        if (!actl.trim().isEmpty()) {
            desc.appendText(", actual message is ").appendValue(actl);
        }
        desc.appendText(".");
    }

}
