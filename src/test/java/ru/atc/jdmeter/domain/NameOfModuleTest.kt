package ru.atc.jdmeter.domain

import org.junit.Test
import kotlin.test.assertTrue


/**
 * Created by vkoba on 26.10.2016.
 */
class NameOfModuleTest {


    @Test
    fun value() {
        assertTrue(NameOfModule("""C:\Projects\JavadocMeter\src\main\java\ru\atc\jdmeter\domain\Class.kt""").name() == "JavadocMeter")
        assertTrue(NameOfModule("""C:/Projects/JavadocMeter/src/main/java/ru/atc/jdmeter/domain/Class.kt""").name() == "JavadocMeter")
    }


}