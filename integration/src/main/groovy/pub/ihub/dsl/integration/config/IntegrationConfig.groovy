package pub.ihub.dsl.integration.config

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.context.IntegrationFlowContext
import pub.ihub.dsl.integration.MessageFlowBuilder

import javax.annotation.PostConstruct

import static org.springframework.integration.dsl.IntegrationFlows.from



/**
 * 消息配置
 * @author liheng
 */
@Slf4j
@Configuration
@EnableIntegration
class IntegrationConfig {

    private static final ThreadLocal<IntegrationFlowContext> CONTEXT_THREAD_LOCAL = new ThreadLocal<>()

    @Autowired
    private IntegrationFlowContext integrationFlowContext

//    @Autowired
//    private List<ChannelInterceptor> channelInterceptors

    @Autowired(required = false)
    private List<MessageFlowBuilder> flowBuilders

    static IntegrationFlowContext getIntegrationFlowContext() {
        CONTEXT_THREAD_LOCAL.get()
    }

    @Bean
//    @ConditionalOnMissingBean
    IntegrationFlow errorFlow() {
        from('errorChannel').handle(Object) { p, h -> log.error '消息处理异常：' + p }.get()
    }

    @PostConstruct
    void registerIntegrationFlows() {
        log.info '开始初始化消息通道'
        CONTEXT_THREAD_LOCAL.set integrationFlowContext
        flowBuilders*.build()
        CONTEXT_THREAD_LOCAL.remove()
        log.info '消息流程完成初始化'
    }

}
