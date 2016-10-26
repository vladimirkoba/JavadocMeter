package ru.atc.jdmeter.domain

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * User: Vladimir Koba
 * Date: 26.10.2016
 * Time: 23:44
 */
class JavaClassTest {
    val testClassText: String = """package ru.vkoba.configuration;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: Vladimir Koba
 * Date: 20.09.2015
 * Time: 12:25
 */

@Configuration
public class CamelConfig {

    public CamelConfig() {

    }

    @Bean
    public RouteBuilder fileRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file://D:/camel/in").to("file://D:/camel/out");

            }
        };
    }

    /**
     * method
     */
    @Bean
    public CamelContext camel() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(fileRoute());
        camelContext.start();
        return camelContext;
    }

    private void a() {

    }
}"""

    @Test
    fun hasCommentAboveClass_hasComment() {
        assertTrue(JavaClass("A", classWithComment()).hasCommentAboveClass())
    }

    @Test
    fun hasCommentAboveClass_noComment() {
        assertFalse(JavaClass("A", classNoComment()).hasCommentAboveClass())
    }

    @Test
    fun countOfPublicMethod_threePublicMethod_return3() {
        assertEquals(3, JavaClass("CamelConfig", testClassText.lines()).countOfPublicMethod())
    }

    @Test
    fun countOfPublicCommentedMethod_oneCommenedMethod_return1() {
        assertEquals(1, JavaClass("CamelConfig", testClassText.lines()).countOfCommentedPublicMethod())
    }

    private fun classWithComment() = listOf("/**", "*", "*comment", "*/", "public class A{", "}")
    private fun classNoComment() = listOf("import", "public class A{", "}", "/**")

}

