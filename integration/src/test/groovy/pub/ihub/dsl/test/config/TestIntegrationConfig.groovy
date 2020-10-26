package pub.ihub.dsl.test.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.MessageHandlerSpec
import org.springframework.integration.dsl.MessageProcessorSpec
import org.springframework.integration.handler.MessageProcessor
import org.springframework.integration.router.MethodInvokingRouter
import org.springframework.integration.router.PayloadTypeRouter
import org.springframework.messaging.MessageHandler
import pub.ihub.dsl.integration.MessageFlowBuilder
import pub.ihub.dsl.test.endpoint.TestFilter
import pub.ihub.dsl.test.endpoint.TestGenericHandler
import pub.ihub.dsl.test.endpoint.TestGenericSelector
import pub.ihub.dsl.test.endpoint.TestGenericTransformer
import pub.ihub.dsl.test.endpoint.TestHandler
import pub.ihub.dsl.test.endpoint.TestMessageHandler
import pub.ihub.dsl.test.endpoint.TestRouter
import pub.ihub.dsl.test.endpoint.TestTransformer

import static org.springframework.integration.dsl.MessageChannels.publishSubscribe
import static pub.ihub.dsl.integration.MessageEndpoints.filter
import static pub.ihub.dsl.integration.MessageEndpoints.handle
import static pub.ihub.dsl.integration.MessageEndpoints.route
import static pub.ihub.dsl.integration.MessageEndpoints.split
import static pub.ihub.dsl.integration.MessageEndpoints.transform



/**
 * @author liheng
 */
@Configuration
@SuppressWarnings(['Println', 'MethodSize'])
class TestIntegrationConfig {

    @Bean
    TestHandler handler() {
        new TestHandler(name: 'bean')
    }

    @Bean
    TestRouter router() {
        new TestRouter(name: '路由器三')
    }

    @Bean
    MessageFlowBuilder flowBuilder() {
        // TODO 可整理为流程配置文件
        new MessageFlowBuilder([
                测试通道一  : 'test1',
                测试通道二  : 'test2',
                测试通道三  : 'test3',
                测试通道四  : 'test4',
                测试通道五  : 'test5',
                路由器测试通道: publishSubscribe('routeChannel'),
                分流器测试通道: publishSubscribe('splitChannel'),
                处理器一   : new TestGenericHandler(name: '处理器一'),
                处理器二   : new TestMessageHandler(name: '处理器二'),
                处理器十   : new MessageHandlerSpec<MessageHandlerSpec, MessageHandler>() {

                    @Override
                    protected MessageHandler doGet() {
                        new TestMessageHandler(name: '处理器十')
                    }

                },
                处理器三   : handle(new TestMessageHandler(name: '处理器三')),
                处理器四   : handle(new TestGenericHandler(name: '处理器四')),
                处理器五   : handle(new MessageHandlerSpec<MessageHandlerSpec, MessageHandler>() {

                    @Override
                    protected MessageHandler doGet() {
                        new TestMessageHandler(name: '处理器五')
                    }

                }),
                处理器六   : handle('handler'),
                处理器七   : handle(new TestHandler(name: '处理器七'), 'handle'),
                处理器八   : handle(String, new TestGenericHandler(name: '处理器八')),
                处理器九   : handle(new MessageProcessorSpec<MessageProcessorSpec>() {

                    @Override
                    protected MessageProcessor doGet() {
                        new TestHandler(name: '处理器九')
                    }

                }),
                过滤器一   : filter(new TestGenericSelector(name: '过滤器一')),
                过滤器二   : filter('\'error\' != payload'),
                过滤器三   : filter(new TestFilter(name: '过滤器三'), 'filter'),
                过滤器四   : filter(String, new TestGenericSelector(name: '过滤器四')),
                过滤器五   : filter(new MessageProcessorSpec<MessageProcessorSpec>() {

                    @Override
                    protected MessageProcessor doGet() {
                        new TestFilter(name: '过滤器五')
                    }

                }),
                过滤器六   : new TestGenericSelector(name: '过滤器六'),
                转换器一   : transform('new Integer(payload)'),
                转换器二   : transform(new TestTransformer(name: '转换器二'), 'transform'),
                转换器三   : transform(String, new TestGenericTransformer(name: '转换器三')),
                转换器四   : transform(new MessageProcessorSpec<MessageProcessorSpec>() {

                    @Override
                    protected MessageProcessor doGet() {
                        new TestTransformer(name: '转换器四')
                    }

                }),
                转换器五   : new TestGenericTransformer(name: '转换器五'),
                // TODO 加入流程
                路由器一   : new PayloadTypeRouter(),
                路由器二   : route(new MethodInvokingRouter(new TestRouter(name: '路由器二'))),
                路由器三   : route('router', 'processMessage'),
                路由器四   : route(new TestRouter(name: '路由器四'), 'processMessage'),
                路由器五   : route(new MessageProcessorSpec<MessageProcessorSpec>() {

                    @Override
                    protected MessageProcessor doGet() {
                        new TestRouter(name: '路由器五')
                    }

                }),
                分流器一   : split(new TestRouter(name: '路由器四'), 'processMessage')
        ], [
                流程一     : {
                    测试通道一 >> 处理器一 >> 处理器二
                },
                流程二     : {
                    测试通道二 >> 处理器六 >> 处理器七 >> 处理器八 >> 处理器九 >> 处理器三
                },
                流程三     : {
                    测试通道三 >> 处理器四 >> 处理器五
                },
                流程四     : {
                    测试通道四 >> 过滤器一 >> 过滤器二 >> 过滤器三 >> 过滤器四 >> 过滤器五 >> 过滤器六 >> 处理器十
                },
                流程五     : {
                    测试通道五 >> 转换器一 >> 转换器二 >> 转换器三 >> 转换器四 >> 转换器五 >> 处理器十
                },
                路由器测试流程一: {
                    路由器测试通道 >> [
                            分支一: { 处理器一 >> 处理器二 },
                            分支二: { 处理器十 }
                    ]
                },
                路由器测试流程二: {
                    路由器测试通道 >>
                            // TODO 后续无法接步骤，router不被识别为Lambda
//                            route(String, { payload -> payload }, [
//                                    test1: { 过滤器一 >> 过滤器二 >> 过滤器三 },
//                                    test2: { 处理器一 }
//                            ]) >>
                            route('payload', [
                                    test1  : { 过滤器四 >> 过滤器五 >> 过滤器六 >> 处理器十 },
                                    default: { 处理器十 }
                            ])
                },
                路由器测试流程三: {
                    路由器测试通道 >> route('payload')
                },
                路由器测试流程四: {
                    路由器测试通道 >> route(String) { payload -> payload }
                },
                路由器测试流程五: {
                    路由器测试通道 >> route(String, { payload -> payload }, [
                            test1: { 过滤器一 >> 过滤器二 >> 过滤器三 >> 处理器十 },
                            test2: { 处理器一 >> 处理器十 }
                    ])
                },
                路由器测试流程六: {
                    路由器测试通道 >> 路由器二
                },
                分流器测试流程一: {
                    分流器测试通道 >> split >> 处理器十
                }
        ])
    }

}
