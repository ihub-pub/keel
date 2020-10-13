package pub.ihub.dsl.integration.endpoint

import groovy.transform.CompileStatic
import org.springframework.integration.core.GenericSelector
import org.springframework.integration.dsl.FilterEndpointSpec
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
@CompileStatic
class FilterSpec<P> extends AEndpointSpec<P, GenericSelector<P>, FilterEndpointSpec> {

    String builderMethodName = 'filter'

    FilterSpec(String expression, Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        super(expression, endpointConfigurer)
    }

    FilterSpec(Object service, String methodName = null, Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        super(service, methodName, endpointConfigurer)
    }

    FilterSpec(Class<P> payloadType = null, GenericSelector<P> selector,
               Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        super(payloadType, selector, endpointConfigurer)
    }

    FilterSpec(MessageProcessorSpec processorSpec, Consumer<FilterEndpointSpec> endpointConfigurer = null) {
        super(processorSpec, endpointConfigurer)
    }

}
