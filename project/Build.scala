import sbt._
import sbt.Keys._
import com.typesafe.sbt.pgp.PgpKeys._

object MainBuild extends Build {

  lazy val baseSettings =
    sbtrelease.ReleasePlugin.releaseSettings ++
    Sonatype.settings ++
    net.virtualvoid.sbt.graph.Plugin.graphSettings ++
    scoverage.ScoverageSbtPlugin.projectSettings

  lazy val buildSettings = baseSettings ++ Seq(
            organization := BuildSettings.organization,
            scalaVersion := Dependencies.Versions.scala,
              crossPaths := false,
           sourcesInBase := false,
        autoScalaLibrary := false,
       externalResolvers := BuildSettings.resolvers,
     checkLicenseHeaders := License.checkLicenseHeaders(streams.value.log, sourceDirectory.value),
    formatLicenseHeaders := License.formatLicenseHeaders(streams.value.log, sourceDirectory.value)
  )

  lazy val root = project.in(file("."))
    .aggregate(
      `iep-config`,
      `iep-governator`,
      `iep-guice`,
      `iep-launcher`,
      `iep-module-archaius2`,
      `iep-module-dynprop`,
      `iep-module-eureka`,
      `iep-module-jmxport`,
      `iep-module-karyon`,
      `iep-module-rxnetty`,
      `iep-nflxenv`,
      `iep-rxhttp`)
    .settings(buildSettings: _*)
    .settings(BuildSettings.noPackaging: _*)

  lazy val `iep-config` = project
    .dependsOn(`iep-nflxenv`)
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.archaiusCore,
      Dependencies.archaiusLegacy,
      Dependencies.jodaTime,
      Dependencies.equalsVerifier % "test"
    ))

  lazy val `iep-governator` = project
    .dependsOn(`iep-config`)
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.archaiusCore,
      Dependencies.archaiusLegacy,
      Dependencies.governator,
      Dependencies.guiceAssist,
      Dependencies.guiceCore,
      Dependencies.guiceGrapher,
      Dependencies.guiceMulti,
      Dependencies.guiceServlet,
      Dependencies.slf4jApi
    ))

  lazy val `iep-guice` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.guiceCore,
      Dependencies.slf4jApi
    ))

  lazy val `iep-launcher` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)

  lazy val `iep-module-archaius2` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.archaiusCore,
      Dependencies.archaiusGuice,
      Dependencies.archaiusLegacy,
      Dependencies.archaiusPersist,
      Dependencies.archaiusTypesafe,
      Dependencies.guiceCore,
      Dependencies.slf4jApi
    ))

  lazy val `iep-module-dynprop` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.archaiusCore,
      Dependencies.archaiusLegacy,
      Dependencies.eureka,
      Dependencies.guiceCore,
      Dependencies.slf4jApi
    ))

  lazy val `iep-module-eureka` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.eureka,
      Dependencies.guiceCore,
      Dependencies.slf4jApi
    ))

  lazy val `iep-module-jmxport` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.guiceCore,
      Dependencies.slf4jApi
    ))

  lazy val `iep-module-karyon` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.archaiusCore,
      Dependencies.archaiusLegacy,
      Dependencies.guiceCore,
      Dependencies.karyonAdmin,
      Dependencies.karyonCore,
      Dependencies.rxnettyCore,
      Dependencies.rxnettyCtxts,
      Dependencies.slf4jApi
    ))

  lazy val `iep-module-rxnetty` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.guiceCore,
      Dependencies.rxnettyCore,
      Dependencies.rxnettyCtxts,
      Dependencies.rxnettySpectator,
      Dependencies.slf4jApi
    ))

  lazy val `iep-nflxenv` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)

  lazy val `iep-rxhttp` = project
    .settings(buildSettings: _*)
    .settings(libraryDependencies ++= commonDeps)
    .settings(libraryDependencies ++= Seq(
      Dependencies.archaiusCore,
      Dependencies.archaiusLegacy,
      Dependencies.eureka,
      Dependencies.jzlib,
      Dependencies.rxjava,
      Dependencies.rxnettyCore,
      Dependencies.rxnettyCtxts,
      Dependencies.spectatorApi,
      Dependencies.spectatorSandbox,
      Dependencies.slf4jApi,
      Dependencies.equalsVerifier % "test"
    ))

  lazy val commonDeps = Seq(
    Dependencies.junitInterface % "test",
    Dependencies.scalatest % "test"
  )

  lazy val checkLicenseHeaders = taskKey[Unit]("Check the license headers for all source files.")
  lazy val formatLicenseHeaders = taskKey[Unit]("Fix the license headers for all source files.")
}
