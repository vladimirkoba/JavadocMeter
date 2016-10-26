package ru.atc.jdmeter.domain

/**
 * Created by vkoba on 26.10.2016.
 */


interface Module {
    fun classes(): List<Class>
    fun statistic(): ModuleStatistic
}