package pub.ihub.dsl.test.config


import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pub.ihub.dsl.integration.config.IntegrationConfig

import java.lang.annotation.Retention
import java.lang.annotation.Target

import static java.lang.annotation.ElementType.TYPE
import static java.lang.annotation.RetentionPolicy.RUNTIME



/**
 * 用于标识Service测试用例的注解
 *
 * @author liheng
 */
@ContextConfiguration(
        classes = [IntegrationConfig, TestIntegrationConfig],
        initializers = ConfigFileApplicationContextInitializer
)
@SpringBootTest
@Retention(RUNTIME)
@Target([TYPE])
@interface ServiceFTConfig {

}
