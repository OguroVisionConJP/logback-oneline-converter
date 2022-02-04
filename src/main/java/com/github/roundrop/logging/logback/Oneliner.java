package com.github.roundrop.logging.logback;

/**
 * replace string multi line to one line.
 */
public class Oneliner {

    /**
     * back port.
     */
    public static String toOnline(String src) {
        return TabPattern.replaceAll(NewLinePattern.replaceAll(src));
    }
}
