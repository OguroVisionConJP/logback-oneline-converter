package com.github.roundrop.logging.logback.customizable;

import ch.qos.logback.classic.spi.IThrowableProxy;

public class ThrowableProxyConverter extends ch.qos.logback.classic.pattern.ThrowableProxyConverter {
    private ConvertProc converter;

    @Override
    public void start() {
        converter = new ConvertProc(getContext());
        super.start();
    }

    @Override
    protected String throwableProxyToString(IThrowableProxy tp) {
        final String string = super.throwableProxyToString(tp);
        if (converter == null) {
            return Matcher.toOneLine(string);
        } else {
            return converter.exec(string);
        }
    }
}
