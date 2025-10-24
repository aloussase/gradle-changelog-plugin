package io.github.aloussase.changelog

import io.github.aloussase.changelog.config.Config
import io.github.aloussase.changelog.formatter.ChangelogFormatterFactory
import io.github.aloussase.changelog.git.GitService
import io.github.aloussase.changelog.git.commands.GetCurrentBranchCommand
import io.github.aloussase.changelog.git.commands.GetCurrentBranchCommitsCommand
import io.github.aloussase.changelog.git.commands.GetCurrentReleaseCommand
import io.github.aloussase.changelog.parser.ChangelogParserFactory
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class ChangelogTask : DefaultTask() {

    @Input
    lateinit var config: Config

    private val gitService = GitService(
        GetCurrentBranchCommand(),
        GetCurrentBranchCommitsCommand(),
        GetCurrentReleaseCommand(),
    )

    @TaskAction
    fun run() {
        val parser = ChangelogParserFactory.createParser(config.documentFormat)
        val changelogFile = File(config.fileName)
        if (!changelogFile.exists()) {
            changelogFile.createNewFile()
        }
        val document = changelogFile.readText()
        val changelog = parser.parse(document).getOrThrow()

        val changes = gitService.getCurrrentBranchChanges()
        val releaseExists = changelog.entries.any { it.release == changes.release }

        val newChangelog =
            if (releaseExists) {
                Changelog(
                    changelog
                        .entries
                        .map {
                            if (it.release == changes.release) {
                                ChangelogEntry(
                                    changes.release,
                                    it.commits.toSet()
                                        .union(changes.commits.toSet())
                                        .toList()
                                )
                            } else {
                                it
                            }
                        }
                )
            } else {
                Changelog(listOf(changes) + changelog.entries)
            }

        val formatter = ChangelogFormatterFactory.create(config.documentFormat)

        changelogFile.writeText(formatter.format(newChangelog))
    }

}
