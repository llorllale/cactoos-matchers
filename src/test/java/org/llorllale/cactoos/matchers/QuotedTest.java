package org.llorllale.cactoos.matchers;

import org.cactoos.text.TextOf;
import org.junit.Test;

public class QuotedTest {
    @Test
    void convertsText() {
        new Assertion<>(
                "Can't quote a text",
                new Quoted(new TextOf("qwerty")),
                new TextHasString("\nqwerty\n")
        ).affirm();
    }

    @Test
    void convertsString() {
        new Assertion<>(
                "Can't quote a string",
                new Quoted("qwerty"),
                new TextHasString("\"qwerty\"")
        ).affirm();
    }
}
