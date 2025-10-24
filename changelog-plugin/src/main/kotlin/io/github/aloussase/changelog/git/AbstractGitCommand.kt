package io.github.aloussase.changelog.git

abstract class AbstractGitCommand : GitCommand {

    override fun execute(): String {
        val runtime = Runtime.getRuntime()
        val ary = arrayOf("/bin/sh", "-c", commandLine)
        val process = runtime.exec(ary)
        process.waitFor()
        return process.inputReader().use { br ->
            br.readText().trim()
        }
    }

}
