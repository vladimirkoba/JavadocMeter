package ru.atc.jdmeter.domain

/**
 * Created by vkoba on 26.10.2016.
 */




class JavaModule(val name: String, val classes: List<Class>) {

    override fun toString(): String {
        return "JavaModule(name='$name', classes=$classes)"
    }


}