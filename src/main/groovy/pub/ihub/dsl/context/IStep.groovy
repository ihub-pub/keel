package pub.ihub.dsl.context


/**
 * 工作流步骤
 * @author liheng
 */
interface IStep<T> {

    Tuple4<Integer, Integer, T, String> run(Context context)

}
