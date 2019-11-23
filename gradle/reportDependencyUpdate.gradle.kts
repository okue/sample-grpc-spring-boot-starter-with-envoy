import com.github.benmanes.gradle.versions.reporter.AbstractReporter
import com.github.benmanes.gradle.versions.reporter.result.DependencyOutdated
import com.github.benmanes.gradle.versions.reporter.result.Result
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import kotlinx.html.HEAD
import kotlinx.html.TABLE
import kotlinx.html.TBODY
import kotlinx.html.ThScope
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.link
import kotlinx.html.stream.appendHTML
import kotlinx.html.style
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.tr
import kotlinx.html.unsafe
import java.io.PrintStream

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlinx:kotlinx-html-jvm:0.6.12")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.27.0")
    }
}

task(name = "reportDependencyUpdates", type = DependencyUpdatesTask::class) {
    /**
     * Auxiliary functions and constant values for building report html
     */
    val bootstrapLink = "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"

    fun TABLE.headerOfReportingTable() = thead {
        tr {
            th(scope = ThScope.col) { +"Module" }
            th(scope = ThScope.col) { +"Current version" }
            th(scope = ThScope.col) { +"Latest version" }
        }
    }

    fun TBODY.itemOfUpdateCandidate(dependency: DependencyOutdated) = tr {
        td {
            a(href = dependency.projectUrl) {
                +"${dependency.group}:${dependency.name}"
            }
        }
        td { +dependency.version }
        td { +dependency.available.release }
    }

    fun HEAD.cssStyle(block: () -> String) = style {
        unsafe { raw(block().trimIndent()) }
    }

    /**
     * Auxiliary function to define what means an unstable version
     */
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    /**
     * Main settings
     */
    revision = "release"
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }

    /**
     * See [com.github.benmanes.gradle.versions.updates.DependencyUpdatesReporter],
     * to understand what [AbstractReporter] does.
     */
    outputFormatter = object : AbstractReporter() {

        override fun write(printStream: Any, result: Result) {
            check(printStream is PrintStream)
            val updateable = result.outdated.dependencies
            if (updateable.isEmpty()) return
            printStream.appendHTML().html {
                head {
                    link(rel = "stylesheet", href = bootstrapLink)
                    cssStyle {
                        """
                        .main {
                            width: 80%;
                            margin: auto;
                        }
                        """
                    }
                }
                body {
                    div(classes = "main") {
                        h2 { +"Dependency Update Report" }
                        table(classes = "table") {
                            headerOfReportingTable()
                            tbody {
                                updateable.forEach { itemOfUpdateCandidate(it) }
                            }
                        }
                    }
                }
            }
        }

        override fun getFileExtension() = "html"
    }
}
