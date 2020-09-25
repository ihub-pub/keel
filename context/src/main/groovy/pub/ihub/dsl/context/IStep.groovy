package pub.ihub.dsl.context


/**
 * 工作流步骤
 * @author liheng
 */
interface IStep<T> {

    T run(Context context)

}
