package pub.ihub.dsl.test.endpoint

import groovy.util.logging.Slf4j
import org.springframework.integration.handler.MessageProcessor
import org.springframework.messaging.Message



/**
 * @author liheng
 */
@Slf4j
class TestFilter implements MessageProcessor<Boolean> {

    String name

    boolean filter(String source) {
        log.info 'filter {}, source {}', name, source
        'error' != source
    }

    @Override
    Boolean processMessage(Message<?> message) {
        filter message.payload as String
    }

}
