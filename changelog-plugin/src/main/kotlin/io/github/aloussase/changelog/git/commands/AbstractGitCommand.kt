package io.github.aloussase.changelog.git.commands

import org.gradle.api.GradleException

abstract class AbstractGitCommand<T> : GitCommand<T> {

    abstract fun transform(rawOutput: String): T

    override fun execute(): Result<T> {
        val runtime = Runtime.getRuntime()
        val ary = arrayOf("/bin/sh", "-c", commandLine)
        val process = runtime.exec(ary)
        process.waitFor()
        return if (process.exitValue() == 0) {
            Result.success(
                process.inputReader().use { br ->
                    transform(br.readText().trim())
                }
            )
        } else {
            Result.failure(
                process.errorReader().use { br ->
                    GradleException(
                        br.readText()
                    )
                }
            )
        }
    }

}
