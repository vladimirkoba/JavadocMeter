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

    fun notCommentedClasses(): List<String> {
        val publicMethodLines = linesOfCode
                .filter { line -> Method(line).isPublicMethod(name) && Method(line).methodIsNotSetter() }
        var notCommentedClasses = mutableListOf<String>();
        for (i in linesOfCode.indices) {
            if (publicMethodLines.contains(linesOfCode[i])) {
                val potentialCommentArea = findPotencialCommentArea(i, publicMethodLines)
                if (potentialCommentArea
                        .filter { line -> LineOfCode(line).hasCommentSymbols() || LineOfCode(line).hasOverrideAnnotation() }
                        .count() == 0) {
                    if (!notCommentedClasses.contains(this.name)) {
                        notCommentedClasses.add(this.name);
                    }
                }
            }
        }
        return notCommentedClasses;
    }

    fun countOfCommentedPublicMethod(): Int {
        val publicMethodLines = linesOfCode
                .filter { line -> Method(line).isPublicMethod(name) && Method(line).methodIsNotSetter() }

        var commentedPublicMethods = 0
        for (i in linesOfCode.indices) {
            if (publicMethodLines.contains(linesOfCode[i])) {
                val potentialCommentArea = findPotencialCommentArea(i, publicMethodLines);
                if (potentialCommentArea
                        .filter { line -> LineOfCode(line).hasCommentSymbols() || LineOfCode(line).hasOverrideAnnotation() }
                        .count() > 0) {
                    commentedPublicMethods++
                }
            }
        }
        return commentedPublicMethods
    }

    private fun findPotencialCommentArea(publicMethodLineNumber: Int, publicMethodLines: List<String>): List<String> {
        return linesOfCode.subList(getPreviousMethodEndNumber(publicMethodLines, publicMethodLineNumber), publicMethodLineNumber);

    }

    private fun getPreviousMethodEndNumber(publicMethodLines: List<String>, publicMethodLineNumber: Int): Int {
        for (i in (publicMethodLineNumber - 1) downTo 1) {
            val currentLine = linesOfCode[i];
            if (publicMethodLines.contains(currentLine) || currentLine.trim() == "}" || currentLine.contains(" class ") || currentLine.contains(" interface ") || currentLine.contains(" enum ")) {
                return i;
            }
        }
        return 0;
    }

    fun countOfWtf(): Int {
        return linesOfCode.filter { line -> line.toUpperCase().contains("WTF") }.count()
    }


}

