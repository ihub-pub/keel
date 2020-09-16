package pub.ihub.dsl.test

import groovy.util.logging.Slf4j
import pub.ihub.dsl.DSLActuator
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll



/**
 * @author liheng
 */
@Title('测试套件')
@Slf4j
class DSLActuatorUT extends Specification {

    @Shared
    private builder

    final setupSpec() {
        builder = new DSLActuator(this.class.classLoader.getResource('test2.dsl'))
    }

    /**
     * 用例01
     */
    @Unroll
    '单元测试 #name'() {
        when: '执行自定义流程'
        def result = builder.call flow

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

}
