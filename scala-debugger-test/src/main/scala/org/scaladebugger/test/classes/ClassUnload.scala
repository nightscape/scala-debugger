package org.scaladebugger.test.classes

import java.net.URLClassLoader

/**
 * Provides test of class unloading used to verify reception of class unload
 * events.
 */
object ClassUnload extends App {
  /* Load our test class from the test jar, then force out of scope */ {
    val testJarUrl = this.getClass.getResource("/TestJar.jar")
    val urlClassLoader = new URLClassLoader(Array(testJarUrl), null)

    val className = "org.scaladebugger.test.jar.CustomClass"
    val klass = urlClassLoader.loadClass(className)
    val instance = klass.getDeclaredConstructor().newInstance() // Causes class to be loaded

    println(s"Loading classes from ${testJarUrl.getPath}")
    println(s"Class[CustomClass]: $klass")
    println(s"CustomClass: $instance")
  }

  // Suggest garbage collecting our out-of-scope classloader to unload the
  // associated class
  System.gc()

  while (true) { Thread.sleep(1000) }
}
