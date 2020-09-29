package pub.ihub.dsl.integration.endpoint

import org.springframework.integration.dsl.GenericEndpointSpec
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageProcessorSpec
import org.springframework.integration.dsl.RouterSpec as RouterEndpointSpec
import org.springframework.integration.router.AbstractMessageRouter
import org.springframework.integration.router.ExpressionEvaluatingRouter
import org.springframework.integration.router.MethodInvokingRouter
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer
import java.util.function.Function



/**
 * 路由
 *
 * routeToRecipients TODO
 * routeByException TODO
 *
 * @param <P> the expected {@code payload} type.
 * @param <K> the key type.
 * @author liheng
 */
class RouterSpec<P, K> extends AEndpointSpec<P, Function<P, K>, RouterEndpointSpec> {

    String builderMethodName = 'route'
    private AbstractMessageRouter router
    private Consumer<GenericEndpointSpec<? extends AbstractMessageRouter>> endpointConfigurer
    private Consumer<RouterEndpointSpec<K, ExpressionEvaluatingRouter>> routerConfigurer

    RouterSpec(AbstractMessageRouter router,
               Consumer<GenericEndpointSpec<? extends AbstractMessageRouter>> endpointConfigurer = null) {
        this.router = router
        this.endpointConfigurer = endpointConfigurer
    }

    RouterSpec(String expression, Consumer<RouterEndpointSpec<K, ExpressionEvaluatingRouter>> routerConfigurer = null) {
        this.expression = expression
        this.routerConfigurer = routerConfigurer
    }

    RouterSpec(String beanName, String methodName,
               Consumer<RouterEndpointSpec<Object, MethodInvokingRouter>> endpointConfigurer = null) {
        super(beanName, methodName, endpointConfigurer)
    }

    RouterSpec(Object service, String methodName = null,
               Consumer<RouterEndpointSpec<Object, MethodInvokingRouter>> endpointConfigurer = null) {
        super(service, methodName, endpointConfigurer)
    }

    RouterSpec(Class<P> payloadType = null, Function<P, K> router,
               Consumer<RouterEndpointSpec<K, MethodInvokingRouter>> endpointConfigurer = null) {
        super(payloadType, router, endpointConfigurer)
    }

    RouterSpec(MessageProcessorSpec processorSpec,
               Consumer<RouterEndpointSpec<Object, MethodInvokingRouter>> endpointConfigurer = null) {
        super(processorSpec, endpointConfigurer)
    }

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        (super << builder) ?: router ? builder.route(router, endpointConfigurer) :
                expression ? builder.route(expression, routerConfigurer) : null
    }

}
