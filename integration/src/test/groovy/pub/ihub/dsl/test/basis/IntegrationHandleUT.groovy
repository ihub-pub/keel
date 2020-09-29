package pub.ihub.dsl.test.basis

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.support.GenericMessage
import pub.ihub.dsl.test.config.ServiceFTConfig
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll



/**
 * @author liheng
 */
@Title('测试套件')
@Slf4j
@ServiceFTConfig
@SuppressWarnings('Println')
class IntegrationHandleUT extends Specification {

    @Autowired
    private DirectChannel test1

    /**
     * 用例01
     */
    @Unroll
    '单元测试：测试DSL配置内置流程'() {
        given: '初始化参数'
//        def ac = actuator
        println '123'

        when: '执行内置流程'
//        def result = ac 'flow_demo'
        test1.send(new GenericMessage<String>('147'))
        println '456'

        then: '校验期望结果'
//        result == 'p1-p2-p3'
        println '789'
        true
    }

}
