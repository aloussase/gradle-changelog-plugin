package io.github.aloussase.changelog.git.commands

interface GitCommand<T> {

    val commandLine: String

    fun execute(): Result<T>

}
