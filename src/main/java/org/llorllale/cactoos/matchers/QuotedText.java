package org.llorllale.cactoos.matchers;

import org.cactoos.Text;

/**
 * Quoted text.
 */
public class QuotedText implements Text {
    private final Text origin;

    /**
     * Ctor.
     * @param origin The text
     */
    public QuotedText(Text origin) {
        this.origin = origin;
    }

    @Override
    public String asString() throws Exception {
        return origin.asString();
    }
}
