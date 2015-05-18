The following describes how to release the Maven plugin. This is done manually, not via the Maven release target at this point.

## Deploying Snapshots ##

Make sure the `-SNAPSHOT` string is present in the `version` element.

```
mvn deploy
```

## Deploying Releases ##

Remove the `-SNAPSHOT` string from the `version` element, then commit.

Then tag the repository.

```
mvn -Dtag="<current release version>" -DtagBase=https://impala.googlecode.com/svn/tags/maven-plugin -DconnectionType=developerConnection scm:tag
```

Now perform the release.

```
mvn deploy -DperformRelease=true -Dgpg.passphrase=****
```

Follow the steps described in http://nexus.sonatype.org/oss-repository-hosting.html
to promote the release. The Nexus interface is here: http://oss.sonatype.org/index.html

The add the `-SNAPSHOT` string back to the `version` element, and change the snapshot version to the next one.