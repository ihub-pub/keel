package pub.ihub.dsl.context

/**
 * 工作流相关的静态常量
 * @author liheng
 */
class Constants {

    // TODO 流程控制
    //<editor-fold defaultState="collapsed" desc="route flag">
    static final ROUTE_FLAG_CONTINUE        = 0
    static final ROUTE_FLAG_DONE            = ROUTE_FLAG_CONTINUE + 1
    //</editor-fold>

    //<editor-fold defaultState="collapsed" desc="status code">
    static final STATUS_CODE_OK             = 0
    static final _STATUS_CODE_FATAL_ERROR   = STATUS_CODE_OK - 1
    //</editor-fold>

}
