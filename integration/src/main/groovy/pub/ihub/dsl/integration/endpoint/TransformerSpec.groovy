package pub.ihub.dsl.integration.endpoint

import groovy.transform.CompileStatic
import org.springframework.integration.dsl.GenericEndpointSpec
import org.springframework.integration.dsl.MessageProcessorSpec
import org.springframework.integration.transformer.GenericTransformer
import org.springframework.integration.transformer.MessageTransformingHandler
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Consumer



/**
 * 转换器
 *
 * convert TODO
 * MessageStore claimCheckIn TODO
 * MessageStore claimCheckOut TODO
 * fluxTransform TODO
 *
 * @param <S> the source type - 'transform from'.
 * @param <T> the target type - 'transform to'.
 * @author liheng
 */
@CompileStatic
class TransformerSpec<S, T> extends AEndpointSpec<S, GenericTransformer<S, T>, GenericEndpointSpec<MessageTransformingHandler>> {

    String builderMethodName = 'transform'

    TransformerSpec(String expression,
                    Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer = null) {
        super(expression, endpointConfigurer)
    }

    TransformerSpec(Object service, String methodName,
                    Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer = null) {
        super(service, methodName, endpointConfigurer)
    }

    TransformerSpec(Class<S> payloadType = null, GenericTransformer<S, T> genericTransformer,
                    Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer = null) {
        super(payloadType, genericTransformer, endpointConfigurer)
    }

    TransformerSpec(MessageProcessorSpec processorSpec,
                    Consumer<GenericEndpointSpec<MessageTransformingHandler>> endpointConfigurer = null) {
        super(processorSpec, endpointConfigurer)
    }

}
