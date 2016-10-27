package ru.atc.jdmeter.domain

import org.junit.Test

import org.junit.Assert.*

/**
 * User: Vladimir Koba
 * Date: 27.10.2016
 * Time: 0:00
 */
class MethodNameTest {
    @Test
    fun extractNameFromLineOfCode() {
        assertEquals("getName", Method("public static void getName(String u){").name())
    }

}