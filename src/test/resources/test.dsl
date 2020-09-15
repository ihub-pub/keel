import pub.ihub.dsl.RouteDemo
import pub.ihub.dsl.StepDemo



nameClassMappings = [
        首步  : new StepDemo('首步'),
        第一步 : new StepDemo('第一步'),
        第二步 : new StepDemo('第二步'),
        第三步 : new StepDemo('第三步'),
        第四步 : new StepDemo('第四步'),
        只有一步: new StepDemo('只有一步'),
        最后一步: new StepDemo('最后一步'),
        默认步骤: StepDemo,
        路由一 : new RouteDemo(1),
        路由二 : new RouteDemo(2)
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
