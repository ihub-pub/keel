package pub.ihub.dsl.test.endpoint

import groovy.util.logging.Slf4j
import org.springframework.integration.handler.GenericHandler
import org.springframework.messaging.MessageHeaders



/**
 * @author liheng
 */
@Slf4j
class TestGenericHandler implements GenericHandler<String> {

    String name

    @Override
    Object handle(String payload, MessageHeaders headers) {
        log.info 'handle {}, payload {}, headers {}', name, payload, headers
        payload
    }

}
