package pub.ihub.dsl.context

import pub.ihub.dsl.DSLActuator
import groovy.util.logging.Slf4j



/**
 * 基于上下文DSL执行器
 * @author liheng
 */
@Slf4j
class ContextActuator extends DSLActuator {

    ContextActuator(Map<String, Object> nameClassMappings, Map<String, Closure> nameFlowMappings) {
        super(nameClassMappings, nameFlowMappings)
    }

    ContextActuator(URL scriptLocation) {
        super(scriptLocation)
    }

    protected doCall(Class groovyMethods, Closure flow, ... args) {
        use(groovyMethods, flow.with { it.rehydrate this, owner, thisObject })
                .with { it instanceof IStep || it instanceof Closure<RuntimeContext> ? new RuntimeContext() >> it : it }
    }

    def call(Context context, Closure closure) {
        RuntimeContext.context = context
        call ContextGroovyMethods, closure
    }

    def call(Map context, Closure closure) {
        call new Context(context), closure
    }

    def call(Map context, String flow) {
        RuntimeContext.context = new Context(context)
        call ContextGroovyMethods, flow
    }

}
