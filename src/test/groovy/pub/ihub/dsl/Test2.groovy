package pub.ihub.dsl


import groovy.util.logging.Slf4j
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll



/**
 * @author liheng
 */
@Title('测试套件')
@Slf4j
class Test2 extends Specification {

    private builder = new DSLActuator(this.class.classLoader.getResource('test2.dsl'))
    @Shared
    private builder2

    final setupSpec() {
        builder2 = new ShellActuator(this.class.classLoader.getResource('test2.dsl'))
    }

    /**
     * 用例01
     */
    @Unroll
    '单元测试 #name'() {
        when: '执行自定义流程'
        def result = builder flow

        then: '期望'
        result == expected

        where:
        name     | flow                        | expected
        'flow_1' | { 方法一 参数一 }                 | 'p1'
        'flow_2' | { 片段一 }                     | 'p1-p2-p3'
        'flow_3' | { 方法一 参数一 方法二 参数二 方法二 参数三 } | 'p1-p2-p3'
        'flow_4' | { 参数一.方法二 参数二 方法二 参数三 }     | 'p1-p2-p3'
        'flow_5' | { 方法五 参数一, 参数二 方法二 参数三 }    | 'p1-p2-p3'
    }

    /**
     * 用例02
     */
    @Unroll
    '单元测试GroovyShell #name'() {
        when: '执行自定义流程'
        def result = builder2.call flow

        then: '期望'
        result == expected

        where:
        name     | flow                      | expected
        'flow_1' | '方法一 参数一'                 | 'p1'
        'flow_2' | '方法一 参数一 方法二 参数二 方法二 参数三' | 'p1-p2-p3'
        'flow_3' | '参数一.方法二 参数二 方法二 参数三'     | 'p1-p2-p3'
        'flow_4' | '方法五 参数一, 参数二 方法二 参数三'    | 'p1-p2-p3'
    }

    /**
     * 用例03
     */
    @Unroll
    '单元测试cache #name'() {
        when: '执行自定义流程'
        def result = builder2.call flow, true

        then: '期望'
        result == expected

        where:
        name     | flow                   | expected
        'flow_1' | '方法五 参数一, 参数二 方法二 参数三' | 'p1-p2-p3'
        'flow_2' | '方法五 参数一, 参数二 方法二 参数三' | 'p1-p2-p3'
        'flow_3' | '方法五 参数一, 参数二 方法二 参数三' | 'p1-p2-p3'
    }

    /**
     * 用例04
     */
    @Unroll
    '单元测试args #name'() {
        when: '执行自定义流程'
        def result = builder2.call 'args[0] + "-" + args[1] + "-" + args[2]', true, args as String[]

        then: '期望'
        result == expected

        where:
        name     | args               | expected
        'flow_1' | ['p1', 'p2', 'p3'] | 'p1-p2-p3'
        'flow_2' | ['p4', 'p5', 'p6'] | 'p4-p5-p6'
        'flow_3' | ['p7', 'p8', 'p9'] | 'p7-p8-p9'
    }

}
