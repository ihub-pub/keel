package pub.ihub.dsl.integration.endpoint

import org.springframework.expression.Expression
import org.springframework.integration.dsl.IntegrationFlowBuilder
import org.springframework.integration.handler.LoggingHandler
import org.springframework.messaging.Message
import pub.ihub.dsl.integration.AEndpointSpec

import java.util.function.Function

import static org.springframework.integration.handler.LoggingHandler.Level.INFO
import static pub.ihub.dsl.integration.endpoint.LoggerSpec.ConstructorArgumentType.EXPRESSION
import static pub.ihub.dsl.integration.endpoint.LoggerSpec.ConstructorArgumentType.EXPRESSION_STR
import static pub.ihub.dsl.integration.endpoint.LoggerSpec.ConstructorArgumentType.FUNCTION



/**
 * 日志
 *
 * logAndReply TODO
 *
 * TODO 搞清楚category
 * @author liheng
 */
class LoggerSpec<P> extends AEndpointSpec {

    String builderMethodName = 'log'
    private LoggingHandler.Level level
    private String category
    private Expression logExpression
    private String logExpressionStr
    private Function<Message<P>, Object> function
    private ConstructorArgumentType argumentType

    LoggerSpec(LoggingHandler.Level level = INFO, String category = null, Expression logExpression = null) {
        this.level = level
        this.category = category
        this.logExpression = logExpression
        argumentType = EXPRESSION
    }

    LoggerSpec(LoggingHandler.Level level = INFO, String category, String logExpression) {
        this.level = level
        this.category = category
        logExpressionStr = logExpression
        argumentType = EXPRESSION_STR
    }

    LoggerSpec(LoggingHandler.Level level = INFO, String category, Function<Message<P>, Object> function) {
        this.level = level
        this.category = category
        this.function = function
        argumentType = FUNCTION
    }

    IntegrationFlowBuilder leftShift(IntegrationFlowBuilder builder) {
        if (EXPRESSION == argumentType) {
            builder.log level, category, logExpression
        } else if (EXPRESSION_STR == argumentType) {
            builder.log level, category, logExpressionStr
        } else if (FUNCTION == argumentType) {
            builder.log level, category, function
        } else {
            builder
        }
    }

    private enum ConstructorArgumentType {

        EXPRESSION, EXPRESSION_STR, FUNCTION

    }

}
