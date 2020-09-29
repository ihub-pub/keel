package pub.ihub.dsl.test

import groovy.util.logging.Slf4j
import pub.ihub.dsl.context.ContextActuator
import pub.ihub.dsl.step.Step
import spock.lang.Title
import spock.lang.Unroll



/**
 * @author liheng
 */
@Title('上下文DSL执行器测试套件')
@Slf4j
class ContextActuatorUT extends ATestActuatorUT {

    @Override
    def getActuator(URL scriptLocation) {
        new ContextActuator(scriptLocation)
    }

    /**
     * 用例01
     */
    @Unroll
    '单元测试：测试DSL配置内置流程'() {
        given: '初始化参数'
        def ac = actuator
        def context = [steps: []]

        when: '执行内置流程'
        def result = ac context, 'flow_demo'

        then: '校验期望结果'
        result == ['首步', '第一步', '第二步', '第三步', '第四步', '最后一步']
    }

    /**
     * 用例02
     */
    @Unroll
    '单元测试：自定义流程 - #name'() {
        given: '初始化参数'
        def ac = actuator
        def context = [steps: []]

        when: '执行自定义流程'
        def result = ac context, flow

        then: '校验期望结果'
        result == expected

        where:
        name       | flow                                                               | expected
        '单步骤'      | { 只有一步 }                                                           | ['只有一步']
        '多步骤'      | { 第一步 >> 第二步 >> 第三步 >> 第四步 >> 默认步骤 }                               | ['第一步', '第二步', '第三步', '第四步', '默认步骤']
        '单片段'      | { 片段二 }                                                            | ['第一步', '第二步']
        '片段+步骤'    | { 片段一 >> 最后一步 }                                                    | ['第一步', '第二步', '第三步', '第四步', '最后一步']
        '步骤+片段'    | { 首步 >> 片段一 }                                                      | ['首步', '第一步', '第二步', '第三步', '第四步']
        '多片段'      | { 片段二 >> 片段三 }                                                     | ['第一步', '第二步', '第三步', '第四步']
        '单步骤片段+步骤' | { 片段四 >> 最后一步 }                                                    | ['只有一步', '最后一步']
        '路由步骤'     | { 是 >> [(true): 第一步, (false): 第二步] }                               | ['第一步']
        '路由片段'     | { 否 >> [(true): 片段二, (false): 片段三] }                               | ['第三步', '第四步']
        '路由闭包+步骤'  | { 是 >> [(true): { 第一步 >> 第二步 }, (false): { 第三步 >> 第四步 }] >> 最后一步 } | ['第一步', '第二步', '最后一步']
        '单模板'      | { 模板一 << [T1: 第二步, T2: 第一步] }                                      | ['第二步', '第一步']
        '模板步骤混合'   | { 首步 >> (模板一 << [T1: 默认步骤, T2: 片段一]) >> 最后一步 }                     | ['首步', '默认步骤', '第一步', '第二步', '第三步', '第四步', '最后一步']
    }

    /**
     * 用例03
     */
    @Unroll
    '单元测试：测试构造器初始化上下文执行器'() {
        given: '初始化参数'
        def ac = new ContextActuator([
                第一步: new Step('第一步'),
                第二步: new Step('第二步'),
                第三步: new Step('第三步')
        ])
        def context = [steps: []]

        when: '执行内置流程'
        def result = ac context, { 第一步 >> 第二步 >> 第三步 }

        then: '校验期望结果'
        result == ['第一步', '第二步', '第三步']
    }

}
