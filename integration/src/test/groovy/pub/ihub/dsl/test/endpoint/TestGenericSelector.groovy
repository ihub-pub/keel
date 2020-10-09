package pub.ihub.dsl.test.endpoint

import groovy.util.logging.Slf4j
import org.springframework.integration.core.GenericSelector



/**
 * @author liheng
 */
@Slf4j
class TestGenericSelector implements GenericSelector<String> {

    String name

    @Override
    boolean accept(String source) {
        log.info 'filter {}, source {}', name, source
        'error' != source
    }

}
