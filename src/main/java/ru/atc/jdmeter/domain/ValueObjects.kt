package ru.atc.jdmeter.domain

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by vkoba on 27.10.2016.
 */


class Percentage(val num: Double) {
    fun value(): Double {
        return BigDecimal(num)
                .multiply(BigDecimal(100))
                .setScale(2, RoundingMode.CEILING)
                .toDouble()
    }
}

class LineOfCode(val line: String) {
    fun hasCommentSymbols(): Boolean {
        return line.contains("*/")
    }

    fun hasOverrideAnnotation(): Boolean {
        return line.contains("@Override")
    }

}

class Method(val lineOfCode: String) {
    fun name(): String {
        val spaceBeforeMethodNameIndex = lineOfCode.substring(0, lineOfCode.indexOf("(")).lastIndexOf(" ")
        return lineOfCode.substring(spaceBeforeMethodNameIndex, lineOfCode.indexOf("(")).trim()
    }

    fun isPublicMethod(constructorName: String): Boolean {
        return lineOfCode.contains("public ") &&
                lineOfCode.contains("(") &&
                !lineOfCode.contains("class ") &&
                !lineOfCode.contains(" = ") &&
                !name().contains(constructorName)
    }

    fun methodIsNotSetter() = !Method(lineOfCode).name().startsWith("set")
}