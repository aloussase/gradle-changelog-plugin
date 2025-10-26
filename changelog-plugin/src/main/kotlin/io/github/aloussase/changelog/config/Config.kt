package io.github.aloussase.changelog.config

import io.github.aloussase.changelog.formats.DocumentFormat
import org.gradle.api.GradleException

class Config private constructor(
    val fileName: String,
    val gitBranch: String,
    val documentFormat: DocumentFormat,
    val ignoreCommits: Set<String>,
) {

    companion object {
        fun configure(extension: ChangelogPluginExtension): Config {
            val format = DocumentFormat.parse(extension.format.get())
            if (format == null) {
                throw GradleException("Invalid document format: cannot make a changelog from ${extension.format.get()}")
            }

            val branchName = extension.gitInfo.baseBranch.get()
            if (branchName.isBlank()) {
                throw GradleException("Branch name cannot be blank")
            }

            val outputFileName = extension.fileName.get()
            if (outputFileName.isBlank()) {
                throw GradleException("Output file name cannot be blank")
            }

            return Config(
                outputFileName,
                branchName,
                format,
                extension.gitInfo.ignoreCommits.get().toSet()
            )
        }
    }
}
