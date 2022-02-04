package com.github.roundrop.logging.logback;

import java.util.regex.Pattern;

public class TabPattern {
    private static Pattern compiledPattern = Pattern.compile("\t", Pattern.LITERAL);

    public static String replaceAll(String src, String replacedString) {
        return compiledPattern.matcher(src).replaceAll(replacedString);
    }

    public static String replaceAll(String src) {
        return replaceAll(src, "    ");
    }
}
