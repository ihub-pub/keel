package pub.ihub.dsl

import groovy.util.logging.Slf4j



/**
 * DSL映射配置
 * @author liheng
 */
@Slf4j
@Newify(ConfigSlurper)
class MappingsConfig {

    /**
     * 步骤/属性/方法映射
     */
    private final Map<String, Object> nameClassMappings = [:]
    /**
     * 子流程映射
     */
    private final Map<String, Closure> nameFlowMappings = [:]
    /**
     * 流程映射
     */
    private final Map<String, Closure> flows = [:]
    /**
     * 运行时步骤/属性/子流程绑定映射（主要用于流程模板变量映射）
     */
    private static final ThreadLocal<Map<String, Object>> NAME_BINDINGS_MAPPINGS_THREAD_LOCAL = new ThreadLocal<>()

    MappingsConfig(Map<String, Object> nameClassMappings,
                   Map<String, Closure> nameFlowMappings = [:],
                   Map<String, Closure> flows = [:]) {
        this.nameClassMappings.putAll nameClassMappings
        this.nameFlowMappings.putAll nameFlowMappings
        this.flows.putAll flows
    }

    /**
     * 配置脚本构造器
     * base_config为全局公共配置，文件名base_config.dsl，路径在resources根目录下
     * @param scriptLocation 配置脚本URL
     */
    @SuppressWarnings('UnnecessaryGetter')
    MappingsConfig(URL scriptLocation) {
        def config = parse(getClass().classLoader.getResource('base_config.dsl')).merge parse(scriptLocation)
        nameClassMappings.putAll config.nameClassMappings.collectEntries { key, value ->
            [(key): value instanceof Class ? value.getDeclaredConstructor().newInstance() : value]
        } as Map<String, Object>
        nameFlowMappings.putAll config.nameFlowMappings as Map<String, Closure>
        flows.putAll config.flows as Map<String, Closure>
    }

    /**
     * 解析配置文件
     * @param scriptLocation 配置脚本URL
     * @return 配置
     */
    private static ConfigObject parse(URL scriptLocation) {
        scriptLocation?.with {
            log.trace '解析配置 <<< {}', path
            ConfigSlurper().parse it
        } ?: new ConfigObject()
    }

    static void setNameBindingsMappings(Map<String, Object> bindings) {
        NAME_BINDINGS_MAPPINGS_THREAD_LOCAL.remove()
        NAME_BINDINGS_MAPPINGS_THREAD_LOCAL.set bindings
    }

    void putNameClazzMapping(String name, value) {
        nameClassMappings.put name, value
    }

    Binding getBinding() {
        new Binding(nameClassMappings)
    }

    def getClass(String name) {
        nameClassMappings[name]?.tap {
            log.trace '获取 <<<步骤/属性/方法<<< {} <- {}', name, it
        }
    }

    Closure getSubFlow(String name) {
        nameFlowMappings[name]?.tap {
            log.trace '获取 <<<子流程<<< {}', name
        }
    }

    Closure getFlow(String name) {
        flows[name]?.tap {
            log.trace '获取 <<<流程<<< {}', name
        }
    }

    static getBinding(String name) {
        NAME_BINDINGS_MAPPINGS_THREAD_LOCAL.get()?.get(name)?.tap {
            log.trace '获取 <<<绑定属性<<< {} <- {}', name, it
        }
    }

}
