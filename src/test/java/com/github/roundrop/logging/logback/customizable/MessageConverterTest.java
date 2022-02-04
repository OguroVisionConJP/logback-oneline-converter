package com.github.roundrop.logging.logback.customizable;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.TurboFilterList;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.read.ListAppender;
import ch.qos.logback.core.spi.AppenderAttachable;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static java.util.Objects.requireNonNull;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageConverterTest {

    private final MessageConverter converter = new MessageConverter();

    private final LoggingEvent event = new LoggingEvent();

    @Test
    public void noLineFeed() throws Exception {
        event.setMessage("foo,bar, baz");
        String s = converter.convert(event);
        assertThat(s, is("foo,bar, baz"));
    }

    @Test
    public void withLF() throws Exception {
        event.setMessage("foo\nbar\nbaz");
        String s = converter.convert(event);
        assertThat(s, is("foo\\nbar\\nbaz"));
    }

    @Test
    public void withCRLF() throws Exception {
        event.setMessage("foo\r\nbar\r\nbaz");
        String s = converter.convert(event);
        assertThat(s, is("foo\\nbar\\nbaz"));
    }

    @Test
    public void withCR() throws Exception {
        event.setMessage("foo\rbar\rbaz");
        String s = converter.convert(event);
        assertThat(s, is("foo\\nbar\\nbaz"));
    }

    @Test
    public void log() throws Exception {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LoggerContext context = logger.getLoggerContext();
        context.reset();

        Map<String, String> ruleRegistry = (Map) context.getObject(CoreConstants.PATTERN_RULE_REGISTRY);
        if (ruleRegistry == null) {
            ruleRegistry = new HashMap<String, String>();
            context.putObject(CoreConstants.PATTERN_RULE_REGISTRY, ruleRegistry);
        }
        ruleRegistry.put("msg1L", MessageConverter.class.getCanonicalName());

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy/MM/dd HH:mm:ss:SSS}\\t%-5level\\t%msg1L");
        encoder.start();

        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>();
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.start();
        logger.addAppender(appender);

        logger.info(
                "[ORM] SQL : SELECT\n" +
                        "    t1.*\n" +
                        "FROM\n" +
                        "    tbl1 t1\n" +
                        "INNER JOIN tbl2 t2 ON t1.xxx_id = t2.xxx_id\n" +
                        "WHERE\n" +
                        "    t1.xxx_id in (1, 2, 3)\n" +
                        "AND\n" +
                        "    t2.xxx_code in (11, 12)\n" +
                        "ORDER BY\n" +
                        "    xxx_id");
    }

    @Test
    public void testSimple() throws JoranException {
        LoggerContext loggerFactory = createLoggerFactory("/logback-newline-test.xml");

        // Write something that never gets logged explicitly...
        Logger debugLogger = loggerFactory.getLogger("com.example.Debug");
        debugLogger.debug("debug one");
        debugLogger.debug("debug two");
        debugLogger.debug("debug three");
        debugLogger.debug("debug four");

        Logger logger = loggerFactory.getLogger("com.example.Test");
        logger.error("Write out error message to console");

        ListAppender<ILoggingEvent> listAppender = (ListAppender<ILoggingEvent>) requireNonNull(
                loggerFactory.getLoggerList().get(0).getAppender("LIST"));
        assertThat(listAppender.list.size(), is(5));
    }

    LoggerContext createLoggerFactory(String resourceName) throws JoranException {
        LoggerContext context = new LoggerContext();
        URL resource = getClass().getResource(resourceName);
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        configurator.doConfigure(resource);
        return context;
    }
}