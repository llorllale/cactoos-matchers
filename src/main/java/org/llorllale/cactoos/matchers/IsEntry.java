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
import org.cactoos.scalar.And;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

/**
 * Matcher to check that {@link Map.Entry} has particular key and value.
 *
 * @param <K> Type of key
 * @param <V> Type of value
 * @since 1.0.0
 */
public final class IsEntry<K, V> extends MatcherEnvelope<Map.Entry<K, V>> {

    /**
     * Ctor.
     *
     * @param key Literal key.
     * @param value Literal value.
     */
    public IsEntry(final K key, final V value) {
        this(new IsEqual<>(key), new IsEqual<>(value));
    }

    /**
     * Ctor.
     *
     * @param key Matcher for key.
     * @param value Matcher for value.
     */
    public IsEntry(final Matcher<K> key, final Matcher<V> value) {
        super(
            new MatcherOf<>(
                entry -> new And(
                    () -> key.matches(entry.getKey()),
                    () -> value.matches(entry.getValue())
                ).value(),
                desc -> {
                    IsEntry.descriptionOfKey(desc).appendDescriptionOf(key);
                    IsEntry.descriptionOfValue(desc).appendDescriptionOf(value);
                },
                (entry, desc) -> {
                    IsEntry.descriptionOfKey(desc);
                    key.describeMismatch(entry.getKey(), desc);
                    IsEntry.descriptionOfValue(desc);
                    value.describeMismatch(entry.getValue(), desc);
                }
            )
        );
    }

    /**
     * Start describing value.
     *
     * @param desc Description.
     * @return Altered description
     */
    private static Description descriptionOfValue(final Description desc) {
        return desc.appendText(", value ");
    }

    /**
     * Start describing key.
     *
     * @param desc Description.
     * @return Altered description
     */
    private static Description descriptionOfKey(final Description desc) {
        return desc.appendText("key ");
    }
}
