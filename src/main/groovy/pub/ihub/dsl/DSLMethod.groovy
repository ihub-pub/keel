package pub.ihub.dsl

/**
 * 自定义方法接口
 * @author liheng
 */
interface DSLMethod<T> {

    T call(... args)

}
