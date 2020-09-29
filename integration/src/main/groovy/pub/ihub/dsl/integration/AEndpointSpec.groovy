package pub.ihub.dsl.integration

import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageProcessorSpec

import java.util.function.Consumer

import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.BEAN_NAME
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.EXPRESSION
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.FUNCTION
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.OBJECT
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.PROCESSOR_SPEC
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.SERVICE



/**
 * @author liheng
 */
abstract class AEndpointSpec<P, F, T> {

    protected String expression
    protected Class<P> payloadType
    protected String beanName
    protected Object service
    protected Class serviceType
    protected String methodName
    protected F function
    protected MessageProcessorSpec processorSpec
    protected Consumer<T> endpointConfigurer
    private final ConstructorArgumentType argumentType

    protected AEndpointSpec() {
        argumentType = null
    }

    protected AEndpointSpec(String expression, Consumer<T> endpointConfigurer = null) {
        this.expression = expression
        this.endpointConfigurer = endpointConfigurer
        argumentType = EXPRESSION
    }

    protected AEndpointSpec(String beanName, String methodName, Consumer<T> endpointConfigurer = null) {
        this.beanName = beanName
        this.methodName = methodName
        this.endpointConfigurer = endpointConfigurer
        argumentType = BEAN_NAME
    }

    protected AEndpointSpec(Object service, String methodName = null, Consumer<T> endpointConfigurer = null) {
        this.service = service
        this.methodName = methodName
        this.endpointConfigurer = endpointConfigurer
        argumentType = OBJECT
    }

    // TODO 同service
    protected AEndpointSpec(Class serviceType, String methodName, Consumer<T> endpointConfigurer = null) {
        this.serviceType = serviceType
        this.methodName = methodName
        this.endpointConfigurer = methodName ? endpointConfigurer : null
        argumentType = SERVICE
    }

    protected AEndpointSpec(Class<P> payloadType, F function, Consumer<T> endpointConfigurer = null) {
        this.payloadType = payloadType
        this.function = function
        this.endpointConfigurer = endpointConfigurer
        argumentType = FUNCTION
    }

    protected AEndpointSpec(MessageProcessorSpec processorSpec, Consumer<T> endpointConfigurer = null) {
        this.processorSpec = processorSpec
        this.endpointConfigurer = endpointConfigurer
        argumentType = PROCESSOR_SPEC
    }

    protected IntegrationFlowBuilder buildWithExpression(IntegrationFlowBuilder builder) {
        builder."$builderMethodName" expression, endpointConfigurer
    }

    protected IntegrationFlowBuilder buildWithBeanName(IntegrationFlowBuilder builder) {
        builder."$builderMethodName" beanName, methodName, endpointConfigurer
    }

    protected IntegrationFlowBuilder buildWithObject(IntegrationFlowBuilder builder) {
        builder."$builderMethodName" service, methodName, endpointConfigurer
    }

    protected IntegrationFlowBuilder buildWithFunction(IntegrationFlowBuilder builder) {
        builder."$builderMethodName" payloadType, function, endpointConfigurer
    }

    protected IntegrationFlowBuilder buildWithProcessorSpec(IntegrationFlowBuilder builder) {
        builder."$builderMethodName" processorSpec, endpointConfigurer
    }

    abstract protected String getBuilderMethodName()

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        switch (argumentType) {
            case EXPRESSION:
                buildWithExpression builder
                break
            case BEAN_NAME:
                buildWithBeanName builder
                break
            case OBJECT:
                buildWithObject builder
                break
            case FUNCTION:
                buildWithFunction builder
                break
            case PROCESSOR_SPEC:
                buildWithProcessorSpec builder
                break
            default: null
        }
    }

    private enum ConstructorArgumentType {

        EXPRESSION, BEAN_NAME, OBJECT, SERVICE, FUNCTION, PROCESSOR_SPEC

    }

}
