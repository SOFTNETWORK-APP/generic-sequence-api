package app.softnetwork.sequence.persistence.typed

import akka.actor.typed.ActorSystem
import app.softnetwork.persistence.scalatest.InMemoryPersistenceTestKit
import org.scalatest.wordspec.AnyWordSpecLike
import Sequence._
import app.softnetwork.persistence.launch.PersistentEntity
import app.softnetwork.persistence.message._
import app.softnetwork.sequence.message._
import org.slf4j.{Logger, LoggerFactory}

/** Created by smanciot on 19/03/2020.
  */
class SequenceSpec extends AnyWordSpecLike with InMemoryPersistenceTestKit {

  lazy val log: Logger = LoggerFactory getLogger getClass.getName

  import app.softnetwork.persistence.launch.PersistenceGuardian._

  /** initialize all entities
    */
  override def entities: ActorSystem[_] => Seq[PersistentEntity[_, _, _, _]] = _ =>
    List(
      Sequence
    )

  "Sequence" must {
    "handle Inc" in {
      val probe = createTestProbe[SequenceResult]()
      val ref = entityRefFor(TypeKey, "inc")
      ref ! IncSequence("")
      ref ! CommandWrapper(LoadSequence(""), probe.ref)
      probe.expectMessage(SequenceLoaded("inc", 1))
    }
    "handle Dec" in {
      val probe = createTestProbe[SequenceResult]()
      val ref = entityRefFor(TypeKey, "dec")
      ref ! IncSequence("")
      ref ! IncSequence("")
      ref ! DecSequence("")
      ref ! CommandWrapper(LoadSequence(""), probe.ref)
      probe.expectMessage(SequenceLoaded("dec", 1))
    }
    "handle Reset" in {
      val probe = createTestProbe[SequenceResult]()
      val ref = entityRefFor(TypeKey, "reset")
      ref ! IncSequence("")
      ref ! IncSequence("")
      ref ! ResetSequence("")
      ref ! CommandWrapper(LoadSequence(""), probe.ref)
      probe.expectMessage(SequenceLoaded("reset"))
    }
    "handle Load" in {
      val probe = createTestProbe[SequenceResult]()
      val ref = entityRefFor(TypeKey, "load")
      ref ! IncSequence("")
      ref ! CommandWrapper(LoadSequence(""), probe.ref)
      probe.expectMessage(SequenceLoaded("load", 1))
    }
  }
}
