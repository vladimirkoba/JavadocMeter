package ru.atc.jdmeter.domain

/**
 * Created by vkoba on 27.10.2016.
 */


class ModuleStatistic(val module: Module) {

    fun coveragePercent(): Double {
        return Percentage(commentedPublicMethods().toDouble() / allPublicMethods().toDouble()).value();
    }

    fun wtfCount(): Int {
        return module.classes.map(Class::countOfWtf).reduce { pm1, pm2 -> pm1 + pm2 }
    }

    fun allPublicMethods(): Int = module.classes.map(Class::countOfPublicMethod).reduce { pm1, pm2 -> pm1 + pm2 }

    fun commentedPublicMethods(): Int = module.classes.map(Class::countOfCommentedPublicMethod).reduce { pm1, pm2 -> pm1 + pm2 }
}

class ProjectStatistic(val modules: List<Module>) {
    fun coveragePercent(): Double {
        val allPublicMethods = modules.map { module -> ModuleStatistic(module).allPublicMethods() }.reduce { m1, m2 -> m1 + m2 }
        val commentedPublicMethods = modules.map { module -> ModuleStatistic(module).commentedPublicMethods() }.reduce { m1, m2 -> m1 + m2 }
        return Percentage(commentedPublicMethods.toDouble() / allPublicMethods.toDouble()).value();
    }

}