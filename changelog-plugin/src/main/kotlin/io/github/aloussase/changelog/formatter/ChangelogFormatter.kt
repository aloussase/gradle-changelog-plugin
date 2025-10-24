package io.github.aloussase.changelog.formatter

import io.github.aloussase.changelog.Changelog

interface ChangelogFormatter {
    /**
     * Format a changelog into a string.
     */
    fun format(changelog: Changelog): String
}
