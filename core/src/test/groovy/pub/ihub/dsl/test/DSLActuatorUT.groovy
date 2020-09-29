package pub.ihub.dsl.test

import groovy.util.logging.Slf4j
import pub.ihub.dsl.DSLActuator
import pub.ihub.dsl.DSLException
import spock.lang.Title
import spock.lang.Unroll



/**
 * @author liheng
 */
@Title('DSL执行器测试套件')
@Slf4j
class DSLActuatorUT extends ATestActuatorUT {

    @Override
    def getActuator(URL scriptLocation) {
        new DSLActuator(scriptLocation)
    }

    /**
     * 用例01
     */
    @Unroll
    '单元测试：测试DSL配置内置流程'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行内置流程'
        def result = ac 'flow_demo'

        then: '校验期望结果'
        result == 'p1-p2-p3'
    }

    /**
     * 用例02
     */
    @Unroll
    '单元测试：自定义流程 - #name'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行自定义流程'
        def result = ac flow

        then: '校验期望结果'
        result == expected

        where:
        name      | flow                        | expected
        '单方法'     | { 方法一 参数一 }                 | 'p1'
        '单片段'     | { 片段一 }                     | 'p1-p2-p3'
        '方法开头'    | { 方法一 参数一 方法二 参数二 方法二 参数三 } | 'p1-p2-p3'
        '属性开头'    | { 参数一.方法二 参数二 方法二 参数三 }     | 'p1-p2-p3'
        '自定义接口方法' | { 方法五 参数一, 参数二 方法二 参数三 }    | 'p1-p2-p3'
    }

    /**
     * 用例03
     */
    @Unroll
    '单元测试：自定义环境方法'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行自定义流程'
        def result = ac DefGroovyMethods, { 方法一 参数一 >> 参数二 >> 参数三 }

        then: '校验期望结果'
        result == 'p1-p2-p3'
    }

    /**
     * 用例04
     */
    @Unroll
    '单元测试：属性不存在'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行自定义流程'
        ac { 方法一 一 方法二 二 方法二 三 }

        then: '期望：属性不存在'
        def ex = thrown MissingPropertyException
        'No such property: 一 for class: pub.ihub.dsl.MappingsConfig' == ex.message
    }

    /**
     * 用例05
     */
    @Unroll
    '单元测试：自定义流程不存在'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行自定义流程'
        ac 'demo'

        then: '期望：自定义流程不存在'
        def ex = thrown MissingPropertyException
        'No such property: demo for class: pub.ihub.dsl.MappingsConfig' == ex.message
    }

    /**
     * 用例06
     */
    @Unroll
    '单元测试：方法不存在'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行自定义流程'
        ac { 不存在方法 参数一 }

        then: '期望：方法不存在'
        def ex = thrown MissingMethodException
        ex.message.contains 'pub.ihub.dsl.MappingsConfig.不存在方法()'
    }

    /**
     * 用例07
     */
    @Unroll
    '单元测试：不可执行方法'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行器不可执行方法'
        ac { 不可执行方法 参数一 }

        then: '期望：不可执行方法'
        def ex = thrown DSLException
        ex.message.contains '方法类型无法执行！'

        when: '当前执行对象不可执行方法'
        ac { 方法一 参数一 不可执行方法 参数二 }

        then: '期望：不可执行方法'
        ex = thrown DSLException
        ex.message.contains '方法类型无法执行！'
    }

    /**
     * 用例08
     */
    @Unroll
    '单元测试：上一步返回值为null，无法继续执行方法'() {
        given: '初始化参数'
        def ac = actuator
        ac.无返回值方法 = { a -> }

        when: '执行自定义流程'
        ac { 无返回值方法 参数一 方法二 参数二 方法二 参数三 }

        then: '期望：不可执行方法'
        def ex = thrown DSLException
        ex.message.contains '上一步返回值为null'
    }

    private class DefGroovyMethods {

        static rightShift(String self, String str) {
            [self, str].join '-'
        }

    }

}
