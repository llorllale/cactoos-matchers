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

import java.util.Map;
import java.util.Properties;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;
import org.hamcrest.core.IsEqual;

/**
 * Matcher to check that {@link Properties} has particular entry.
 *
 * <p>Here is an example how {@link HasProperty} can be used:</p>
 * <pre>
 *  new Assertion<>(
 *     "must have an entry",
 *     new PropertiesOf<>(...),
 *     new HasProperty<>("foo", "bar")
 * );</pre>
 *
 * @since 1.0.0
 */
public final class HasProperty extends MatcherEnvelope<Properties> {

    /**
     * Ctor.
     *
     * @param key Literal key.
     * @param value Literal value.
     */
    HasProperty(final String key, final String value) {
        this(new IsEqual<>(key), new IsEqual<>(value));
    }

    /**
     * Ctor.
     *
     * @param key Matcher for key.
     * @param value Matcher for value.
     */
    public HasProperty(final Matcher<String> key, final Matcher<String> value) {
        this(new IsEntry<>(key, value));
    }

    /**
     * Ctor.
     *
     * @param key Literal key.
     */
    public HasProperty(final String key) {
        this(new IsEntry<>(new IsEqual<>(key), new IsAnything<>()));
    }

    /**
     * Ctor.
     *
     * @param entr Entry matcher.
     */
    private HasProperty(final Matcher<Map.Entry<String, String>> entr) {
        super(
            new MatcherOf<>(
                properties -> new HasValuesMatching<>(
                    entr::matches
                ).matches(properties.entrySet()),
                desc -> desc
                    .appendText("has property ")
                    .appendDescriptionOf(entr),
                (properties, desc) -> desc
                    .appendText("has properties ")
                    .appendValue(properties)
            )
        );
    }

}
