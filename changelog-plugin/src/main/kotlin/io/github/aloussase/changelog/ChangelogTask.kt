package io.github.aloussase.changelog

import io.github.aloussase.changelog.config.Config
import io.github.aloussase.changelog.formatter.ChangelogFormatterFactory
import io.github.aloussase.changelog.git.GetCurrentBranchCommand
import io.github.aloussase.changelog.parser.ChangelogParserFactory
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class ChangelogTask : DefaultTask() {

    @Input
    lateinit var config: Config

    @TaskAction
    fun run() {
        val parser = ChangelogParserFactory.createParser(config.documentFormat)
        val changelogFile = File(config.fileName)
        if (!changelogFile.exists()) {
            changelogFile.createNewFile()
        }
        val document = changelogFile.readText()
        val changelog = parser.parse(document).getOrThrow()

        // TODO: Add commits from current branch here.
        println(GetCurrentBranchCommand().execute())

        val formatter = ChangelogFormatterFactory.create(config.documentFormat)
        changelogFile.writeText(formatter.format(changelog))
    }

}
