# spray-google-elevation

Example based on the [official spray example](https://github.com/spray/spray/blob/master/examples/spray-client/simple-spray-client/src/main/scala/spray/examples/Main.scala) to run from console or another program.

## Console use
```
import scala.concurrent.ExecutionContext.Implicits.global // Import some execution context
implicit val system = ActorSystem("elevation-actor") // Init actor system
val e = GoogleElevation() // get elevation instance
Await.result(e.getMtEverestElevation, Duration.Inf) // make request and wait for answer
```
## Console output
```
Welcome to Scala 2.11.8 (OpenJDK 64-Bit Server VM, Java 1.8.0_92).
Type in expressions for evaluation. Or try :help.

scala> import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext.Implicits.global

scala> implicit val system = ActorSystem("elevation-actor")
system: akka.actor.ActorSystem = akka://test-actor

scala> val e = GoogleElevation()
e: com.grburst.spraytest.GoogleElevation = GoogleElevation(akka://test-actor,scala.concurrent.impl.ExecutionContextImpl@74e9773e)

scala> Await.result(e.getMtEverestElevation, Duration.Inf)
[INFO] [10/07/2016 12:07:07.004] [run-main-0] [GoogleElevation(akka://test-actor)] Requesting the elevation of Mt. Everest from Googles Elevation API...
res0: String = 8815.7158203125

scala> e.shutdown
[INFO] [10/07/2016 12:07:17.837] [test-actor-akka.actor.default-dispatcher-9] [akka://test-actor/user/IO-HTTP/group-0/0] Message [akka.dispatch.sysmsg.Terminate] from Actor[akka://test-actor/user/IO-HTTP/group-0/0#704075546] to Actor[akka://test-actor/user/IO-HTTP/group-0/0#704075546] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.


```
