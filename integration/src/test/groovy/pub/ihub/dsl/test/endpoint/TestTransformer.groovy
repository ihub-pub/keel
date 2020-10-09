package pub.ihub.dsl.test.endpoint

import groovy.util.logging.Slf4j
import org.springframework.integration.handler.MessageProcessor
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders



/**
 * @author liheng
 */
@Slf4j
class TestTransformer implements MessageProcessor<String> {

    String name

    String transform(Integer payload, MessageHeaders headers) {
        log.info 'transform {}, payload {}, headers {}', name, payload, headers
        payload
    }

    @Override
    String processMessage(Message<?> message) {
        transform message.payload as Integer, message.headers
    }

}
