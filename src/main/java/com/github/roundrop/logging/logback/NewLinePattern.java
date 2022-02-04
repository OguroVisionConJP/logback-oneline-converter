package com.github.roundrop.logging.logback;

import java.util.regex.Pattern;

public class NewLinePattern {
    private static Pattern compiledPattern = Pattern.compile("\r\n|[\n\r]|\r|\n");

    public static String replaceAll(String src, String replacedString) {
        return compiledPattern.matcher(src).replaceAll(replacedString);
    }

    public static String replaceAll(String src) {
        return replaceAll(src, "\\\\n");
    }
}
