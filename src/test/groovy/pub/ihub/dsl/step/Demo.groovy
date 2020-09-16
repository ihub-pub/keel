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
class Demo implements IStep<List> {

    String name = '默认步骤'

    List run(Context context) {
        log.debug 'context -> {}', context
        log.debug '执行 -> {}', name
        context.steps << name
    }

}
