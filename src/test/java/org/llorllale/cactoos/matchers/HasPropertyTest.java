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

import java.util.Properties;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.PropertiesOf;
import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.Test;

/**
 * Test case for {@link HasProperty}.
 *
 * @since 1.0.0
 * @checkstyle ClassDataAbstractionCoupling (100 lines)
 */
public final class HasPropertyTest {

    /**
     * Simple positive case.
     */
    @Test
    public void positiveMatch() {
        new Assertion<>(
            "must match 'a=1'",
            new ScalarHasValue<>(new HasProperty("a", "1")),
            new Matches<>(new PropertiesOf("a=1"))
        ).affirm();
    }

    /**
     * Simple positive case only for key.
     */
    @Test
    public void positiveMachKey() {
        new Assertion<>(
            "must match 'b=...'",
            new ScalarHasValue<>(new HasProperty("b")),
            new Matches<>(new PropertiesOf("b=..."))
        ).affirm();
    }

    /**
     * Positive case with Matchers.
     */
    @Test
    public void positiveFuzzyMatch() {
        new Assertion<>(
            "must match 'f=q'",
            new ScalarHasValue<>(
                new HasProperty(
                    new IsEqualIgnoringCase("F"),
                    new IsEqualIgnoringCase("Q")
                )
            ),
            new AllOf<>(
                new ListOf<Matcher<? super ScalarHasValue<Properties>>>(
                    new Matches<>(new PropertiesOf("F=q")),
                    new Matches<>(new PropertiesOf("f=Q")),
                    new Matches<>(new PropertiesOf("f=q"))
                )
            )
        ).affirm();
    }

    /**
     * Negative case.
     */
    @Test
    public void negativeMatch() {
        new Assertion<>(
            "must mismatches 'abc=2' & 'xyz=1'",
            new ScalarHasValue<>(new HasProperty("abc", "1")),
            new AllOf<>(
                new ListOf<Matcher<? super ScalarHasValue<Properties>>>(
                    new IsNot<>(new Matches<>(new PropertiesOf("abc=2"))),
                    new IsNot<>(new Matches<>(new PropertiesOf("xyz=1")))
                )
            )
        ).affirm();
    }

    /**
     * Test for mismatch description readability.
     *
     * @todo #67:30m Make sure {@link Matcher#describeMismatch} of the original
     *  matcher is called by {@link ScalarHasValue}.
     *  And then adjust this test with the message originating
     *  from {@link HasProperty}.
     */
    @Test
    public void describesCorrectly() {
        new Assertion<>(
            "must have a message that describes the properties",
            new ScalarHasValue<>(new HasProperty("c", "3")),
            new Mismatches<>(
                new PropertiesOf("b=2"),
                "Scalar with has property key \"c\", value \"3\"",
                "<{b=2}>"
            )
        ).affirm();
    }
}

