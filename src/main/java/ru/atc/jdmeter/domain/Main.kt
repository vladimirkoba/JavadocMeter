package ru.atc.jdmeter.domain

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by vkoba on 27.10.2016.
 */


fun main(args: Array<String>) {
    val modules = ModuleFactory("""C:\Users\akhovanskii\nspk\billing-system""").createModules()
    for (module in modules) {
        println("${module.name}\t ${ModuleStatistic(module).coveragePercent()}")
    }
    println("-----------------------------------------")
    println("Total\t${ProjectStatistic(modules).coveragePercent()}")
}






