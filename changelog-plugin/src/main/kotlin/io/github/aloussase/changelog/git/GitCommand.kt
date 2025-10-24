package io.github.aloussase.changelog.git

interface GitCommand {

    val commandLine: String

    fun execute(): String

}
