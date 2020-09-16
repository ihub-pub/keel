package pub.ihub.dsl

import groovy.transform.TupleConstructor
import pub.ihub.dsl.context.Context
import pub.ihub.dsl.context.IStep



/**
 * @author liheng
 */
@TupleConstructor
class RouteDemo implements IStep<Integer> {

    int step

    Integer run(Context context) {
        println 'context -> ' + context
        step
    }

}
