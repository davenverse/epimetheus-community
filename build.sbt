ThisBuild / version := "0.0.1" // global version to determine central or snapshots.
ThisBuild / organization := "io.chrisdavenport"
ThisBuild / organizationName := "Christopher Davenport"
ThisBuild / licenses := Seq(License.MIT)
ThisBuild / developers := List(
  tlGitHubDev("christopherdavenport", "Christopher Davenport")
)
ThisBuild / versionScheme := Some("early-semver")

ThisBuild / tlCiReleaseBranches := Seq("main")
ThisBuild / tlSonatypeUseLegacyHost := true


val Scala3 = "3.3.1"

ThisBuild / crossScalaVersions := Seq("2.13.12", Scala3)
ThisBuild / scalaVersion := Scala3

ThisBuild / testFrameworks += new TestFramework("munit.Framework")

ThisBuild / tlCiReleaseBranches := Seq("main")
ThisBuild / tlSitePublishBranch := Some("main")

ThisBuild / githubWorkflowJavaVersions := Seq(JavaSpec.temurin("17"))

ThisBuild / mergifyStewardConfig ~= {
  _.map(_.copy(mergeMinors = true, author = "davenverse-steward[bot]"))
}


// val munitCatsEffectV = "1.0.7"

val circuitV = "0.5.1"

val epimetheusV = "0.6.0-M1"

val mulesV = "0.7.0"

val redis4catsV = "1.5.0"

val rediculousV = "0.5.1"

val log4catsV = "2.6.0"


// Projects
lazy val `epimetheus-community` = tlCrossRootProject
  .aggregate(circuit, mules, log4cats, rediculous, redis4cats, site)

lazy val log4cats = mkProject("log4cats")
  .settings(
    name := "epimetheus-log4cats",
    version := "0.6.0",
    libraryDependencies ++= Seq(
      "org.typelevel"           %% "log4cats-core"              % log4catsV,
    )
  )

lazy val mules= mkProject("mules")
  .settings(
    name := "epimetheus-mules",
    version := "0.5.0",
    libraryDependencies ++= Seq(
      "io.chrisdavenport"           %% "mules"                      % mulesV,
    )
  )

lazy val rediculous = mkProject("rediculous")
  .settings(
    name := "epimetheus-rediculous",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "io.chrisdavenport"  %% "rediculous" % rediculousV,
    )
  )

lazy val redis4cats = mkProject("redis4cats")
  .settings(
    name := "epimetheus-redis4cats",
    version := "0.2.0",
    libraryDependencies ++= Seq(
      "dev.profunktor" %% "redis4cats-effects" % redis4catsV
    )
  )

lazy val circuit = mkProject("circuit")
  .settings(
    name := "epimetheus-circuit",
    version := "0.5.1",
    libraryDependencies ++= Seq(
      "io.chrisdavenport" %% "circuit" % circuitV,
    )
  )

lazy val site = project.in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .settings(
    tlSiteIsTypelevelProject := Some(TypelevelProject.Affiliate)
  )

def mkProject(name: String) =
  sbt.Project(name, file(name))
    .settings(
      mimaPreviousArtifacts := Set(),
      libraryDependencies += "io.chrisdavenport" %% "epimetheus" % epimetheusV,
      publish / skip := {
        import coursier._
        val mod = CrossVersion(crossVersion.value,  scalaVersion.value, scalaBinaryVersion.value)
          .get.apply(moduleName.value)
        val dep = Dependency(Module(Organization(organization.value), ModuleName(mod)), Keys.version.value)
        try { // if this dep already exists, skip publishing
          Resolve()
            .addDependencies(dep)
            .addRepositories(Repositories.sonatype("releases"))
            .run()
          true
        } catch {
          case _: error.ResolutionError => false
        }
      }

    )