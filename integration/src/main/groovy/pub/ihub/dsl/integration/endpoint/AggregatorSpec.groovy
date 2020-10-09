package pub.ihub.dsl.integration.endpoint

import groovy.transform.CompileStatic
import org.springframework.integration.dsl.AggregatorSpec as AggregatorEndpointSpec
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
@CompileStatic
class AggregatorSpec extends AEndpointSpec<Object, Object, AggregatorEndpointSpec> {

    String builderMethodName = 'aggregate'

    AggregatorSpec() {
    }

    AggregatorSpec(Consumer<AggregatorEndpointSpec> aggregator) {
        super(aggregator)
    }

}
