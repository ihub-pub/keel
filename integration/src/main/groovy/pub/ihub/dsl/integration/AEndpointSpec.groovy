package pub.ihub.dsl.integration

import groovy.transform.CompileStatic
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageProcessorSpec

import java.util.function.Consumer

import static groovy.transform.TypeCheckingMode.SKIP
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.BEAN_NAME
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.CONFIGURER
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.EXPRESSION
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.FUNCTION
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.NO_PARAMETERS
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.OBJECT
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.PROCESSOR_SPEC



/**
 * @author liheng
 */
@CompileStatic
abstract class AEndpointSpec<P, F, T> {

    protected String expression
    protected Class<P> payloadType
    protected String beanName
    protected Object service
    protected String methodName
    protected F function
    protected MessageProcessorSpec processorSpec
    protected Consumer<T> endpointConfigurer
    protected ConstructorArgumentType argumentType

    protected AEndpointSpec(ConstructorArgumentType argumentType) {
        this.argumentType = argumentType
    }

    protected AEndpointSpec() {
        this(NO_PARAMETERS)
    }

    protected AEndpointSpec(Consumer<T> endpointConfigurer) {
        this(CONFIGURER)
        this.endpointConfigurer = endpointConfigurer
    }

    protected AEndpointSpec(String expression, Consumer<T> endpointConfigurer) {
        this(EXPRESSION)
        this.expression = expression
        this.endpointConfigurer = endpointConfigurer
    }

    protected AEndpointSpec(String beanName, String methodName, Consumer<T> endpointConfigurer) {
        this(BEAN_NAME)
        this.beanName = beanName
        this.methodName = methodName
        this.endpointConfigurer = endpointConfigurer
    }

    // TODO 确认 methodName = null
    protected AEndpointSpec(Object service, String methodName, Consumer<T> endpointConfigurer) {
        this(OBJECT)
        this.service = service
        this.methodName = methodName
        this.endpointConfigurer = endpointConfigurer
    }

    protected AEndpointSpec(Class<P> payloadType, F function, Consumer<T> endpointConfigurer) {
        this(FUNCTION)
        this.payloadType = payloadType
        this.function = function
        this.endpointConfigurer = endpointConfigurer
    }

    protected AEndpointSpec(MessageProcessorSpec processorSpec, Consumer<T> endpointConfigurer) {
        this(PROCESSOR_SPEC)
        this.processorSpec = processorSpec
        this.endpointConfigurer = endpointConfigurer
    }

    abstract protected String getBuilderMethodName()

    @CompileStatic(SKIP)
    protected Map<ConstructorArgumentType, Closure<IntegrationFlowBuilder>> getFlowBuilderHandlerMapping() {
        [
                (NO_PARAMETERS) : { IntegrationFlowBuilder builder ->
                    builder."$builderMethodName"()
                },
                (CONFIGURER)    : { IntegrationFlowBuilder builder ->
                    builder."$builderMethodName" endpointConfigurer
                },
                (EXPRESSION)    : { IntegrationFlowBuilder builder ->
                    builder."$builderMethodName" expression, endpointConfigurer
                },
                (BEAN_NAME)     : { IntegrationFlowBuilder builder ->
                    builder."$builderMethodName" beanName, methodName, endpointConfigurer
                },
                (OBJECT)        : { IntegrationFlowBuilder builder ->
                    builder."$builderMethodName" service, methodName, endpointConfigurer
                },
                (FUNCTION)      : { IntegrationFlowBuilder builder ->
                    payloadType ? builder."$builderMethodName"(payloadType, function, endpointConfigurer) :
                            builder."$builderMethodName"(function, endpointConfigurer)
                },
                (PROCESSOR_SPEC): { IntegrationFlowBuilder builder ->
                    builder."$builderMethodName" processorSpec, endpointConfigurer
                }
        ]
    }

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        flowBuilderHandlerMapping[argumentType] builder
    }

    protected enum ConstructorArgumentType {

        //<editor-fold defaultState="collapsed" desc="Common">

        NO_PARAMETERS,
        CONFIGURER,
        EXPRESSION,
        BEAN_NAME,
        OBJECT,
        FUNCTION,
        PROCESSOR_SPEC,

        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Handler">

        HANDLER,
        HANDLER_SPEC,

        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Router">

        ROUTER,

        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Splitter">

        SPLITTER,

        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Logger">

        EXPRESSION_STR

        //</editor-fold>

    }

}
