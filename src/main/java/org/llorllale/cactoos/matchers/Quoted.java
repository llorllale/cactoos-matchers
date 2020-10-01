package org.llorllale.cactoos.matchers;

import org.cactoos.Text;
import org.cactoos.text.TextEnvelope;
import org.cactoos.text.TextOf;

/**
 * Quoted text.
 * @todo #203:15m/DEV Move it to cactoos basic types as quoted text. This would be reused if there is the need to quote something.
 */
public class Quoted extends TextEnvelope {
    /**
     * Ctor.
     * @param string
     */
    public Quoted(final String string){
        this(new TextOf(string));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public Quoted(final Text text) {
        super(new TextOf("\"" + text + "\""));
    }
}
