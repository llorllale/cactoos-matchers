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

import java.util.Arrays;
import org.cactoos.Bytes;
import org.cactoos.bytes.BytesOf;
import org.cactoos.bytes.UncheckedBytes;
import org.cactoos.iterable.IterableOfBytes;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher for {@link Bytes}.
 *
 * @since 1.0.0
 * @checkstyle ProtectedMethodInFinalClassCheck (200 lines)
 */
public final class IsBytes extends TypeSafeDiagnosingMatcher<Bytes> {

    /**
     * Expected bytes.
     */
    private final UncheckedBytes bytes;

    /**
     * Ctor.
     * @param bytes The bytes to match against
     */
    public IsBytes(final byte... bytes) {
        this(new BytesOf(bytes));
    }

    /**
     * Ctor.
     * @param chars The chars to match against
     */
    public IsBytes(final char... chars) {
        this(new BytesOf(chars));
    }

    /**
     * Ctor.
     * @param string The string to match against
     */
    public IsBytes(final String string) {
        this(new BytesOf(string));
    }

    /**
     * Ctor.
     * @param bytes The bytes to match against
     */
    public IsBytes(final Bytes bytes) {
        this.bytes = new UncheckedBytes(bytes);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendValue(new IterableOfBytes(this.bytes));
    }

    @Override
    protected boolean matchesSafely(
        final Bytes item, final Description description
    ) {
        final byte[] presented = new UncheckedBytes(item).asBytes();
        final byte[] expected = this.bytes.asBytes();
        final boolean result;
        if (!Arrays.equals(presented, expected)) {
            description.appendValue(new IterableOfBytes(presented));
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
