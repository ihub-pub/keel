package pub.ihub.dsl.test

import groovy.util.logging.Slf4j
import pub.ihub.dsl.ShellActuator
import spock.lang.Title
import spock.lang.Unroll



/**
 * @author liheng
 */
@Title('GroovyShell DSL执行器测试套件')
@Slf4j
class ShellActuatorUT extends ATestActuatorUT {

    @Override
    def getActuator(URL scriptLocation) {
        new ShellActuator()
    }

    /**
     * 用例01
     */
    @Unroll
    '单元测试：自定义流程 - #name'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行自定义流程'
        def result = ac flow

        then: '期望'
        result == expected

        where:
        name      | flow                      | expected
        '单方法'     | '方法一 参数一'                 | 'p1'
        '多方法'     | '方法一 参数一 方法二 参数二 方法二 参数三' | 'p1-p2-p3'
        '属性开头'    | '参数一.方法二 参数二 方法二 参数三'     | 'p1-p2-p3'
        '自定义接口方法' | '方法五 参数一, 参数二 方法二 参数三'    | 'p1-p2-p3'
    }

    /**
     * 用例02
     */
    @Unroll
    '单元测试cache #name'() {
        given: '初始化参数'
        def ac = actuator

        when: '执行自定义流程'
        def result = ac 'args[0] + "-" + args[1] + "-" + args[2]', args as String[]

        then: '期望'
        result == expected

        where:
        name     | args               | expected
        'flow_1' | ['p1', 'p2', 'p3'] | 'p1-p2-p3'
        'flow_2' | ['p4', 'p5', 'p6'] | 'p4-p5-p6'
        'flow_3' | ['p7', 'p8', 'p9'] | 'p7-p8-p9'
    }

    /**
     * 用例03
     */
    @Unroll
    '单元测试：测试构造器初始化执行器'() {
        given: '初始化参数'
        def ac = new ShellActuator([
                参数一: 'p1',
                参数二: 'p2',
                参数三: 'p3'
        ])

        when: '执行内置流程'
        def result = ac '参数一 + \'-\' + 参数二 + \'-\' + 参数三'

        then: '校验期望结果'
        result == 'p1-p2-p3'
    }

}
