package pub.ihub.dsl.integration.endpoint

import org.springframework.integration.dsl.GenericEndpointSpec
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.handler.BridgeHandler
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer



/**
 * 桥
 *
 * @author liheng
 */
class BridgeSpec extends AEndpointSpec<Object, Object, GenericEndpointSpec<BridgeHandler>> {

    String builderMethodName = 'bridge'

    BridgeSpec(Consumer<GenericEndpointSpec<BridgeHandler>> endpointConfigurer = null) {
        this.endpointConfigurer = endpointConfigurer
    }

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        endpointConfigurer ? builder.bridge(endpointConfigurer) : builder.bridge()
    }

}
