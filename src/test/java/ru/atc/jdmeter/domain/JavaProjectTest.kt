package ru.atc.jdmeter.domain

import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by vkoba on 26.10.2016.
 */
class JavaProjectTest {

    @Test
    fun printStatistic() {
        var totalAllMethods = 0.0
        var totalCommentedMethods = 0.0
        val modules = JavaProject("""C:\Projects\billing-system""").modules()
        for (module in modules) {
            var moduleAllMethods = 0.0
            var moduleCommentedMethods = 0.0
            for (clazz in module.classes) {
                moduleAllMethods += clazz.countOfPublicMethod()
                moduleCommentedMethods += clazz.countOfCommentedPublicMethod()
            }
            totalAllMethods += moduleAllMethods
            totalCommentedMethods += moduleCommentedMethods
            println("${module.name}\t${BigDecimal(moduleCommentedMethods / moduleAllMethods).setScale(2, RoundingMode.CEILING).multiply(BigDecimal(100)).toPlainString()}")
        }
        println("---------------------------------------------")
        println("Total\t${BigDecimal(totalCommentedMethods / totalAllMethods).setScale(2, RoundingMode.CEILING).multiply(BigDecimal(100)).toPlainString()}")

    }
}