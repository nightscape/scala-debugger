package org.senkbeil.debugger.api.profiles.pure.monitors

import org.senkbeil.debugger.api.lowlevel.JDIArgument
import org.senkbeil.debugger.api.pipelines.Pipeline
import org.senkbeil.debugger.api.pipelines.Pipeline.IdentityPipeline
import org.senkbeil.debugger.api.profiles.traits.monitors.MonitorContendedEnteredProfile

import scala.util.Try

/**
 * Represents a pure profile for monitor contended entered events that adds no
 * extra logic on top of the standard JDI.
 */
trait PureMonitorContendedEnteredProfile extends MonitorContendedEnteredProfile {
  /**
   * Constructs a stream of monitor contended entered events.
   *
   * @param extraArguments The additional JDI arguments to provide
   *
   * @return The stream of monitor contended entered events and any retrieved
   *         data based on requests from extra arguments
   */
  override def onMonitorContendedEnteredWithData(
    extraArguments: JDIArgument*
  ): Try[IdentityPipeline[MonitorContendedEnteredEventAndData]] = ???
}
