package ru.atc.jdmeter.domain

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import org.apache.commons.io.FileUtils
import ru.atc.jdmeter.domain.Module
import ru.atc.jdmeter.domain.ProjectStatistic
import java.io.File
import java.io.FilenameFilter

/**
 * Created by vkoba on 26.10.2016.
 */
public interface Project {
    fun modules(): List<Module>
    fun statistic(modules: List<ModuleStatistic>): ProjectStatistic
}

class JavaProject(val rootDirectoryPath: String) : Project {


    override fun statistic(modulesStatistic: List<ModuleStatistic>): ProjectStatistic {
        var projectCountOfClasses = 0;
        var projectCoveragePercentage = 0.0;
        for (ms in modulesStatistic) {
            projectCountOfClasses += ms.countOfClasses
            projectCoveragePercentage += ms.coveragePercent
        }
        return ProjectStatistic(projectCountOfClasses, projectCoveragePercentage / projectCountOfClasses)
    }

    override fun modules(): List<Module> {
        val javaFiles = FileUtils.listFiles(File(rootDirectoryPath), arrayOf("java"), true)
        val filteredJavaFile = javaFiles.filter { f -> filesIgnoreTestAndGenerated(f) }
        val moduleNameToJavaFile = ArrayListValuedHashMap<String, File>()
        for (javaFile in filteredJavaFile) {
            moduleNameToJavaFile.put(NameOfModule(javaFile.absolutePath).name(), javaFile)
        }
        val modules = mutableListOf<Module>()
        moduleNameToJavaFile.keys().forEach { moduleName ->
            val filesForModule = moduleNameToJavaFile[moduleName];
            modules.add(JavaModule(moduleName, filesForModule.map { f -> JavaClass(f.name, f.readLines()) }))
        }
        return modules
    }

    private fun filesIgnoreTestAndGenerated(f: File) = !f.absolutePath.contains("target") && !f.absolutePath.contains("test") && !f.absolutePath.contains("generated")


}


class NameOfModule(val absolutePath: String) {
    fun name(): String {
        val pathWithSlash = absolutePath.replace("\\", "/")
        return pathWithSlash.take(pathWithSlash.indexOf("/src")).substringAfterLast("/")
    }

}