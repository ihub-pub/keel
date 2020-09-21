package pub.ihub.dsl


import groovy.util.logging.Slf4j

import static java.lang.System.identityHashCode



/**
 * 基于GroovyShell DSL执行器
 *
 * 不支持子流程以及模板
 * @author liheng
 */
@Slf4j
class ShellActuator extends DSLActuator {

    private final GroovyShell shell
    /**
     * 脚本映射
     */
    private final Map<Integer, Script> flows = [:]

    ShellActuator(URL scriptLocation = null) {
        super(scriptLocation)
        shell = new GroovyShell(config.binding)
    }

    ShellActuator(Map<String, Object> nameClassMappings) {
        super(nameClassMappings, null)
        shell = new GroovyShell(config.binding)
    }

    protected doCall(Class groovyMethods, String flow, String... args) {
        synchronized (shell) {
            shell.evaluate "use $groovyMethods.canonicalName, { $flow }"
        }
    }

    static protected doCall(Class groovyMethods, Script flow, String... args) {
        synchronized (flow) {
            flow.setProperty 'args', args
            flow.run()
        }
    }

    def call(Class groovyMethods = DSLGroovyMethods, String flow) {
        shell.setVariable groovyMethods.canonicalName, groovyMethods
        execute groovyMethods, flow, null
    }

    def call(Class groovyMethods = DSLGroovyMethods, String flow, String... args) {
        def methodsName = groovyMethods.canonicalName
        def code = identityHashCode flow
        execute groovyMethods, flows[code] ?: shell.parse("use $methodsName, { $flow }").tap {
            binding = new Binding([(methodsName): groovyMethods] + binding.variables)
            flows.put code, it
        }, args
    }

}
