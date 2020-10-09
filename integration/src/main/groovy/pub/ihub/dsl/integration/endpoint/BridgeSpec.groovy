package pub.ihub.dsl.integration.endpoint

import groovy.transform.CompileStatic
import org.springframework.integration.dsl.GenericEndpointSpec
import org.springframework.integration.handler.BridgeHandler
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer



/**
 * 桥
 *
 * @author liheng
 */
@CompileStatic
class BridgeSpec extends AEndpointSpec<Object, Object, GenericEndpointSpec<BridgeHandler>> {

    String builderMethodName = 'bridge'

    BridgeSpec() {
    }

    BridgeSpec(Consumer<GenericEndpointSpec<BridgeHandler>> endpointConfigurer) {
        super(endpointConfigurer)
    }

}
