package com.github.roundrop.logging.logback.customizable;

import com.github.roundrop.logging.logback.NewLinePattern;
import com.github.roundrop.logging.logback.TabPattern;

import ch.qos.logback.core.Context;

public class ConvertProc {

    /**
     * default: <property name="newLineReplaceExec" value="true" />.
     */
    protected static String NEW_LINE_REPLACE_EXEC = "newLineReplaceExec";

    /**
     * default: <property name="newLineReplaced" value=" \\n " />.
     */
    protected static String NEW_LINE_REPLACED = "newLineReplaced";

    /**
     * default: <property name="hardTabReplaceExec" value="true" />.
     */
    protected static String HARD_TAB_REPLACE_EXEC = "hardTabReplaceExec";

    /**
     * default: <property name="hardTabReplaced" value=" " />.
     */
    protected static String HARD_TAB_REPLACED = "hardTabReplaced";

    private boolean replaceNewLine = true;
    private boolean replaceHardTab = true;

    private String newLineReplaced = " \\n ";
    private String hardTabReplaced = "    ";

    public ConvertProc(Context context) {
        String hardTabReplaced = context.getProperty(NEW_LINE_REPLACED);
        this.hardTabReplaced = hardTabReplaced != null ? hardTabReplaced : this.hardTabReplaced;
        String newLineReplaced = context.getProperty(NEW_LINE_REPLACED);
        this.newLineReplaced = newLineReplaced != null ? newLineReplaced : this.newLineReplaced;
        String replaceHardTab = context.getProperty(NEW_LINE_REPLACED);
        this.replaceHardTab = replaceHardTab != null ? Boolean.parseBoolean(replaceHardTab) : this.replaceHardTab;
        String replaceNewLine = context.getProperty(NEW_LINE_REPLACED);
        this.replaceNewLine = replaceNewLine != null ? Boolean.parseBoolean(replaceNewLine) : this.replaceNewLine;
    }

    public boolean isReplaceNewLine() {
        return replaceNewLine;
    }

    public boolean isReplaceHardTab() {
        return replaceHardTab;
    }

    public String getNewLineReplaced() {
        return newLineReplaced;
    }

    public String getHardTabReplaced() {
        return hardTabReplaced;
    }

    /**
     * string convert with context properties.
     * 
     * @param string
     * @return replacedString
     */
    String exec(final String string) {
        String result = string.concat("");
        if (isReplaceHardTab()) {
            result = TabPattern.replaceAll(result, getHardTabReplaced());
        }
        if (isReplaceNewLine()) {
            result = NewLinePattern.replaceAll(result, getNewLineReplaced());
        }
        return result;
    }
}
