import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project


fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}
fun DependencyHandler.kapt(dep: String) {
    add("kapt", dep)
}

fun DependencyHandler.implementation(dep: String) {
    add("implementation", dep)
}

fun DependencyHandler.implementationProject(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", project(dependency))
    }
}
fun DependencyHandler.apiProject(list: List<String>) {
    list.forEach { dependency ->
        add("api", project(dependency))
    }
}



fun DependencyHandler.api(list: List<String>) {
    list.forEach { dependency ->
        add("api", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}