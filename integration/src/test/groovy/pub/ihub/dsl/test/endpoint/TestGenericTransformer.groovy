package pub.ihub.dsl.test.endpoint

import groovy.util.logging.Slf4j
import org.springframework.integration.transformer.GenericTransformer



/**
 * @author liheng
 */
@Slf4j
class TestGenericTransformer implements GenericTransformer<String,Integer> {

    String name

    @Override
    Integer transform(String source) {
        log.info 'transform {}, source {}', name, source
        source as Integer
    }

}
