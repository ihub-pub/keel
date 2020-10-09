package pub.ihub.dsl.integration.endpoint

import groovy.transform.CompileStatic
import org.springframework.expression.Expression
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.handler.LoggingHandler
import org.springframework.messaging.Message
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Function

import static org.springframework.integration.handler.LoggingHandler.Level.INFO
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.EXPRESSION
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.EXPRESSION_STR
import static pub.ihub.dsl.integration.AEndpointSpec.ConstructorArgumentType.FUNCTION



/**
 * 日志
 *
 * logAndReply TODO
 *
 * TODO 搞清楚category
 * @author liheng
 */
@CompileStatic
class LoggerSpec<P> extends AEndpointSpec {

    String builderMethodName = 'log'
    private LoggingHandler.Level level
    private String category
    private Expression logExpression
    private String logExpressionStr
    private Function<Message<P>, Object> function

    LoggerSpec(LoggingHandler.Level level = INFO, String category = null, Expression logExpression = null) {
        super(EXPRESSION)
        this.level = level
        this.category = category
        this.logExpression = logExpression
    }

    LoggerSpec(LoggingHandler.Level level = INFO, String category, String logExpression) {
        super(EXPRESSION_STR)
        this.level = level
        this.category = category
        logExpressionStr = logExpression
    }

    LoggerSpec(LoggingHandler.Level level = INFO, String category, Function<Message<P>, Object> function) {
        super(FUNCTION)
        this.level = level
        this.category = category
        this.function = function
    }

    private Map<ConstructorArgumentType, Closure<IntegrationFlowBuilder>> flowBuilderHandlerMapping = [
            (EXPRESSION)    : { IntegrationFlowBuilder builder ->
                builder.log level, category, logExpression
            },
            (EXPRESSION_STR): { IntegrationFlowBuilder builder ->
                builder.log level, category, logExpressionStr
            },
            (FUNCTION)      : { IntegrationFlowBuilder builder ->
                builder.log level, category, function
            }
    ]

    protected Closure<IntegrationFlowBuilder> getIntegrationFlowBuilderHandler() {
        flowBuilderHandlerMapping[argumentType]
    }

}
