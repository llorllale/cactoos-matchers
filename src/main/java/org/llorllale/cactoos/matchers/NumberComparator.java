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

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparator for Numbers.
 *
 * @since 1.0.0
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("SE_TRANSIENT_FIELD_NOT_RESTORED")
final class NumberComparator implements Comparator<Number>, Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -1L;

    /**
     * Comparator of numbers.
     */
    private final transient Comparator<? super Number> comparator;

    /**
     * Ctor.
     * @param comparator Comparator.
     */
    private NumberComparator(
        final Comparator<? super Number> comparator
    ) {
        this.comparator = comparator;
    }

    /**
     * Ctor.
     */
    NumberComparator() {
        this(
            Comparator
                .comparing(Number::doubleValue)
                .thenComparing(Number::intValue)
                .thenComparing(Number::longValue)
                .thenComparing(Number::floatValue)
        );
    }

    @Override
    public int compare(final Number first, final Number second) {
        return this.comparator.compare(first, second);
    }

    @Override
    public String toString() {
        return "NumberComparator";
    }
}
