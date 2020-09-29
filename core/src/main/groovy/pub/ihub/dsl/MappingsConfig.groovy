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
    MappingsConfig(URL scriptLocation) {
        def config = parse(getClass().classLoader.getResource('base_config.dsl'))
                .with { scriptLocation ? merge(parse(scriptLocation)) : it }
        nameClassMappings.putAll config.nameClassMappings as Map<String, Object>
        nameFlowMappings.putAll config.nameFlowMappings as Map<String, Closure>
        flows.putAll config.flows as Map<String, Closure>
    }

    /**
     * 解析配置文件
     * @param scriptLocation 配置脚本URL
     * @return 配置
     */
    private static ConfigObject parse(URL scriptLocation) {
        scriptLocation.with {
            log.trace '解析配置 <<< {}', path
            ConfigSlurper().parse it
        }
    }

    void putNameClazzMapping(String name, value) {
        nameClassMappings.put name, value
    }

    Binding getBinding() {
        new Binding(nameClassMappings)
    }

    def getMethod(String name) {
        nameClassMappings[name].tap {
            if (it) {
                log.trace '获取 <<<方法<<< {} <- {}', name, it
            } else {
                log.error '方法没有定义：{}', name
                throw new MissingMethodException(name, this.class)
            }
        }
    }

    Closure getFlow(String name) {
        flows[name].tap {
            if (it) {
                log.trace '获取 <<<流程<<< {}', name
            } else {
                log.error '流程没有定义：{}', name
                throw new MissingPropertyException(name, this.class)
            }
        }
    }

    Set<String> getFlowNames() {
        flows.keySet()
    }

    def getMissProperty(String name, delegate) {
        nameClassMappings[name]?.tap {
            log.trace '获取 <<<步骤/属性<<< {} <- {}', name, it
        } ?: nameFlowMappings[name].with {
            if (it) {
                log.trace '获取 <<<子流程<<< {}', name
                it.rehydrate delegate, owner, thisObject
            } else {
                log.error '步骤/属性没有定义：{}', name
                throw new MissingPropertyException(name, this.class)
            }
        }
    }

}
