import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._

import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._

object Build extends sbt.Build{
  val cross = new utest.jsrunner.JsCrossBuild(
    organization := "com.lihaoyi",

    version := "0.1.4",
    name := "autowire",
    scalaVersion := "2.11.2",
    scalacOptions += "-Xlog-implicits",
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    ) ++ (
      if (scalaVersion.value startsWith "2.11.") Nil
      else Seq(
        compilerPlugin("org.scalamacros" % s"paradise" % "2.0.0" cross CrossVersion.full),
        "org.scalamacros" %% s"quasiquotes" % "2.0.0"
      )
    ),
    // Sonatype
    publishArtifact in Test := false,
    publishTo := Some("releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"),

    pomExtra :=
      <url>https://github.com/lihaoyi/ajax</url>
      <licenses>
        <license>
          <name>MIT license</name>
          <url>http://www.opensource.org/licenses/mit-license.php</url>
        </license>
      </licenses>
      <scm>
        <url>git://github.com/lihaoyi/ajax.git</url>
        <connection>scm:git://github.com/lihaoyi/ajax.git</connection>
      </scm>
      <developers>
        <developer>
          <id>lihaoyi</id>
          <name>Li Haoyi</name>
          <url>https://github.com/lihaoyi</url>
        </developer>
      </developers>
  )

  lazy val root = cross.root

  lazy val js = cross.js.settings(
    resolvers ++= Seq(
      "bintray-alexander_myltsev" at "http://dl.bintray.com/content/alexander-myltsev/maven"
    ),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "0.2.2" % "test"
    )
  )

  lazy val jvm = cross.jvm.settings(
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "upickle" % "0.2.2" % "test",
      "org.scala-lang" %% "scala-pickling" % "0.8.0" % "test",
      "com.esotericsoftware.kryo" % "kryo" % "2.24.0" % "test"
    )
  )
}

