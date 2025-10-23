package io.github.aloussase.changelog.formats

enum class DocumentFormat {
    Markdown;

    companion object {
        fun parse(format: String): DocumentFormat? {
            return when (format) {
                "markdown", "md" -> Markdown
                else -> null
            }
        }
    }
}
