package pub.ihub.dsl.context

import groovy.util.logging.Slf4j



/**
 * 运行时上下文
 * @author liheng
 */
@Slf4j
class RuntimeContext extends Context {

    private static final ThreadLocal<Context> CONTEXT_THREAD_LOCAL = new ThreadLocal<>()

    Integer flag
    Integer status
    Object router
    String version
    Exception exception

    RuntimeContext() {
        super(CONTEXT_THREAD_LOCAL.get())
        CONTEXT_THREAD_LOCAL.remove()
        log.trace '<<<<<<<<<< 初始化上下文 >>>>>>>>>>'
    }

    /**
     * 执行步骤
     * @param step 步骤
     * @return 上下文
     */
    RuntimeContext rightShift(IStep step) {
        log.trace '执行步骤 >>>>>> {}', step
        this << step.run(this)
    }

    /**
     * 选择执行分支流程
     * @param fork 分支流程
     * @return 上下文
     */
    RuntimeContext rightShift(Map fork) {
        log.trace '选择执行分支流程 >>>>>> {}', fork
        this >> fork[this.router]
    }

    /**
     * 执行子流程
     * @param subFlow 子流程
     * @return 上下文
     */
    RuntimeContext rightShift(Closure<RuntimeContext> subFlow) {
        log.trace '执行子流程 >>>>>> {}', subFlow
        context = this
        use(ContextGroovyMethods, subFlow).with {
            it instanceof IStep ? this >> it : it
        }
    }

    RuntimeContext leftShift(Tuple4<Integer, Integer, ?, String> fsrv) {
        flag = fsrv.first
        status = fsrv.second
        router = fsrv.third
        version = fsrv.fourth
        this
    }

    static void setContext(Context context) {
        CONTEXT_THREAD_LOCAL.set context
    }

    @Override
    Object getProperty(String propertyName) {
        super.getProperty(propertyName).with {
            null == it ? this.getProperties().find { it.key == propertyName }?.value : it
        }
    }

}
