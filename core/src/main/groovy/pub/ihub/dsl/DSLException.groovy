package pub.ihub.dsl

import groovy.transform.CompileStatic



/**
 * DSL异常
 * @author liheng
 */
@CompileStatic
class DSLException extends RuntimeException {

    DSLException(String message) {
        this(message, null)
    }

    DSLException(String message, Throwable throwable) {
        super(message, throwable)
    }

}
