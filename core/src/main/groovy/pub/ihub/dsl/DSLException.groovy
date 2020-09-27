package pub.ihub.dsl

/**
 * DSL异常
 * @author liheng
 */
class DSLException extends RuntimeException {

    DSLException(String message) {
        this(message, null)
    }

    DSLException(String message, Throwable throwable) {
        super(message, throwable)
    }

}
