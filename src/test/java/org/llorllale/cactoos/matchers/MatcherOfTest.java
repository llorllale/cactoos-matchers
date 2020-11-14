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

import java.util.Comparator;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.Test;

/**
 * Test case for {@link MatcherOf}.
 *
 * @since 1.0.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class MatcherOfTest {

    @Test
    public void matchesFunc() {
        new Assertion<>(
            "matches when arg satisfies the predicate",
            new MatcherOf<>(x -> x > 5),
            new Matches<>(10)
        ).affirm();
    }

    @Test
    public void mismatchesFunc() {
        new Assertion<>(
            "mismatches when arg does not satisfy the predicate",
            new MatcherOf<>(
                x -> x > 5,
                new TextOf("Must be > 5")
            ),
            new Mismatches<>(
                1,
                "\"Must be > 5\"",
                "<1>"
            )
        ).affirm();
    }

    @Test
    public void matcherOfProcMatchesAnyArguments() {
        new Assertion<>(
            "matches any arguments when constructed from a Proc",
            new MatcherOf<>(String::trim),
            new Matches<>("a")
        ).affirm();
    }

    @Test
    public void mismatches() {
        final Integer expected = 42;
        final Integer provided = 43;
        new Assertion<>(
            "must mismatches correctly",
            new MatcherOf<>(
                input -> input.equals(expected),
                desc -> desc.appendValue(expected),
                (actual, desc) -> desc.appendValue(actual)
            ),
            new Mismatches<>(
                provided,
                new Joined("", "<", expected.toString(), ">"),
                new Joined("", "<", provided.toString(), ">")
            )
        ).affirm();
    }

    @Test
    public void matchesByComparator() {
        new Assertion<>(
            "matches when comparator returns 0",
            new MatcherOf<>(
                10,
                Comparator.comparingInt(x -> Math.abs(x.intValue()))
            ),
            new Matches<>(-10)
       ).affirm();
    }

    @Test
    public void mismatchesByComparator() {
        new Assertion<>(
            "mismatches when arg does not satisfy comparator",
            new MatcherOf<>(
                123,
                Comparator.comparingInt(x -> Math.abs(x.intValue()))
            ),
            new Mismatches<>(
                1234,
                "equals <123>",
                "comparator returns <1> when <123> compared to <1234>"
            )
        ).affirm();
    }

}
