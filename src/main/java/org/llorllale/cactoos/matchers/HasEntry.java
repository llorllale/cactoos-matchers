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

import java.util.Map;
import java.util.Optional;
import org.cactoos.scalar.Ternary;

/**
 * Matcher to check that {@link Map} has particular elements.
 *
 * <p>Here is an example how {@link HasEntry} can be used:</p>
 * <pre>{@code
 *  new Assertion<>(
 *     "must match",
 *     () -> new MapOf<>(
 *         new MapEntry<>("a", 1),
 *         new MapEntry<>("b", 2)
 *     ),
 *     new HasEntry<>("a", 1)
 * ).affirm();
 * }</pre>
 *
 * @param <K> Type of key.
 * @param <V> Type of value.
 * @since 1.0.0
 */
public final class HasEntry<K, V> extends MatcherEnvelope<Map<? extends K, ? extends V>> {
    /**
     * Ctor.
     * @param key The expected key.
     * @param value The expected value.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public HasEntry(final K key, final V value) {
        super(
            new MatcherOf<>(
                input -> Optional.ofNullable(input.get(key))
                    .map(vl -> vl.equals(value))
                    .orElse(false),
                desc -> desc
                    .appendText("has entry ")
                    .appendValue(key)
                    .appendText("=")
                    .appendValue(value),
                (input, desc) -> new Ternary<>(
                    () -> input.containsKey(key),
                    () -> desc.appendText("has entry ")
                        .appendValue(key)
                        .appendText("=")
                        .appendValue(input.get(key)),
                    () -> desc.appendText("has no entry for ")
                        .appendValue(key)
                ).value()
            )
        );
    }
}
