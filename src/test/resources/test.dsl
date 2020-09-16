import pub.ihub.dsl.step.Route
import pub.ihub.dsl.step.Demo



nameClassMappings = [
        首步  : new Demo('首步'),
        第一步 : new Demo('第一步'),
        第二步 : new Demo('第二步'),
        第三步 : new Demo('第三步'),
        第四步 : new Demo('第四步'),
        只有一步: new Demo('只有一步'),
        最后一步: new Demo('最后一步'),
        默认步骤: Demo,
        路由一 : new Route(1),
        路由二 : new Route(2)
]

nameFlowMappings = [
        片段一: { 第一步 },
        片段二: { 第一步 >> 第二步 >> 第三步 >> 第四步 },
        片段三: { 第一步 >> 第二步 },
        片段四: { 第三步 >> 第四步 },
        模板一: { T1 >> T2 }
]

flows = [
        flow_demo: { 首步 >> 第一步 >> 第二步 >> 第三步 >> 第四步 >> 最后一步 }
]
