package pub.ihub.dsl.test

import pub.ihub.dsl.step.Route
import pub.ihub.dsl.step.Step



nameClassMappings = [
        首步  : new Step('首步'),
        第一步 : new Step('第一步'),
        第二步 : new Step('第二步'),
        第三步 : new Step('第三步'),
        第四步 : new Step('第四步'),
        只有一步: new Step('只有一步'),
        最后一步: new Step('最后一步'),
        默认步骤: Step,
        是   : new Route(true),
        否   : new Route(false)
]

nameFlowMappings = [
        片段一: { 第一步 >> 第二步 >> 第三步 >> 第四步 },
        片段二: { 第一步 >> 第二步 },
        片段三: { 第三步 >> 第四步 },
        片段四: { 只有一步 },
        模板一: { T1 >> T2 }
]

flows = [
        flow_demo: { 首步 >> 第一步 >> 第二步 >> 第三步 >> 第四步 >> 最后一步 }
]
