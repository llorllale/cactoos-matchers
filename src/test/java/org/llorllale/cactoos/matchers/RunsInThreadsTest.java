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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.Func;
import org.cactoos.func.Repeated;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Test;

/**
 * Test case for {@link RunsInThreads}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCoupling (2 lines)
 */
public final class RunsInThreadsTest {

    /**
     * Twenty threads will each attempt to increment a counter 100 times with
     * a safe function.
     */
    @Test
    public void matchPositive() {
        final AtomicInteger counter = new AtomicInteger(0);
        final int threads = 20;
        final int attempts = 100;
        new Assertion<>(
            "matches the thread-safe Func",
            new RunsInThreads<>(counter, threads),
            new Matches<>(new Repeated<>(new Safe(), attempts))
        ).affirm();
        new Assertion<>(
            "counter must be incremented by all threads",
            counter.get(),
            new IsEqual<>(threads * attempts)
        ).affirm();
    }

    /**
     * Twenty threads will each attempt to increment a counter 100 times with
     * an unsafe function.
     */
    @Test
    public void matchNegative() {
        final AtomicInteger counter = new AtomicInteger(0);
        final int threads = 20;
        final int attempts = 100;
        new Assertion<>(
            "does not match Func that is not thread-safe",
            new RunsInThreads<>(counter, threads),
            new IsNot<>(
                new Matches<>(
                    new Repeated<>(new Unsafe(), attempts)
                )
            )
        ).affirm();
        new Assertion<>(
            "counter must not be incremented by all threads",
            counter.get(),
            new IsNot<>(new IsEqual<>(threads * attempts))
        ).affirm();
    }

    /**
     * Guaranteed thread-safety.
     *
     * @since 0.24
     */
    private static class Safe implements Func<AtomicInteger, Boolean> {
        @Override
        public Boolean apply(final AtomicInteger input) {
            input.incrementAndGet();
            return true;
        }
    }

    /**
     * Guaranteed "thread-unsafety". It only allows the first thread to
     * increment the integer.
     *
     * @since 0.24
     */
    private static class Unsafe implements Func<AtomicInteger, Boolean> {
        /**
         * Special value indicating no thread has yet applied this func.
         */
        private static final String NO_THREAD = "";

        /**
         * Name of thread to first apply this func.
         */
        private final AtomicReference<String> thread = new AtomicReference<>(
            Unsafe.NO_THREAD
        );

        @Override
        public Boolean apply(final AtomicInteger input) {
            this.thread.compareAndSet(
                Unsafe.NO_THREAD,
                Thread.currentThread().getName()
            );
            final boolean applies = this.thread.get().equals(
                Thread.currentThread().getName()
            );
            if (applies) {
                input.incrementAndGet();
            }
            return applies;
        }
    }
}
