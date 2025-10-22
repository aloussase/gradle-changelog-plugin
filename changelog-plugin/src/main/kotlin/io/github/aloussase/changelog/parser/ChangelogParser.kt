package io.github.aloussase.changelog.parser

import io.github.aloussase.changelog.Changelog

interface ChangelogParser {

    /**
     * Parse the provided document as a Changelog.
     */
    fun parse(doc: String): Result<Changelog>

}
