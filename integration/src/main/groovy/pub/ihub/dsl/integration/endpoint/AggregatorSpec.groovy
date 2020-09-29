package pub.ihub.dsl.integration.endpoint

import org.springframework.integration.dsl.AggregatorSpec as AggregatorEndpointSpec
import org.springframework.integration.dsl.IntegrationFlowBuilder
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer



/**
 * 聚合器
 *
 * scatterGather TODO
 *
 * TODO 扩展构造器
 * @author liheng
 */
class AggregatorSpec extends AEndpointSpec<Object, Object, AggregatorEndpointSpec> {

    String builderMethodName = 'aggregate'

    AggregatorSpec(Consumer<AggregatorEndpointSpec> aggregator = null) {
        endpointConfigurer = aggregator
    }

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        endpointConfigurer ? builder.aggregate(endpointConfigurer) : builder.aggregate()
    }

}
