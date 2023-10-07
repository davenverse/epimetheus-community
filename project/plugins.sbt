lazy val sbtTypelevelVersion = "0.5.4"
addSbtPlugin("org.typelevel" % "sbt-typelevel-ci" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-ci-signing" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-sonatype-ci-release" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-github" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-settings" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-site" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-mergify" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-mima" % sbtTypelevelVersion)

libraryDependencies ++= Seq(
  "io.get-coursier" %% "coursier" % "2.1.7"
)
