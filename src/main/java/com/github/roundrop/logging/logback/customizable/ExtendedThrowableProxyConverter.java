package com.github.roundrop.logging.logback.customizable;

import ch.qos.logback.classic.spi.IThrowableProxy;

public class ExtendedThrowableProxyConverter extends ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter {
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
