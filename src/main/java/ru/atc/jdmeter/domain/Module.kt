package ru.atc.jdmeter.domain

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import org.apache.commons.io.FileUtils
import java.io.File

/**
 * Created by vkoba on 26.10.2016.
 */




class Module(val name: String, val classes: List<Class>)

class ModuleFactory(val projectDirectoryPath: String)  {

    fun createModules(): List<Module> {
        val javaFiles = FileUtils.listFiles(File(projectDirectoryPath), arrayOf("java"), true)
        val filteredJavaFile = javaFiles.filter { f -> filesIgnoreTestAndGenerated(f) }
        val moduleNameToJavaFile = ArrayListValuedHashMap<String, File>()
        for (javaFile in filteredJavaFile) {
            moduleNameToJavaFile.put(NameOfModule(javaFile.absolutePath).name(), javaFile)
        }
        val modules = mutableListOf<Module>()
        for (moduleName in moduleNameToJavaFile.keys().uniqueSet()) {
            val filesForModule = moduleNameToJavaFile[moduleName]
            modules.add(Module(moduleName, filesForModule.map { f -> Class(f.nameWithoutExtension, f.readLines()) }))
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