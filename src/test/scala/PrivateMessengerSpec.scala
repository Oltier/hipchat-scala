import com.imadethatcow.hipchat._
import com.typesafe.config.ConfigFactory
import org.scalatest._
import scala.util.Try

class PrivateMessengerSpec  extends FlatSpec {
  val config = ConfigFactory.load
  val API_TOKEN_KEY = "com.imadethatcow.hipchat.auth_token"
  val TEST_ROOM_KEY = "com.imadethatcow.hipchat.test_room"
  val TEST_EMAIL_KEY = "com.imadethatcow.hipchat.test_email"
  val apiTokenTry = Try(config.getString(API_TOKEN_KEY))
  val testRoomTry = Try(config.getString(TEST_ROOM_KEY))
  val testEmailTry = Try(config.getString(TEST_EMAIL_KEY))
  if (apiTokenTry.isFailure) fail("Could not find api_token in config")
  if (testRoomTry.isFailure) fail("Could not find test_room in config")
  if (testEmailTry.isFailure) fail("Could not find test_email in config")

  for (apiToken <- apiTokenTry; room <- testRoomTry; email <- testEmailTry) {
    val message = "This is an automated private message"
    val messager = new PrivateMessenger(apiToken)

    "Private message" should "not fail" in {
      assert(messager.call(email, message))
    }
  }
}