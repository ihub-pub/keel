package pub.ihub.dsl.context

import groovy.util.logging.Slf4j

import static pub.ihub.dsl.MappingsConfig.nameBindingsMappings
import static pub.ihub.dsl.context.Context.context



/**
 * 上下文环境方法
 *
 * 主要用于处理：
 * 1、上下文执行第一、二步
 * 2、绑定流程模板运行时变量（步骤/属性/子流程）映射
 * @author liheng
 */
@Slf4j
class ContextGroovyMethods {

    static Context rightShift(IStep first, IStep second) {
        context >> first >> second
    }

    static Context rightShift(Closure first, IStep second) {
        context >> first >> second
    }

    static Context rightShift(IStep first, Closure second) {
        context >> first >> second
    }

    static Context rightShift(Closure first, Closure second) {
        context >> first >> second
    }

    static Context rightShift(IStep step, Map fork) {
        context >> step >> fork
    }

    static Closure leftShift(Closure template, Map bindings) {
        nameBindingsMappings = bindings
        template
    }

}
