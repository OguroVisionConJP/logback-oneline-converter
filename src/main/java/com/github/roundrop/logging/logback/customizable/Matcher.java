package com.github.roundrop.logging.logback.customizable;

import com.github.roundrop.logging.logback.NewLinePattern;
import com.github.roundrop.logging.logback.TabPattern;

public class Matcher {
    /**
     * back port.
     */
    public static String toOneLine(String src) {
        return TabPattern.replaceAll(NewLinePattern.replaceAll(src));
    }
}
