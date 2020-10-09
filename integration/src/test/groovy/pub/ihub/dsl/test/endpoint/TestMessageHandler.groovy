package pub.ihub.dsl.test.endpoint

import groovy.util.logging.Slf4j
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler



/**
 * @author liheng
 */
@Slf4j
class TestMessageHandler implements MessageHandler {

    String name

    @Override
    void handleMessage(Message<?> message) {
        log.info 'handle {}, message {}', name, message
    }

}
