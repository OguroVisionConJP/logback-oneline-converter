package com.github.roundrop.logging.logback.customizable;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConverterTest {

    private PrintStream originalSystemOut;
    private ByteArrayOutputStream systemOutContent;

    @BeforeEach
    void redirectSystemOutStream() {

        originalSystemOut = System.out;

        // given
        systemOutContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(systemOutContent));
    }

    @AfterEach
    void restoreSystemOutStream() {
        System.setOut(originalSystemOut);
    }

    @Test
    public void go() throws Exception {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.debug("\tdebug one \r");
        logger.debug("\tdebug two \n");
        logger.debug("\tdebug three \r\n");
        logger.debug("\tdebug four \n\r");

        String nlcode = "/*[NL]*/";
        assertThat(systemOutContent.toString(), is(
                "\tdebug one " + nlcode +
                        "\tdebug two " + nlcode +
                        "\tdebug three " + nlcode +
                        "\tdebug four " + nlcode));
    }
}
