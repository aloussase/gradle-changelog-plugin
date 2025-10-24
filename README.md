# gradle-changelog-plugin

## Description

This is a Gradle plugin that generates changelog entries from Git commits. Yes, I know what the
[Keep a Changelog](https://keepachangelog.com/en/1.0.0/) guys say. I understand that point of view, but I think if
humans can write meaningful
changelog entries in a Markdown document, they can also write meaningful commits. Furthermore, I think it's
better if a CHANGELOG accurately reflects Git history and vice-versa.

## Usage

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
