package pub.ihub.dsl.integration

import groovy.util.logging.Slf4j
import org.springframework.integration.core.MessageSource
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.dsl.MessageSourceSpec
import org.springframework.integration.endpoint.MessageProducerSupport
import org.springframework.integration.gateway.MessagingGatewaySupport
import pub.ihub.dsl.DSLActuator

import java.util.function.Consumer
import java.util.function.Supplier

import static org.springframework.integration.dsl.IntegrationFlows.from as flowsFrom
import static pub.ihub.dsl.integration.config.IntegrationConfig.integrationFlowContext



/**
 * 消息流程构建器
 * @author liheng
 */
@Slf4j
class MessageFlowBuilder extends DSLActuator {

    MessageFlowBuilder(URL scriptLocation = null) {
        super(scriptLocation)
    }

    MessageFlowBuilder(Map<String, Object> nameClassMappings, Map<String, Closure> nameFlowMappings = [:],
                       Map<String, Closure<IntegrationFlowBuilder>> flows) {
        super(nameClassMappings, nameFlowMappings, flows)
    }

    //<editor-fold defaultState="collapsed" desc="消息通道开启流程">

    static IntegrationFlowBuilder from(String messageChannelName, boolean fixedSubscriber) {
        flowsFrom messageChannelName, fixedSubscriber
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="消息源开启流程">

    static IntegrationFlowBuilder from(MessageSourceSpec messageSourceSpec, Consumer endpointConfigurer) {
        flowsFrom messageSourceSpec, endpointConfigurer
    }

    static IntegrationFlowBuilder from(Supplier messageSource, Consumer endpointConfigurer) {
        flowsFrom messageSource, endpointConfigurer
    }

    static IntegrationFlowBuilder from(MessageSource messageSource, Consumer endpointConfigurer,
                                       IntegrationFlowBuilder integrationFlowBuilderArg = null) {
        flowsFrom messageSource, endpointConfigurer, integrationFlowBuilderArg
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="消息适配器开启流程">

    static IntegrationFlowBuilder from(MessageProducerSupport messageProducer,
                                       IntegrationFlowBuilder integrationFlowBuilderArg) {
        flowsFrom messageProducer, integrationFlowBuilderArg
    }

    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="消息网关开启流程">

    static IntegrationFlowBuilder from(MessagingGatewaySupport inboundGateway,
                                       IntegrationFlowBuilder integrationFlowBuilderArg) {
        flowsFrom inboundGateway, integrationFlowBuilderArg
    }

    static IntegrationFlowBuilder from(Class serviceInterface, Consumer endpointConfigurer) {
        flowsFrom serviceInterface, endpointConfigurer
    }

    //</editor-fold>

    IntegrationFlow build(Closure<IntegrationFlowBuilder> closure) {
        execute(MessageFlowMethods, closure).get()
    }

    def build() {
        config.flowNames.each {
            log.info '开始初始化消息流程 >>>>>> {}', it
            integrationFlowContext.registration(build(config.getFlow(it)))
                    .tap { autoStartup false }.register()
        }
    }

}
