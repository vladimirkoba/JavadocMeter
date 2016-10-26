package ru.atc.jdmeter.domain

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import ru.atc.jdmeter.domain.Module
import ru.atc.jdmeter.domain.ProjectStatistic
import java.io.File
import java.io.FilenameFilter

/**
 * Created by vkoba on 26.10.2016.
 */
public interface Project {
    fun modules(): List<Module>
    fun statistic(): ProjectStatistic
}

class JavaProject(val rootDirectoryPath: String) : Project {
    override fun statistic(): ProjectStatistic {
        throw UnsupportedOperationException("not implemented")
    }

    override fun modules(): List<Module> {
        val javaFiles = File("").listFiles(FilenameFilter { dir, name -> name.endsWith(".java") })
        val moduleNameToJavaFile = ArrayListValuedHashMap<String, File>()
        for (javaFile in javaFiles) {
            moduleNameToJavaFile.put(NameOfModule(javaFile.absolutePath).name(), javaFile)
        }
        val modules = mutableListOf<Module>()
        moduleNameToJavaFile.keys().forEach { moduleName ->
            val filesForModule = moduleNameToJavaFile[moduleName];
            modules.add(JavaModule(moduleName, filesForModule.map { f -> JavaClass(f.name, f) }))
        }
        return modules
    }

    private fun toClass(filesForModule: List<File>): List<Class> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}


class NameOfModule(val absolutePath: String) {
    fun name(): String {
        val pathWithSlash = absolutePath.replace("\\", "/")
        return pathWithSlash.take(pathWithSlash.indexOf("/src")).substringAfterLast("/")
    }

}