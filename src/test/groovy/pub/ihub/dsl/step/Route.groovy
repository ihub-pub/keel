package pub.ihub.dsl.step

import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
import pub.ihub.dsl.context.Context
import pub.ihub.dsl.context.IStep



/**
 * @author liheng
 */
@TupleConstructor
@Slf4j
class Route implements IStep<Integer> {

    int step

    Integer run(Context context) {
        log.debug 'context -> {}', context
        step
    }

}
