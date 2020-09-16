package pub.ihub.dsl.test

import groovy.util.logging.Slf4j
import pub.ihub.dsl.context.ContextActuator
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll



/**
 * // TODO 测试用例整理
 * @author liheng
 */
@Title('测试套件')
@Slf4j
class ContextActuatorUT extends Specification {

    @Shared
    private builder

    final setupSpec() {
        builder = new ContextActuator(this.class.classLoader.getResource('test.dsl'))
    }

    /**
     * 用例01
     */
    @Unroll
    '单元测试 flow_demo'() {
        when: '执行配置流程'
        def result = builder.call([steps: []], 'flow_demo')

        then: '期望'
        result == ['首步', '第一步', '第二步', '第三步', '第四步', '最后一步']
    }

    /**
     * 用例02
     */
    @SuppressWarnings('DuplicateListLiteral')
    @Unroll
    '单元测试 #name'() {
        when: '执行自定义流程'
        def result = builder.call([steps: []], flow)

        then: '期望'
        result == expected

        where:
        name       | flow                                                      | expected
        '单步骤'      | { 只有一步 }                                                  | ['只有一步']
        '多步骤'      | { 第一步 >> 第二步 >> 第三步 >> 第四步 }                              | ['第一步', '第二步', '第三步', '第四步']
        '单片段'      | { 片段二 }                                                   | ['第一步', '第二步', '第三步', '第四步']
        '片段+步骤'    | { 片段二 >> 最后一步 }                                           | ['第一步', '第二步', '第三步', '第四步', '最后一步']
        '步骤+片段'    | { 首步 >> 片段二 }                                             | ['首步', '第一步', '第二步', '第三步', '第四步']
        '多片段'      | { 片段三 >> 片段四 }                                            | ['第一步', '第二步', '第三步', '第四步']
        '单步骤片段+步骤' | { 片段一 >> 最后一步 }                                           | ['第一步', '最后一步']
        '路由步骤'     | { 路由一 >> [1: 第一步, 2: 第二步] }                               | ['第一步']
        '路由片段'     | { 路由二 >> [1: 片段三, 2: 片段四] }                               | ['第三步', '第四步']
        '路由闭包+步骤'  | { 路由一 >> [1: { 第一步 >> 第二步 }, 2: { 第三步 >> 第四步 }] >> 最后一步 } | ['第一步', '第二步', '最后一步']
        '单模板'      | { 模板一 << [T1: 第二步, T2: 第一步] }                             | ['第二步', '第一步']
        '模板步骤混合'   | { 首步 >> (模板一 << [T1: 默认步骤, T2: 片段二]) >> 最后一步 }            | ['首步', '默认步骤', '第一步', '第二步', '第三步', '第四步', '最后一步']
    }

}
