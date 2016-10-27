package ru.atc.jdmeter.domain

/**
 * Created by vkoba on 26.10.2016.
 */

interface Class {
    fun countOfPublicMethod(): Int
    fun countOfCommentedPublicMethod(): Int
    fun hasCommentAboveClass(): Boolean
    fun statistic(): ClassStatistic
}

class JavaClass(val name: String, val linesOfCode: List<String>) : Class {
    override fun hasCommentAboveClass(): Boolean {
        return linesOfCode.subList(0, linesOfCode.indexOfFirst { s -> s.contains("class $name") })
                .reduceRight { s1, s2 -> s1 + s2 }
                .contains("/**")
    }

    override fun countOfPublicMethod(): Int {
        return linesOfCode
                .filter { line -> Method(line).isPublicMethod(name) && Method(line).methodIsNotSetter() }
                .count()
    }

    override fun countOfCommentedPublicMethod(): Int {
        val publicMethodLines = linesOfCode
                .filter { line -> Method(line).isPublicMethod(name) && Method(line).methodIsNotSetter() }

        var commentedPublicMethods = 0
        for (i in linesOfCode.indices) {
            if (publicMethodLines.contains(linesOfCode[i])) {
                val potentialCommentArea = linesOfCode.subList(i - 3, i);
                if (potentialCommentArea
                        .filter { line -> LineOfCode(line).hasCommentSymbols() || LineOfCode(line).hasOverrideAnnotation() }
                        .count() > 0) {
                    commentedPublicMethods++
                }
            }
        }
        return commentedPublicMethods
    }

    override fun statistic(): ClassStatistic {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "JavaClass(name='$name')"
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

    fun isPublicMethod(constructoName: String): Boolean {
        return lineOfCode.contains("public ") &&
                lineOfCode.contains("(") &&
                !lineOfCode.contains("class ") &&
                !name().contains(constructoName)
    }

    fun methodIsNotSetter() = !Method(lineOfCode).name().startsWith("set")
}