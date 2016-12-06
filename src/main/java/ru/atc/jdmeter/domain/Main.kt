package ru.atc.jdmeter.domain

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by vkoba on 27.10.2016.
 */
fun main(args: Array<String>) {
    val modules = ModuleFactory("""C:\Users\bshestakov\IDEA_PROJECTS\nspk-billing\source""").createModules()
    for (module in modules) {

        val all = ModuleStatistic(module).allPublicMethods()
        val done = ModuleStatistic(module).commentedPublicMethods()
        val remain = all - done

        println("${module.name.padEnd(21, ' ')}" +
                "\t${ModuleStatistic(module).coveragePercent()}%" +
                "\tWTF: ${ModuleStatistic(module).wtfCount()}" +
                "\t($done\\$all) = $remain")
    }
    println("-----------------------------------------")
    println("Total".padEnd(21, ' ') + "\t${ProjectStatistic(modules).coveragePercent()}%")
}