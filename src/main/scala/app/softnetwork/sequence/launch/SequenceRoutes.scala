package app.softnetwork.sequence.launch

import akka.actor.typed.ActorSystem
import app.softnetwork.api.server.{ApiRoute, ApiRoutes}
import app.softnetwork.sequence.service.SequenceService

/** Created by smanciot on 07/04/2022.
  */
trait SequenceRoutes extends ApiRoutes {

  override def apiRoutes: ActorSystem[_] => List[ApiRoute] =
    system => List(SequenceService(system))
}
