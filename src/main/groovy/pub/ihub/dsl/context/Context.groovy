package pub.ihub.dsl.context

import groovy.transform.TupleConstructor



/**
 * 上下文
 * @author liheng
 */
@TupleConstructor(includeFields = true)
class Context {

    @Delegate()
    private Map context

}
