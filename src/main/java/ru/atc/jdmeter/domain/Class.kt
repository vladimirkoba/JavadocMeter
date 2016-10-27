package ru.atc.jdmeter.domain

/**
 * Created by vkoba on 26.10.2016.
 */


class Class(val name: String, val linesOfCode: List<String>) {
    fun hasCommentAboveClass(): Boolean {
        return linesOfCode.subList(0, linesOfCode.indexOfFirst { s -> s.contains("class $name") })
                .reduceRight { s1, s2 -> s1 + s2 }
                .contains("/**")
    }

    fun countOfPublicMethod(): Int {
        return linesOfCode
                .filter { line -> Method(line).isPublicMethod(name) && Method(line).methodIsNotSetter() }
                .count()
    }

    fun countOfCommentedPublicMethod(): Int {
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



}

