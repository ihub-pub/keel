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

    DSLActuator(URL scriptLocation) {
        config = new MappingsConfig(scriptLocation)
    }

    DSLActuator(Map<String, Object> nameClassMappings, Map<String, Closure> nameFlowMappings) {
        config = new MappingsConfig(nameClassMappings, nameFlowMappings)
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
        } catch (e) {
            throw e
        } finally {
            ACTUATOR_THREAD_LOCAL.remove()
            log.debug '<<<<<<<<<< 流程执行结束，耗时{}秒', (currentTimeMillis() - beginTime) / 1000
        }
    }

    def call(Class groovyMethods = DSLGroovyMethods, Closure flow) {
        execute groovyMethods, flow
    }

    def call(Class groovyMethods = DSLGroovyMethods, String flow) {
        execute groovyMethods, config.getFlow(flow) ?: throwMissingProperty(flow)
    }

    def propertyMissing(String name) {
        config.getClass(name)
                ?: config.getSubFlow(name)?.with { it.rehydrate this, owner, thisObject }
                ?: config.getBinding(name)
                ?: throwMissingProperty(name)
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
        runMethod getMethod(name), args
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
        getMethod(name).with {
            if (it instanceof Closure) {
                runMethod it.curry(self), args
            } else if (it instanceof String) {
                self."$it" getArgs(args)
            } else if (it instanceof DSLMethod) {
                runMethod it, [self] + args.toList()
            } else {
                runMethod it, args
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
        throw new DSLException(method?.toString() + '方法类型无法执行！')
    }

    private static getArgs(args) {
        args.with { size() > 1 ? toList() : first() }
    }

    private getMethod(String name) {
        config.getClass(name).tap {
            if (it) {
                log.trace '获取 <<<方法<<< {} <- {}', name, it
            } else {
                log.error '方法没有定义：{}', name
                throw new MissingMethodException(name, this.class)
            }
        }
    }

    private throwMissingProperty(String name) {
        log.error '步骤/属性/流程没有定义：{}', name
        throw new MissingPropertyException(name, this.class)
    }

}
