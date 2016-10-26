package ru.atc.jdmeter.domain

import org.junit.Assert.*
import org.junit.Test

/**
 * Created by vkoba on 26.10.2016.
 */
class JavaProjectTest {

    @Test
    fun modulesFromProjectStructure() {
        val modules = JavaProject("""C:\Projects\billing-system""").modules()
        print(modules.lastIndex)
    }
}