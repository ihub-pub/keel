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
                   Map<String, Closure> nameFlowMappings,
                   Map<String, Closure> flows = [:]) {
        this.nameClassMappings.putAll nameClassMappings
        this.nameFlowMappings.putAll nameFlowMappings
        this.flows.putAll flows
    }

    @SuppressWarnings('UnnecessaryGetter')
    MappingsConfig(URL scriptLocation, boolean mergeBase = true) {
        def config = mergeBase ? parse(getClass().classLoader.getResource('base_config.dsl'))
                .merge(parse(scriptLocation)) : parse(scriptLocation).flatten()
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
        scriptLocation?.with { ConfigSlurper().parse(it) } ?: new ConfigObject()
    }

    static void setNameBindingsMappings(Map<String, Object> bindings) {
        NAME_BINDINGS_MAPPINGS_THREAD_LOCAL.remove()
        NAME_BINDINGS_MAPPINGS_THREAD_LOCAL.set bindings
    }

    private static Map<String, Object> getNameBindingsMappings() {
        NAME_BINDINGS_MAPPINGS_THREAD_LOCAL.get() ?: [:]
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
        nameBindingsMappings[name]?.tap {
            log.trace '获取 <<<绑定属性<<< {} <- {}', name, it
        }
    }

}
