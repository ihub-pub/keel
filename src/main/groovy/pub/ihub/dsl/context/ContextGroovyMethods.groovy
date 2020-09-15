package pub.ihub.dsl.context

import groovy.util.logging.Slf4j
import pub.ihub.dsl.MappingsConfig



/**
 * 上下文环境方法
 *
 * 主要用于处理：
 * 1、上下文初始化以及执行第一、二步
 * 2、绑定流程模板运行时变量（步骤/属性/子流程）映射
 * @author liheng
 */
@Slf4j
class ContextGroovyMethods {

    static RuntimeContext rightShift(IStep first, IStep second) {
        new RuntimeContext() >> first >> second
    }

    static RuntimeContext rightShift(Closure first, IStep second) {
        new RuntimeContext() >> first >> second
    }

    static RuntimeContext rightShift(IStep first, Closure second) {
        new RuntimeContext() >> first >> second
    }

    static RuntimeContext rightShift(Closure first, Closure second) {
        new RuntimeContext() >> first >> second
    }

    static RuntimeContext rightShift(IStep step, Map fork) {
        new RuntimeContext() >> step >> fork
    }

    static RuntimeContext rightShift(Closure closure, Map fork) {
        new RuntimeContext() >> closure >> fork
    }

    static Closure leftShift(Closure template, Map bindings) {
        MappingsConfig.nameBindingsMappings = bindings
        template
    }

}
