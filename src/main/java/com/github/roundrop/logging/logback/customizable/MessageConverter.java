package com.github.roundrop.logging.logback.customizable;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class MessageConverter extends ClassicConverter {
    private ConvertProc converter;

    @Override
    public void start() {
        converter = new ConvertProc(getContext());
        super.start();
    }

    @Override
    public String convert(ILoggingEvent event) {
        final String message = event.getFormattedMessage();
        if (converter == null) {
            return Matcher.toOneLine(message);
        } else {
            return converter.exec(message);
        }

    }
}
