package ru.atc.jdmeter.domain

/**
 * Created by vkoba on 26.10.2016.
 */


interface Module {
    fun classes(): List<Class>
    fun statistic(): ModuleStatistic
}

class JavaModule(val name: String, val classes: List<Class>) : Module {
    override fun classes(): List<Class> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun statistic(): ModuleStatistic {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}