package pub.ihub.dsl.test


import spock.lang.Shared
import spock.lang.Specification



/**
 * @author liheng
 */
abstract class ATestActuatorUT extends Specification {

    @Shared
    protected actuator

    final setupSpec() {
        actuator = getActuator this.class.getResource(
                this.class.simpleName
                        .replaceAll('[A-Z][a-z]+', '_$0')
                        .replaceAll('[A-Z]+', '_$0')
                        .replaceAll('_+', '_')
                        .replaceAll('^_', '')
                        .toLowerCase() + '.dsl'
        )
    }

    abstract getActuator(URL scriptLocation)

}
