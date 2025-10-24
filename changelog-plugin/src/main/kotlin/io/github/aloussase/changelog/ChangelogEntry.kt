package io.github.aloussase.changelog

import io.github.aloussase.changelog.git.Commit

data class ChangelogEntry(
    val release: String,
    val commits: List<Commit> = emptyList(),
) : Comparable<ChangelogEntry> {
    override fun compareTo(other: ChangelogEntry): Int {
        val myRelease = release.split(".").map { it.toInt() }
        val otherRelease = other.release.split(".").map { it.toInt() }
        return Comparator
            .comparingInt<List<Int>> { it[0] }
            .thenComparing { it[1] }
            .thenComparing { it[2] }
            .reversed()
            .compare(myRelease, otherRelease)
    }
}
