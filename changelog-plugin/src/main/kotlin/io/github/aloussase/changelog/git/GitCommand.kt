package io.github.aloussase.changelog.git

interface GitCommand<T> {

    val commandLine: String

    fun execute(): T

}
