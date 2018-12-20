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

import java.io.ByteArrayOutputStream;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.cactoos.text.TextOf;
import org.hamcrest.StringDescription;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link TeeInputHasResult}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class TeeInputHasResultTest {

    @Test
    public void failsIfInputDoesNotMatchExpectedValue() {
        new Assertion<>(
            "Matcher does not compare input and expected values",
            () -> {
                final TeeInput matchable = new TeeInput(
                    new TextOf("input"),
                    new OutputTo(new ByteArrayOutputStream())
                );
                final String expected = "expected-1";
                final TeeInputHasResult matcher = new TeeInputHasResult(
                    expected, new TextOf(expected)
                );
                return matcher.matchesSafely(matchable);
            },
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    public void describesMismatchOfInputAndExpected() {
        new Assertion<>(
            "Description of mismatch of input and expected incorrect",
            () -> {
                final TeeInput matchable = new TeeInput(
                    new TextOf("i"),
                    new OutputTo(new ByteArrayOutputStream())
                );
                final TeeInputHasResult matcher = new TeeInputHasResult(
                    "e", new TextOf("e")
                );
                final StringDescription description = new StringDescription();
                matcher.matchesSafely(matchable);
                matcher.describeMismatchSafely(matchable, description);
                return description.toString();
            },
            new IsEqual<>("TeeInput with result \"e\" and copied value \"e\"")
        ).affirm();
    }

    @Test
    public void failsIfCopiedValueDoesNotMatchExpectedValue() {
        new Assertion<>(
            "Matcher does not compare copied and expected values",
            () -> {
                final String expected = "expected-2";
                final TeeInput matchable = new TeeInput(
                    new TextOf(expected),
                    new OutputTo(new ByteArrayOutputStream())
                );
                final TeeInputHasResult matcher = new TeeInputHasResult(
                    expected, new TextOf("incorrect")
                );
                return matcher.matchesSafely(matchable);
            },
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    public void describesMismatchOfCopiedAndExpected() {
        new Assertion<>(
            "Description of mismatch of copied and expected incorrect",
            () -> {
                final TeeInput matchable = new TeeInput(
                    new TextOf("i"),
                    new OutputTo(new ByteArrayOutputStream())
                );
                final TeeInputHasResult matcher = new TeeInputHasResult(
                    "i", new TextOf("wrong")
                );
                final StringDescription description = new StringDescription();
                matcher.matchesSafely(matchable);
                matcher.describeMismatchSafely(matchable, description);
                return description.toString();
            },
            new IsEqual<>("TeeInput with result \"i\" and copied value \"i\"")
        ).affirm();
    }
}
