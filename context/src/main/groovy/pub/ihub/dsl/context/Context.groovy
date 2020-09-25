package pub.ihub.dsl.context

import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j



/**
 * 上下文
 * @author liheng
 */
@TupleConstructor(includeFields = true, excludes = 'payload')
@Slf4j
class Context {

    /**
     * 局部上下文（用于向闭包内步骤传递上下文）
     */
    private static final ThreadLocal<Context> CONTEXT_THREAD_LOCAL = new ThreadLocal<>()

    @Delegate
    private Map delegate
    /**
     * 步骤执行结果
     */
    def payload

    /**
     * 执行步骤
     * @param step 步骤
     * @return 上下文
     */
    Context rightShift(IStep step) {
        log.trace '执行步骤 >>>>>> {}', step
        this << step.run(this)
    }

    /**
     * 选择执行分支流程
     * @param fork 分支流程
     * @return 上下文
     */
    Context rightShift(Map fork) {
        log.trace '选择执行分支流程 >>>>>> {}', fork
        this >> fork[payload]
    }

    /**
     * 执行子流程
     * @param subFlow 子流程
     * @return 上下文
     */
    Context rightShift(Closure<Context> subFlow) {
        log.trace '执行子流程 >>>>>> {}', subFlow
        context = this
        use(ContextGroovyMethods, subFlow).with {
            it instanceof IStep ? this >> it : it
        }
    }

    Context leftShift(payload) {
        this.payload = payload
        this
    }

    static void setContext(Context context) {
        CONTEXT_THREAD_LOCAL.set context
    }

    static Context getContext() {
        def context = CONTEXT_THREAD_LOCAL.get()
        CONTEXT_THREAD_LOCAL.remove()
        context
    }

}
