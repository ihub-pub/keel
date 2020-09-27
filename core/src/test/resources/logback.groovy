import ch.qos.logback.classic.encoder.PatternLayoutEncoder



appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%d{HH:mm:ss.SSS} [%thread] %-5level %-30logger{30}:%-3line - %msg%n'
    }
}
root(TRACE, ['CONSOLE'])
