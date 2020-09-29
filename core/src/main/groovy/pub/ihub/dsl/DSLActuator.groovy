package pub.ihub.dsl

import groovy.util.logging.Slf4j

import static java.lang.System.currentTimeMillis



/**
 * DSL（Domain Specified Language）执行器
 * @author liheng
 */
@Slf4j
class DSLActuator {

    private static final ThreadLocal<DSLActuator> ACTUATOR_THREAD_LOCAL = new ThreadLocal<>()

    protected MappingsConfig config

    DSLActuator(URL scriptLocation = null) {
        config = new MappingsConfig(scriptLocation as URL)
    }

    DSLActuator(Map<String, Object> nameClassMappings, Map<String, Closure> nameFlowMappings,
                Map<String, Closure> flows = [:]) {
        config = new MappingsConfig(nameClassMappings, nameFlowMappings, flows)
    }

    static DSLActuator getThreadLocalActuator() {
        ACTUATOR_THREAD_LOCAL.get()
    }

    protected doCall(Class groovyMethods, Closure flow, ... args) {
        use(groovyMethods, flow.with { it.rehydrate this, owner, thisObject })
                .with { it instanceof Closure ? use(groovyMethods, it) : it }
    }

    /**
     * 执行流程
     * @param groovyMethods 执行环境方法
     * @param flow 执行流程
     * @param args 执行参数
     * @return 执行结果
     */
    protected execute(Class groovyMethods, flow, ... args) {
        def beginTime = currentTimeMillis()
        log.debug '开始执行流程 >>>>>>>>>>'
        ACTUATOR_THREAD_LOCAL.set this
        try {
            doCall groovyMethods, flow, args
        } finally {
            ACTUATOR_THREAD_LOCAL.remove()
            def time = (currentTimeMillis() - beginTime) / 1000
            log.debug '<<<<<<<<<< 流程执行结束，耗时{}秒', time
        }
    }

    def call(Class groovyMethods = DSLGroovyMethods, Closure flow) {
        execute groovyMethods, flow
    }

    def call(Class groovyMethods = DSLGroovyMethods, String flow) {
        call groovyMethods, config.getFlow(flow)
    }

    def propertyMissing(String name) {
        config.getMissProperty name, this
    }

    def propertyMissing(String name, value) {
        config.putNameClazzMapping name, value
    }

    def methodMissing(String name, args) {
        actuatorMethodMissing name, args
    }

    /**
     * 执行器方法丢失（用于执行第一个方法）
     * @param name 方法名称
     * @param args 参数
     * @return 执行结果
     */
    def actuatorMethodMissing(String name, args) {
        log.trace '执行器 >>>执行>>> {} ({})', name, args
        runMethod config.getMethod(name), args
    }

    /**
     * 当前执行对象方法丢失
     * @param self 当前执行对象
     * @param name 方法名称
     * @param args 参数
     * @return 执行结果
     */
    def selfMethodMissing(self, String name, args) {
        log.trace '{} >>>执行>>> {} ({})', self, name, args
        config.getMethod(name).with {
            if (it instanceof Closure) {
                runMethod it.curry(self), args
            } else if (it instanceof String) {
                self."$it" getArgs(args)
            } else if (it instanceof DSLMethod) {
                runMethod it, [self] + args.toList()
            } else {
                throw new DSLException("${name}方法类型无法执行！")
            }
        }
    }

    private static runMethod(Closure method, args) {
        method getArgs(args)
    }

    private static runMethod(DSLMethod method, args) {
        method args as Object[]
    }

    private static runMethod(method, args) {
        throw new DSLException("${method.toString()}方法类型无法执行！")
    }

    private static getArgs(args) {
        args.with { size() > 1 ? toList() : first() }
    }

}
