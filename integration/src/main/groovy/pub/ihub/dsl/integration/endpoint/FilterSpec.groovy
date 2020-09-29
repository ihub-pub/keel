package pub.ihub.dsl.integration.endpoint

import org.springframework.integration.core.GenericSelector
import org.springframework.integration.dsl.FilterEndpointSpec
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageProcessorSpec
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer



/**
 * 过滤器
 *
 * headerFilter TODO
 *
 * @param <S> the source type to accept.
 * @author liheng
 */
class FilterSpec<P> extends AEndpointSpec<P, GenericSelector<P>, FilterEndpointSpec> {

    String builderMethodName = 'filter'
    private GenericSelector<P> genericSelector

    FilterSpec(GenericSelector<P> genericSelector, Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        this.genericSelector = genericSelector
        this.endpointConfigurer = endpointConfigurer
    }

    FilterSpec(String expression, Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        super(expression, endpointConfigurer)
    }

    FilterSpec(Object service, String methodName = null, Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        super(service, methodName, endpointConfigurer)
    }

    FilterSpec(Class<P> payloadType, GenericSelector<P> selector,
               Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        super(payloadType, selector, endpointConfigurer)
    }

    FilterSpec(MessageProcessorSpec processorSpec, Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        super(processorSpec, endpointConfigurer)
    }

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        (super << builder) ?: genericSelector ? builder.filter(genericSelector, endpointConfigurer) : null
    }

}
