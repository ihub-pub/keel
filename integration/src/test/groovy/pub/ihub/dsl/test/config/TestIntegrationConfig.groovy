package pub.ihub.dsl.test.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.handler.GenericHandler
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.MessageHeaders
import pub.ihub.dsl.integration.MessageFlowBuilder



/**
 * @author liheng
 */
@Configuration
@SuppressWarnings('Println')
class TestIntegrationConfig {

    @Bean
    MessageFlowBuilder flowBuilder() {
        new MessageFlowBuilder([
                测试通道一: 'test1',
                处理器一 : new GenericHandler<String>() {

                    @Override
                    Object handle(String payload, MessageHeaders headers) {
                        println payload
                        println headers
                        payload
                    }

                },
                处理器二 : new MessageHandler() {

                    @Override
                    void handleMessage(Message<?> message) {
                        println message
                        message
                    }

                },
        ], [
                流程一: {
                    测试通道一 >> 处理器一 >> 处理器二
                }
        ])
    }

}
