package pub.ihub.dsl.integration

import org.springframework.expression.Expression
import org.springframework.integration.core.GenericSelector
import org.springframework.integration.dsl.AggregatorSpec as AggregatorEndpointSpec
import org.springframework.integration.dsl.FilterEndpointSpec
import org.springframework.integration.dsl.GenericEndpointSpec
import org.springframework.integration.dsl.MessageHandlerSpec
import org.springframework.integration.dsl.MessageProcessorSpec
import org.springframework.integration.dsl.RouterSpec as RouterEndpointSpec
import org.springframework.integration.dsl.SplitterEndpointSpec
import org.springframework.integration.handler.BridgeHandler
import org.springframework.integration.handler.GenericHandler
import org.springframework.integration.handler.LoggingHandler
import org.springframework.integration.handler.ServiceActivatingHandler
import org.springframework.integration.router.AbstractMessageRouter
import org.springframework.integration.router.ExpressionEvaluatingRouter
import org.springframework.integration.router.MethodInvokingRouter
import org.springframework.integration.splitter.AbstractMessageSplitter
import org.springframework.integration.splitter.DefaultMessageSplitter
import org.springframework.integration.splitter.ExpressionEvaluatingSplitter
import org.springframework.integration.splitter.MethodInvokingSplitter
import org.springframework.integration.transformer.GenericTransformer
import org.springframework.integration.transformer.MessageTransformingHandler
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import pub.ihub.dsl.integration.endpoint.AggregatorSpec
import pub.ihub.dsl.integration.endpoint.BridgeSpec
import pub.ihub.dsl.integration.endpoint.FilterSpec
import pub.ihub.dsl.integration.endpoint.HandlerSpec
import pub.ihub.dsl.integration.endpoint.LoggerSpec
import pub.ihub.dsl.integration.endpoint.RouterSpec
import pub.ihub.dsl.integration.endpoint.SplitterSpec
import pub.ihub.dsl.integration.endpoint.TransformerSpec

import java.util.function.Consumer
import java.util.function.Function

import static org.springframework.integration.handler.LoggingHandler.Level.INFO



/**
 * @author liheng
 */
@SuppressWarnings('MethodCount')
final class MessageEndpoints {

    //<editor-fold defaultState="collapsed" desc="消息处理器">

    static <H extends MessageHandler> HandlerSpec handle(H handler,
                                                         Consumer<GenericEndpointSpec<H>> endpointConfigurer = null) {
        new HandlerSpec(handler, endpointConfigurer)
    }

    static <P> HandlerSpec<P> handle(
            GenericHandler<P> handler,
            Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        new HandlerSpec<P>(handler, endpointConfigurer)
    }

    static <H extends MessageHandler> HandlerSpec handle(
            MessageHandlerSpec<? extends MessageHandlerSpec, H> messageHandlerSpec,
            Consumer<GenericEndpointSpec<? extends MessageHandler>> endpointConfigurer = null) {
        new HandlerSpec(messageHandlerSpec, endpointConfigurer)
    }

    static HandlerSpec handle(String beanName, String methodName = 'handle',
                              Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        new HandlerSpec(beanName, methodName, endpointConfigurer)
    }

    static HandlerSpec handle(Object service, String methodName = null,
                              Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        new HandlerSpec(service, methodName, endpointConfigurer)
    }

    static <P> HandlerSpec<P> handle(
            Class<P> payloadType, GenericHandler<P> handler,
            Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        new HandlerSpec<P>(payloadType, handler, endpointConfigurer)
    }

