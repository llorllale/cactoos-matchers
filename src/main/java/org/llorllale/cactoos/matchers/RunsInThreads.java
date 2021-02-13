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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.cactoos.Func;
import org.cactoos.iterable.Mapped;
import org.cactoos.scalar.SumOf;
import org.cactoos.scalar.Ternary;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Matcher for {@link Func} that must run in multiple threads.
 *
 * @param <T> Type of input
 * @since 0.24
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RunsInThreads<T> extends TypeSafeDiagnosingMatcher<Func<T, Boolean>> {

    /**
     * Input.
     */
    private final T input;

    /**
     * Total cid of threads to run.
     */
    private final int total;

    /**
     * Ctor.
     */
    public RunsInThreads() {
        this(null);
    }

    /**
     * Ctor.
     * @param object Input object
     */
    public RunsInThreads(final T object) {
        // @checkstyle MagicNumber (1 line)
        this(object, Runtime.getRuntime().availableProcessors() << 4);
    }

    /**
     * Ctor.
     * @param object Input object
     * @param threads Size of thread pool
     */
    public RunsInThreads(final T object, final int threads) {
        super();
        this.input = object;
        this.total = threads;
    }

    @Override
    public boolean matchesSafely(
        final Func<T, Boolean> func,
        final Description desc
    ) {
        final ExecutorService service = Executors.newFixedThreadPool(
            this.total
        );
        final CountDownLatch latch = new CountDownLatch(1);
        final List<Future<Boolean>> futures = new ArrayList<>(this.total);
        final Callable<Boolean> task = () -> {
            latch.await();
            return func.apply(this.input);
        };
        for (int thread = 0; thread < this.total; ++thread) {
            futures.add(service.submit(task));
        }
        latch.countDown();
        final int matching = new SumOf(
            new Mapped<>(
                f -> new Ternary<>(f.get(), 1, 0).value(),
                futures
            )
        ).intValue();
        service.shutdown();
        if (matching != this.total) {
            desc
                .appendText("ran successfuly in ")
                .appendValue(matching)
                .appendText(" threads");
        }
        return matching == this.total;
    }

    @Override
    public void describeTo(final Description description) {
        description
            .appendText("runs in ")
            .appendValue(this.total)
            .appendText(" threads successfuly");
    }
}
