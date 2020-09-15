package pub.ihub.dsl

import groovy.transform.TupleConstructor
import pub.ihub.dsl.context.Context
import pub.ihub.dsl.context.IStep

import static pub.ihub.dsl.context.Constants.ROUTE_FLAG_CONTINUE
import static pub.ihub.dsl.context.Constants.STATUS_CODE_OK



/**
 * @author liheng
 */
@TupleConstructor
class RouteDemo implements IStep<Integer> {

    int step

    Tuple4<Integer, Integer, String, String> run(Context context) {
        println 'context -> ' + context
        new Tuple4(ROUTE_FLAG_CONTINUE, STATUS_CODE_OK, step, 'ok')
    }

}