    static HandlerSpec handle(MessageProcessorSpec processorSpec,
                              Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        new HandlerSpec(processorSpec, endpointConfigurer)
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="过滤器">

    static <P> FilterSpec<P> filter(GenericSelector<P> genericSelector,
                                    Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        new FilterSpec<P>(genericSelector, endpointConfigurer)
    }

    static FilterSpec filter(String expression, Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        new FilterSpec(expression, endpointConfigurer)
    }

    static FilterSpec filter(Object service, String methodName = null,
                             Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        new FilterSpec(service, methodName, endpointConfigurer)
    }

    static <P> FilterSpec<P> filter(Class<P> payloadType, GenericSelector<P> selector,
                                    Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        new FilterSpec<P>(payloadType, selector, endpointConfigurer)
    }

    static FilterSpec filter(MessageProcessorSpec processorSpec,
                             Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        new FilterSpec(processorSpec, endpointConfigurer)
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="转换器">

    static TransformerSpec transform(
            String expression, Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer = null) {
        new TransformerSpec(expression, endpointConfigurer)
    }

    static TransformerSpec transform(
            Object service, String methodName = null,
            Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer = null) {
        new TransformerSpec(service, methodName, endpointConfigurer)
    }

    static <S, T> TransformerSpec<S, T> transform(
            Class<S> payloadType = null, GenericTransformer<S, T> genericTransformer,
            Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer = null) {
        new TransformerSpec<S, T>(payloadType, genericTransformer, endpointConfigurer)
    }

    static TransformerSpec transform(
            MessageProcessorSpec processorSpec,
            Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer = null) {
        new TransformerSpec(processorSpec, endpointConfigurer)
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="路由">

    static <R extends AbstractMessageRouter> RouterSpec route(
            R router, Consumer<GenericEndpointSpec<R>> endpointConfigurer = null) {
        new RouterSpec(router, endpointConfigurer)
    }

    static <P, K> RouterSpec<P, K> route(
            String expression, Consumer<RouterEndpointSpec<K, ExpressionEvaluatingRouter>> routerConfigurer = null) {
        new RouterSpec<P, K>(expression, routerConfigurer)
    }

    static RouterSpec route(String beanName, String methodName,
                            Consumer<RouterEndpointSpec<Object, MethodInvokingRouter>> endpointConfigurer = null) {
        new RouterSpec(beanName, methodName, endpointConfigurer)
    }

    static RouterSpec route(Object service, String methodName = null,
                            Consumer<RouterEndpointSpec<Object, MethodInvokingRouter>> endpointConfigurer = null) {
        new RouterSpec(service, methodName, endpointConfigurer)
    }

    static <P, K> RouterSpec<P, K> route(
            Class<P> payloadType = null, Function<P, K> router,
            Consumer<RouterEndpointSpec<K, MethodInvokingRouter>> endpointConfigurer = null) {
        new RouterSpec<P, K>(payloadType, router, endpointConfigurer)
    }

    static RouterSpec route(MessageProcessorSpec processorSpec,
                            Consumer<RouterEndpointSpec<Object, MethodInvokingRouter>> endpointConfigurer = null) {
        new RouterSpec(processorSpec, endpointConfigurer)
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="拆分器">

    static SplitterSpec split(Consumer<SplitterEndpointSpec<DefaultMessageSplitter>> endpointConfigurer = null) {
        new SplitterSpec(endpointConfigurer)
    }

    static <S extends AbstractMessageSplitter> SplitterSpec split(
            S splitter, Consumer<SplitterEndpointSpec<S>> endpointConfigurer = null) {
        new SplitterSpec(splitter, endpointConfigurer)
    }

    static SplitterSpec split(String expression,
                              Consumer<SplitterEndpointSpec<ExpressionEvaluatingSplitter>> endpointConfigurer = null) {
        new SplitterSpec(expression, endpointConfigurer)
    }

    static SplitterSpec split(String beanName, String methodName,
                              Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer = null) {
        new SplitterSpec(beanName, methodName, endpointConfigurer)
    }

    static SplitterSpec split(Object service, String methodName = null,
                              Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer = null) {
        new SplitterSpec(service, methodName, endpointConfigurer)
    }

    static <P> SplitterSpec<P> split(Class<P> payloadType, Function<P, ?> splitter,
                                     Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer = null) {
        new SplitterSpec<P>(payloadType, splitter, endpointConfigurer)
    }

    static <P> SplitterSpec<P> split(Function<P, ?> splitter,
                                     Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer) {
        new SplitterSpec<P>(splitter, endpointConfigurer)
    }

    static SplitterSpec split(MessageProcessorSpec processorSpec,
                              Consumer<SplitterEndpointSpec<MethodInvokingSplitter>> endpointConfigurer = null) {
        new SplitterSpec(processorSpec, endpointConfigurer)
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="聚合器">

    static AggregatorSpec aggregate(Consumer<AggregatorEndpointSpec> aggregator = null) {
        new AggregatorSpec(aggregator)
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="桥">

    static BridgeSpec bridge(Consumer<GenericEndpointSpec<BridgeHandler>> endpointConfigurer = null) {
        new BridgeSpec(endpointConfigurer)
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="日志">

    static LoggerSpec log(LoggingHandler.Level level = INFO, String category = null, Expression logExpression = null) {
        new LoggerSpec(level, category, logExpression)
    }

    static LoggerSpec log(LoggingHandler.Level level = INFO, String category, String logExpression) {
        new LoggerSpec(level, category, logExpression)
    }

    static <P> LoggerSpec<P> log(LoggingHandler.Level level = INFO, String category,
                                 Function<Message<P>, Object> function) {
        new LoggerSpec<P>(level, category, function)
    }

    static <P> LoggerSpec<P> log(LoggingHandler.Level level, Function<Message<P>, Object> function) {
        log(level, null, function)
    }

    //</editor-fold>

}
