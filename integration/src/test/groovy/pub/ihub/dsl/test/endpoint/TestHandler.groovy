package pub.ihub.dsl.test.endpoint

import groovy.util.logging.Slf4j
import org.springframework.integration.handler.MessageProcessor
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders



/**
 * @author liheng
 */
@Slf4j
class TestHandler implements MessageProcessor<String> {

    String name

    String handle(String payload, MessageHeaders headers) {
        log.info 'handle {}, payload {}, headers {}', name, payload, headers
        payload
    }

    @Override
    String processMessage(Message<?> message) {
        handle message.payload as String, message.headers
    }

}
