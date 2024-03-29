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
import java.util.stream.Stream;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Test for {@link NaturalOrdering}.
 *
 * @since 1.0.0
 */
final class NaturalOrderingTest implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(
        final ExtensionContext context
    ) throws Exception {
        return Stream.of(
            Arguments.of(1, 2),
            Arguments.of(1d, 2d),
            Arguments.of(0, 1),
            Arguments.of(0, 0)
        );
    }

    @ArgumentsSource(NaturalOrderingTest.class)
    @ParameterizedTest
    <T extends Comparable<T>> void actsTheSameAsNaturalOrder(
        final T first, final T second
    ) {
        new Assertion<>(
            "Must be the same",
            new NaturalOrdering<T>().compare(first, second),
            new IsEqual<>(
                Comparator.<T>naturalOrder().compare(first, second)
            )
        ).affirm();
    }
}
