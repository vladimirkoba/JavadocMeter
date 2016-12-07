package ru.atc.jdmeter.domain

/**
 * Created by vkoba on 27.10.2016.
 */


class ModuleStatistic(val module: Module) {

    fun coveragePercent(): Double {
        return Percentage(commentedPublicMethods().toDouble() / allPublicMethods().toDouble()).value();
    }

    fun wtfCount(): Int {
        return module.classes.map(Class::countOfWtf).sum()
    }

    fun allPublicMethods(): Int = module.classes.map(Class::countOfPublicMethod).sum()

    fun commentedPublicMethods(): Int = module.classes.map(Class::countOfCommentedPublicMethod).sum()

    fun notCommentedClasses() = module.classes.map(Class::notCommentedClass);
}

class ProjectStatistic(val modules: List<Module>) {
    fun coveragePercent(): Double {
        val allPublicMethods = modules.map { module -> ModuleStatistic(module).allPublicMethods() }.sum()
        val commentedPublicMethods = modules.map { module -> ModuleStatistic(module).commentedPublicMethods() }.sum()
        return Percentage(commentedPublicMethods.toDouble() / allPublicMethods.toDouble()).value();
    }

}