package pub.ihub.dsl.integration.endpoint

import org.springframework.integration.dsl.GenericEndpointSpec
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageHandlerSpec
import org.springframework.integration.dsl.MessageProcessorSpec
import org.springframework.integration.handler.GenericHandler
import org.springframework.integration.handler.ServiceActivatingHandler
import org.springframework.messaging.MessageHandler
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer



/**
 * Service Activator消息处理器
 *
 * @param <P> the expected {@code payload} type.
 * @author liheng
 */
class HandlerSpec<P> extends AEndpointSpec<P, GenericHandler<P>, GenericEndpointSpec<MessageHandler>> {

    String builderMethodName = 'handle'
    private MessageHandler handler
    private GenericHandler<P> genericHandler
    private MessageHandlerSpec messageHandlerSpec

    HandlerSpec(MessageHandler handler,
                Consumer<GenericEndpointSpec<? extends MessageHandler>> endpointConfigurer = null) {
        this.handler = handler
        this.endpointConfigurer = endpointConfigurer
    }

    HandlerSpec(GenericHandler<P> handler,
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        genericHandler = handler
        super.endpointConfigurer = endpointConfigurer
    }

    HandlerSpec(MessageHandlerSpec<? extends MessageHandlerSpec, ? extends MessageHandler> messageHandlerSpec,
                Consumer<GenericEndpointSpec<? extends MessageHandler>> endpointConfigurer = null) {
        this.messageHandlerSpec = messageHandlerSpec
        this.endpointConfigurer = endpointConfigurer
    }

    HandlerSpec(String beanName, String methodName = 'handle',
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        super(beanName, methodName, endpointConfigurer)
    }

    HandlerSpec(Object service, String methodName = null,
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        super(service, methodName, endpointConfigurer)
    }

    HandlerSpec(Class<P> payloadType, GenericHandler<P> handler,
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        super(payloadType, handler, endpointConfigurer)
    }

    HandlerSpec(MessageProcessorSpec processorSpec,
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        super(processorSpec, endpointConfigurer)
    }

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        (super << builder) ?: handler ? builder.handle(handler, endpointConfigurer) :
                genericHandler ? builder.handle(genericHandler, endpointConfigurer) :
                        messageHandlerSpec ? builder.handle(messageHandlerSpec, endpointConfigurer) : null
    }

}
