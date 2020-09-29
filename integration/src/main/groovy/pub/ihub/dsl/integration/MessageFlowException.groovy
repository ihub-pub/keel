package pub.ihub.dsl.integration

import pub.ihub.dsl.DSLException



/**
 * 消息流程构建异常
 * @author liheng
 */
class MessageFlowException extends DSLException {

    MessageFlowException(String message) {
        this(message, null)
    }

    MessageFlowException(String message, Throwable throwable) {
        super(message, throwable)
    }

}
