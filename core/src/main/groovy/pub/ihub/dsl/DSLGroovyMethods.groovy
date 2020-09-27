package pub.ihub.dsl


import static DSLActuator.threadLocalActuator



/**
 * DSL环境方法
 * 主要用于处理流程执行期间方法丢失
 * @author liheng
 */
class DSLGroovyMethods {

    static methodMissing(DSLActuator self, String name, args) {
        self.actuatorMethodMissing name, args
    }

    static methodMissing(self, String name, args) {
        if (!self) {
            throw new DSLException("上一步返回值为null，方法“${name}”无法执行！",
                    new NullPointerException("Cannot get property '${name}' on null object"))
        }
        threadLocalActuator.selfMethodMissing self, name, args
    }

}
