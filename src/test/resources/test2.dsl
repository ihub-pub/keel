import pub.ihub.dsl.DSLMethod



nameClassMappings = [
        参数一: 'p1',
        参数二: 'p2',
        参数三: 'p3',
        方法一: { a ->
            println a
            a
        },
        方法二: { a, b ->
            println a + '-' + b
            a + '-' + b
        },
        方法三: 'split',
        方法四: 'join',
        方法五: new DSLMethod() {

            @Override
            def call(Object... args) {
                println args
                args[0] + '-' + args[1]
            }
        }
]

nameFlowMappings = [
        片段一: { 方法二 参数一, 参数二 方法三 '-', 0 方法四 '-' 方法五 参数三 }
]
