package uk.gov.hmrc.SSTTP.connectors

import org.joda.time.DateTime
import play.api.libs.json.JsValue
import uk.gov.hmrc.play.config.ServicesConfig
import uk.gov.hmrc.SSTTP.models.TaxToPayData
import play.api.Play.current
import play.api.libs.ws._
import play.api.libs.json._
import play.api.libs.ws.ning.NingAsyncHttpClientConfigBuilder

import scala.concurrent.{ExecutionContext, Future}

object TaxToPayDataConnector extends TaxToPayDataConnector with ServicesConfig {
  override val taxToPayUrl = baseUrl("SSTTP")
}

trait TaxToPayDataConnector {
  implicit val ttpReads = Json.reads[TaxToPayData]
  val taxToPayUrl: String

  def url(path: String) = s"$taxToPayUrl$path"

  def getTTP(taxToPayData: TaxToPayData)(implicit ec: ExecutionContext): Future[TaxToPayData] = {
    val holder: WSRequestHolder = WS.url(url("/TaxToPayData"))
    val futureResponse: Future[TaxToPayData] = holder.get().map {
      response =>
        (response.json \ "TaxToPayData").as[TaxToPayData]
    }
    futureResponse
  }

  def sendPayDates(dates: List[String])(implicit ec: ExecutionContext): Unit = {
    var data: JsObject = null
    var count: Int = 1
    def addDatesToData(remainingDates: List[String]): Unit = remainingDates.isEmpty match {
      case true =>
      case false => data ++ Json.obj(
        count.toString -> remainingDates.head
      )
        count = count + 1
        addDatesToData(remainingDates.tail)
    }
    val futureResponse: Future[WSResponse] = WS.url(url("-frontend/hello-world")).post(data)
  }

}
