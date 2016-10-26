package ru.atc.jdmeter.domain

/**
 * Created by vkoba on 26.10.2016.
 */


data class ProjectStatistic(val countOfClasses: Int, val coveragePercent: Double) {

}

data class ModuleStatistic(val countOfClasses: Int, val coveragePercent: Double) {

}

data class ClassStatistic(val countOfClasses: Int, val coveragePercent: Double, val hasCommentAboveClass: Boolean) {

}