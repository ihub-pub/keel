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
class IntegrationFilterFT extends Specification {

    @Autowired
    private DirectChannel test4

    /**
     * 用例01
     */
    @Unroll
    '单元测试：测试DSL配置内置流程'() {
        given: '初始化参数'

        when: '执行内置流程'
        test4.send new GenericMessage<String>('text')

        then: '校验期望结果'
        true
    }

}
