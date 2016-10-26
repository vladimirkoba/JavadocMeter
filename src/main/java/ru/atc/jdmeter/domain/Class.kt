package ru.atc.jdmeter.domain

import java.io.File

/**
 * Created by vkoba on 26.10.2016.
 */

interface Class {
    fun name(): String
    fun statistic(): ClassStatistic
}

class JavaClass(val name: String, val file: File) : Class {
    override fun name(): String {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun statistic(): ClassStatistic {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}