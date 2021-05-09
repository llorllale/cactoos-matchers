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
import org.cactoos.bytes.BytesOf;
import org.cactoos.bytes.HexOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link IsBytes}.
 * @since 1.0.0
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class IsBytesTest {

    @Test
    void matchesExactly() {
        new Assertion<>(
            "Must match",
            new BytesOf("ABC"),
            new IsBytes('A', 'B', 'C')
        ).affirm();
    }

    @Test
    void mismatchesWhenThrows() {
        new Assertion<>(
            "Must mismatch",
            new IsBytes(65, 66, 67),
            new Mismatches<>(
                new BytesOf(
                    (Text) () -> {
                        throw new UnsupportedOperationException("ok");
                    }
                ),
                "<65, 66, 67>",
                "thrown <java.lang.UnsupportedOperationException: ok>"
            )
        ).affirm();
    }

    @Test
    void mismatches() {
        new Assertion<>(
            "Must mismatch",
            new IsBytes((byte) 65, (byte) 66, (byte) 67),
            new Mismatches<>(
                new BytesOf("abc"),
                "<65, 66, 67>",
                "<97, 98, 99>"
            )
        ).affirm();
    }

    @Test
    void matchesAsString() {
        new Assertion<>(
            "Must match",
            new HexOf(new TextOf("6465616462656166")),
            new IsBytes("deadbeaf")
        ).affirm();
    }

}
