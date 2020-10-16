package pub.ihub.dsl.test.endpoint

import groovy.util.logging.Slf4j
import org.springframework.integration.handler.MessageProcessor
import org.springframework.messaging.Message



/**
 * @author liheng
 */
@Slf4j
class TestRouter implements MessageProcessor<String> {

    String name

    @Override
    String processMessage(Message<?> message) {
        log.info 'router {}, payload {}, headers {}', name, message.payload, message.headers
        message.payload
    }

}
