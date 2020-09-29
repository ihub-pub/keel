package pub.ihub.dsl.integration

import groovy.util.logging.Slf4j
import org.reactivestreams.Publisher
import org.springframework.integration.core.GenericSelector
import org.springframework.integration.core.MessageSource
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageChannelSpec
import org.springframework.integration.dsl.MessageHandlerSpec
import org.springframework.integration.dsl.MessageProducerSpec
import org.springframework.integration.dsl.MessageSourceSpec
import org.springframework.integration.dsl.MessagingGatewaySpec
import org.springframework.integration.endpoint.MessageProducerSupport
import org.springframework.integration.gateway.MessagingGatewaySupport
import org.springframework.integration.handler.GenericHandler
import org.springframework.integration.router.AbstractMessageRouter
import org.springframework.integration.splitter.AbstractMessageSplitter
import org.springframework.integration.transformer.GenericTransformer
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler

import java.util.function.Supplier

import static org.springframework.integration.dsl.IntegrationFlows.from



/**
 * 消息流程构建方法
 * @author liheng
 */
@Slf4j
class MessageFlowMethods {

    //<editor-fold defaultState="collapsed" desc="消息流程开启节点">

    //<editor-fold defaultState="collapsed" desc="消息通道开启流程">

    static IntegrationFlowBuilder rightShift(String messageChannelName, endpoint) {
        from(messageChannelName) >> endpoint
    }

    static IntegrationFlowBuilder rightShift(MessageChannelSpec messageChannelSpec, endpoint) {
        from(messageChannelSpec) >> endpoint
    }

    static IntegrationFlowBuilder rightShift(MessageChannel messageChannel, endpoint) {
        from(messageChannel) >> endpoint
    }

    static IntegrationFlowBuilder rightShift(Publisher publisher, endpoint) {
        from(publisher) >> endpoint
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="消息源开启流程">

    static IntegrationFlowBuilder rightShift(MessageSourceSpec messageSourceSpec, endpoint) {
        from(messageSourceSpec) >> endpoint
    }

    static IntegrationFlowBuilder rightShift(Supplier messageSource, endpoint) {
        from(messageSource) >> endpoint
    }

    static IntegrationFlowBuilder rightShift(MessageSource messageSource, endpoint) {
        from(messageSource) >> endpoint
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="消息适配器开启流程">

    static IntegrationFlowBuilder rightShift(MessageProducerSpec messageProducerSpec, endpoint) {
        from(messageProducerSpec) >> endpoint
    }

    static IntegrationFlowBuilder rightShift(MessageProducerSupport messageProducer, endpoint) {
        from(messageProducer) >> endpoint
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="消息网关开启流程">

    static IntegrationFlowBuilder rightShift(MessagingGatewaySpec inboundGatewaySpec, endpoint) {
        from(inboundGatewaySpec) >> endpoint
    }

    static IntegrationFlowBuilder rightShift(MessagingGatewaySupport inboundGateway, endpoint) {
        from(inboundGateway) >> endpoint
    }

    static IntegrationFlowBuilder rightShift(Class serviceInterface, endpoint) {
        from(serviceInterface) >> endpoint
    }

    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="消息端点，注：“>>”符号只支持特有的一个参数，如果需要多个参数可以直接调用原生DSL方法">
    // TODO 消息端点处理，重载多种消息处理类型，默认抛异常，建议使用原生DSL语法

    //<editor-fold defaultState="collapsed" desc="消息处理器">

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, MessageHandler handler) {
        builder.handle handler
    }

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, MessageHandlerSpec handlerSpec) {
        builder.handle handlerSpec
    }

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, GenericHandler handler) {
        builder.handle handler
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="过滤器">

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, GenericSelector selector) {
        builder.filter selector
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="转换器">

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, GenericTransformer transformer) {
        builder.transform transformer
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="路由">

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, AbstractMessageRouter router) {
        builder.route router
    }

    // TODO 预留Map路由
    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, Map router) {
        builder.route router
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="拆分器">

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, AbstractMessageSplitter splitter) {
        builder.split splitter
    }

//    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder,
//                                             Consumer<SplitterEndpointSpec<DefaultMessageSplitter>> splitter) {
//        builder.split splitter
//    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="聚合器">

//    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, Consumer<AggregatorSpec> aggregator) {
//        builder.aggregate aggregator
//    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="桥">

//    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder,
//                                             Consumer<GenericEndpointSpec<BridgeHandler>> endpointConfigurer) {
//        builder.bridge endpointConfigurer
//    }

    //</editor-fold>

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, AEndpointSpec endpointSpec) {
        endpointSpec << builder
    }

    static IntegrationFlowBuilder rightShift(IntegrationFlowBuilder builder, endpoint) {
        throw new MessageFlowException('消息端点类型不支持，或使用原生DSL语法！')
    }

    //</editor-fold>

}
