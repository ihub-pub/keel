package pub.ihub.dsl.context

import groovy.util.logging.Slf4j
import pub.ihub.dsl.DSLActuator



/**
 * 基于上下文DSL执行器
 * @author liheng
 */
@Slf4j
class ContextActuator extends DSLActuator {

    ContextActuator(Map<String, Object> nameClassMappings, Map<String, Closure> nameFlowMappings = [:]) {
        super(nameClassMappings, nameFlowMappings)
    }

    ContextActuator(URL scriptLocation) {
        super(scriptLocation)
    }

    @SuppressWarnings('UnnecessaryGetter')
    protected doCall(Class groovyMethods, Closure flow, ... args) {
        def context = args.first() as Context
        Context.context = context
        // 如果执行结果为IStep或者Closure<RuntimeContext>时需要继续执行该步骤/子流程（流程只有一步的情况）
        use(groovyMethods, flow.with { it.rehydrate this, owner, thisObject })
                .with { it instanceof IStep || it instanceof Closure<Context> ? context >> it : it }.getPayload()
    }

    def call(Context context, Closure closure) {
        execute ContextGroovyMethods, closure, context
    }

    def call(Map context, Closure closure) {
        call new Context(context), closure
    }

    def call(Context context, String flow) {
        call context, config.getFlow(flow)
    }

    def call(Map context, String flow) {
        call new Context(context), flow
    }

}
