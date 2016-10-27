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
                .filter { line -> (line.contains("public ") && line.contains("(") && !line.contains("class ")) }
                .filter { methodLine -> methodIsNotSetter(methodLine) && methodIsNotConstructor(methodLine) }
                .count()
    }

    override fun countOfCommentedPublicMethod(): Int {
        val publicMethodLines = linesOfCode
                .filter { line -> (line.contains("public ") && line.contains("(") && !line.contains("class ")) }
                .filter { methodLine -> methodIsNotSetter(methodLine) && methodIsNotConstructor(methodLine) }
        var commentedPublicMethods = 0
        for (i in linesOfCode.indices) {
            if (publicMethodLines.contains(linesOfCode[i])) {
                val potentialCommentArea = linesOfCode.subList(i - 3, i);
                if (potentialCommentArea.filter { line -> line.contains("*/") }.count() > 0) {
                    commentedPublicMethods++;
                }
            }
        }
        return commentedPublicMethods
    }

    override fun statistic(): ClassStatistic {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "JavaClass(name='$name'"
    }

    private fun methodIsNotConstructor(methodLine: String) = !MethodName(methodLine).name().contains(name)
    private fun methodIsNotSetter(methodLines: String) = !MethodName(methodLines).name().startsWith("set")

}

class MethodName(val methodDefinition: String) {
    fun name(): String {
        val spaceBeforeMethodNameIndex = methodDefinition.substring(0, methodDefinition.indexOf("(")).lastIndexOf(" ")
        return methodDefinition.substring(spaceBeforeMethodNameIndex, methodDefinition.indexOf("(")).trim()
    }
}