package pub.ihub.dsl.test.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.MessageHandlerSpec
import org.springframework.integration.dsl.MessageProcessorSpec
import org.springframework.integration.handler.MessageProcessor
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.MessageHeaders
import pub.ihub.dsl.integration.MessageFlowBuilder
import pub.ihub.dsl.test.endpoint.TestFilter
import pub.ihub.dsl.test.endpoint.TestGenericHandler
import pub.ihub.dsl.test.endpoint.TestGenericSelector
import pub.ihub.dsl.test.endpoint.TestHandler
import pub.ihub.dsl.test.endpoint.TestMessageHandler
import pub.ihub.dsl.test.endpoint.TestTransformer

import static pub.ihub.dsl.integration.MessageEndpoints.filter
import static pub.ihub.dsl.integration.MessageEndpoints.handle
import static pub.ihub.dsl.integration.MessageEndpoints.transform



/**
 * @author liheng
 */
@Configuration
@SuppressWarnings('Println')
class TestIntegrationConfig {

    @Bean
    TestHandler handler() {
        new TestHandler(name: 'bean')
    }

    @Bean
    MessageFlowBuilder flowBuilder() {
        new MessageFlowBuilder([
                测试通道一: 'test1',
                测试通道二: 'test2',
                测试通道三: 'test3',
                测试通道四: 'test4',
                测试通道五: 'test5',
                处理器一 : new TestGenericHandler(name: '处理器一'),
                处理器二 : new TestMessageHandler(name: '处理器二'),
                处理器三 : handle(new TestMessageHandler(name: '处理器三')),
                处理器四 : handle(new TestGenericHandler(name: '处理器四')),
                处理器五 : handle(new MessageHandlerSpec<MessageHandlerSpec, MessageHandler>() {

                    @Override
                    protected MessageHandler doGet() {
                        new TestMessageHandler(name: '处理器五')
                    }

                }),
                处理器六 : handle('handler'),
                处理器七 : handle(new TestHandler(name: '处理器七'), 'handle'),
                处理器八 : handle(String) { String payload, MessageHeaders headers ->
                    println 'handle 处理器八, payload ' + payload + ', headers ' + headers
                    payload
                },
                处理器九 : handle(new MessageProcessorSpec<MessageProcessorSpec>() {

                    @Override
                    protected MessageProcessor doGet() {
                        new TestHandler(name: '处理器九')
                    }

                }),
                过滤器一 : filter(new TestGenericSelector(name: '过滤器一')),
                过滤器二 : filter('\'error\' != payload'),
                过滤器三 : filter(new TestFilter(name: '过滤器三'), 'filter'),
                过滤器四 : filter(String) { String source ->
                    println 'filter 过滤器四, source ' + source
                    'error' != source
                },
                过滤器五 : filter(new MessageProcessorSpec<MessageProcessorSpec>() {

                    @Override
                    protected MessageProcessor doGet() {
                        new TestFilter(name: '过滤器五')
                    }

                }),
                转换器一 : transform('new Integer(payload)'),
                转换器二 : transform(new TestTransformer(name: '转换器二'), 'transform'),
                转换器三 : transform(String) { String source ->
                    println 'transform 转换器三, source ' + source
                    source as Integer
                },
                转换器四 : transform(new MessageProcessorSpec<MessageProcessorSpec>() {

                    @Override
                    protected MessageProcessor doGet() {
                        new TestTransformer(name: '转换器四')
                    }

                })
        ], [
                流程一: {
                    测试通道一 >> 处理器一 >> 处理器二
                },
                流程二: {
                    测试通道二 >> 处理器六 >> 处理器七 >> 处理器八 >> 处理器九 >> 处理器三
                },
                流程三: {
                    测试通道三 >> 处理器四 >> 处理器五
                },
                流程四: {
                    测试通道四 >> 过滤器一 >> 过滤器二 >> 过滤器三 >> 过滤器四 >> 过滤器五 >> 处理器三
                },
                流程五: {
                    测试通道五 >> 转换器一 >> 转换器二 >> 转换器三 >> 转换器四 >> 处理器三
                }
        ])
    }

}
