/**
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

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

/**
 * Test case for {@link org.llorllale.cactoos.matchers.RunsInThreads}.
 *
 * @author Alexander Menshikov (sharplermc@gmail.com)
 * @version $Id$
 * @since 1.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class RunsInThreadsTest {

    @Test
    public void matchPositive() {
        final RunsInThreads<Map<Integer, Integer>> matcher =
            new RunsInThreads<>(new ConcurrentHashMap<>());
        MatcherAssert.assertThat(
            "Can't concurrently modify ConcurrentHashMap",
            matcher.matchesSafely(RunsInThreadsTest::modify),
            new IsEqual<>(true)
        );
    }

    @Test
    public void matchNegative() {
        final RunsInThreads<Map<Integer, Integer>> matcher =
            new RunsInThreads<>(new HashMap<>());
        MatcherAssert.assertThat(
            "Can concurrently modify HashMap",
            matcher.matchesSafely(RunsInThreadsTest::modify),
            new IsEqual<>(false)
        );
    }

    /**
     * Check possibility of concurrent modification.
     *
     * @param map Tested map.
     * @return Return {@code true} if the map can be concurrently modified.
     */
    private static boolean modify(final Map<Integer, Integer> map) {
        boolean flag = true;
        try {
            for (final Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                //@checkstyle MagicNumberCheck (1 line)
                for (int count = 0; count < 100; ++count) {
                    map.put(
                        ThreadLocalRandom.current().nextInt(),
                        ThreadLocalRandom.current().nextInt()
                    );
                }
            }
        } catch (final ConcurrentModificationException ignored) {
            flag = false;
        }
        return flag;
    }
}
