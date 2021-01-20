# DBC Build

The `dbc` brach deploys to DBC's internal mavenrepo - it is based upon a stable upstream branch.

Merge tags into it, to keep aligned with upstream.
```sh
git fetch https://github.com/logstash/logstash-logback-encoder.git "refs/tags/*:refs/tags/*"
git checkout dbc
git merge logstash-logback-encoder-...
```