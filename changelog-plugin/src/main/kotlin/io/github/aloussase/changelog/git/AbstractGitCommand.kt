package io.github.aloussase.changelog.git

abstract class AbstractGitCommand<T> : GitCommand<T> {

    abstract fun transform(rawOutput: String): T

    override fun execute(): T {
        val runtime = Runtime.getRuntime()
        val ary = arrayOf("/bin/sh", "-c", commandLine)
        val process = runtime.exec(ary)
        process.waitFor()
        return process.inputReader().use { br ->
            transform(br.readText().trim())
        }
    }

}
