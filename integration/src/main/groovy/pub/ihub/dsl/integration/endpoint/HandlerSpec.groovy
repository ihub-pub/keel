package pub.ihub.dsl.integration.endpoint

import groovy.transform.CompileStatic
import org.springframework.integration.dsl.GenericEndpointSpec
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageHandlerSpec
import org.springframework.integration.dsl.MessageProcessorSpec
import org.springframework.integration.handler.GenericHandler
import org.springframework.integration.handler.ServiceActivatingHandler
import org.springframework.messaging.MessageHandler
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer

import static groovy.transform.TypeCheckingMode.SKIP
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.HANDLER
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.HANDLER_SPEC



/**
 * Service Activator消息处理器
 *
 * @param <P> the expected {@code payload} type.
 * @author liheng
 */
@CompileStatic
class HandlerSpec<P> extends AEndpointSpec<P, GenericHandler<P>, GenericEndpointSpec<MessageHandler>> {

    String builderMethodName = 'handle'
    private MessageHandler handler
    private MessageHandlerSpec messageHandlerSpec

    HandlerSpec(MessageHandler handler,
                Consumer<GenericEndpointSpec<? extends MessageHandler>> endpointConfigurer = null) {
        super(HANDLER)
        this.handler = handler
        this.endpointConfigurer = endpointConfigurer
    }

    HandlerSpec(MessageHandlerSpec<? extends MessageHandlerSpec, ? extends MessageHandler> messageHandlerSpec,
                Consumer<GenericEndpointSpec<? extends MessageHandler>> endpointConfigurer = null) {
        super(HANDLER_SPEC)
        this.messageHandlerSpec = messageHandlerSpec
        this.endpointConfigurer = endpointConfigurer
    }

    HandlerSpec(String beanName, String methodName = 'handle',
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        super(beanName, methodName, endpointConfigurer)
    }

    HandlerSpec(Object service, String methodName,
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        super(service, methodName, endpointConfigurer)
    }

    HandlerSpec(Class<P> payloadType = null, GenericHandler<P> handler,
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        super(payloadType, handler, endpointConfigurer)
    }

    HandlerSpec(MessageProcessorSpec processorSpec,
                Consumer<GenericEndpointSpec<ServiceActivatingHandler>> endpointConfigurer = null) {
        super(processorSpec, endpointConfigurer)
    }

    @CompileStatic(SKIP)
    protected Map<ConstructorArgumentType, Closure<IntegrationFlowBuilder>> getFlowBuilderHandlerMapping() {
        super.flowBuilderHandlerMapping + [
                (HANDLER)        : { IntegrationFlowBuilder builder ->
                    builder.handle handler, endpointConfigurer
                },
                (HANDLER_SPEC)   : { IntegrationFlowBuilder builder ->
                    builder.handle messageHandlerSpec, endpointConfigurer
                }
        ]
    }

}
