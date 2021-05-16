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
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.MappedWithIndex;
import org.cactoos.scalar.Folded;
import org.cactoos.scalar.Mapped;
import org.cactoos.scalar.Unchecked;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;

/**
 * Group assert.
 * Fails if one of assertions fails.
 *
 * @since 1.0.0
 * @checkstyle ClassDataAbstractionCoupling (100 lines)
 */
public final class AssertAll implements Assert {

    /**
     * Assertions.
     */
    private final Iterable<? extends Assert> assertions;

    /**
     * Description.
     */
    private final Text desc;

    /**
     * Ctor.
     * @param description Description.
     * @param assertions Assertions.
     */
    public AssertAll(
        final String description,
        final Assert... assertions
    ) {
        this(
            description,
            new IterableOf<>(assertions)
        );
    }

    /**
     * Ctor.
     * @param description Description.
     * @param assertions Assertions.
     */
    public AssertAll(
        final String description,
        final Iterable<? extends Assert> assertions
    ) {
        this.desc = new FormattedText(
            "%s: %s",
            new TextOf(description),
            new Joined(
                new TextOf(", "),
                new MappedWithIndex<>(
                    (check, num) -> new FormattedText(
                        "%d) %s",
                        num + 1,
                        check.description().asString()
                    ),
                    assertions
                )
            )
        );
        this.assertions = assertions;
    }

    @Override
    public Text description() {
        return this.desc;
    }

    @SuppressWarnings(
        { "PMD.AvoidCatchingGenericException", "PMD.AvoidCatchingThrowable" }
    )
    @Override
    public void affirm() throws AssertionError {
        new Unchecked<>(
            new Mapped<Void>(
                res -> {
                    if (res.getSuppressed().length > 0) {
                        throw res;
                    }
                    return null;
                },
                new Folded<>(
                    new AssertionError(
                        new UncheckedText(this.desc).asString()
                    ),
                    (res, check) -> {
                        // @checkstyle IllegalCatchCheck (5 lines)
                        try {
                            check.affirm();
                        } catch (final Throwable ex) {
                            res.addSuppressed(ex);
                        }
                        return res;
                    },
                    this.assertions
                )
            )
        ).value();
    }

}
