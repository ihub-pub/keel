package pub.ihub.dsl.integration

import groovy.transform.CompileStatic
import pub.ihub.dsl.DSLException



/**
 * 消息流程构建异常
 * @author liheng
 */
@CompileStatic
class MessageFlowException extends DSLException {

    MessageFlowException(String message) {
        this(message, null)
    }

    MessageFlowException(String message, Throwable throwable) {
        super(message, throwable)
    }

}
