# Setup

```bash
mkdir simple-web-app && cd simple-web-app
echo target > .gitignore && git init
echo 'scalaVersion := "2.11.8"' > build.sbt
mkdir -p src/main/scala/se/citerus/scala/sampleapp
mkdir -p src/test/scala/se/citerus/scala/sampleapp
```

# Lägg till testdependencies

`libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0"`
