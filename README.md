# gradle-changelog-plugin

## Description

This is a Gradle plugin that generates changelog entries from Git commits. Yes, I know what the
[Keep a Changelog](https://keepachangelog.com/en/1.0.0/) guys say. I understand that point of view, but I think if
humans can write meaningful
changelog entries in a Markdown document, they can also write meaningful commits. Furthermore, I think it's
better if a CHANGELOG accurately reflects Git history and vice-versa.

## Usage

Add the plugin dependency in your gradle config:

```groovy
plugins {
    id("io.github.aloussase.changelog")
}
```

Then, execute the `changelog` task:

```shell
./gradlew changelog
```

On first run, if there is not a changelog file, it will be created. You can configure the name of the file:

```groovy
changelog {
    format = "markdown"
    fileName = "CHANGELOG.md"

    git {
        baseBranch = "main"
        ignoreCommits = listOf(
                "test",
                "tests",
                "readme",
                "README"
        )
    }
}
```

In this example configuration, you can see that we are setting the file name to `CHANGELOG.md`.

We are also configuring the format to markdown, setting the base branch name against which to compare
the current branch. This means that the changelog will be populated with the commits in the current
branch that are not present in the base branch. This setup works with feature branches in mind.

Lastly, we are also configuring keywords to ignore. In this example, any commits containing the words
tests or readme will be excluded from the changelog. This helps keep the changelog nice and tidy, free
from housekeeping commits.

## Integrating with Git hooks

To run the `changelog` task whenever you push to your remote repository, you can copy paste the sample
pre-push hook script in `scripts/pre-push` to `.git/hooks/pre-push` and make sure the file is
executable:

```bash
cp ./scripts/pre-push ./.git/hooks/pre-push
chmod +x ./.git/hooks/pre-push
```

The script will run the `changelog` task whenever you push and add a commit to include the
changelog changes. Feel free to edit the script to set the commit message to whatever you like.

## Future work

- [ ] Task option to "publish" a release

## License

MIT
