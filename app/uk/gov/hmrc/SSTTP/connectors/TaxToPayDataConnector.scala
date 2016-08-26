package uk.gov.hmrc.SSTTP.connectors

import uk.gov.hmrc.SSTTP.WSHttp
import uk.gov.hmrc.play.config.ServicesConfig
import uk.gov.hmrc.play.http.HttpGet

object TaxToPayDataConnector extends TaxToPayDataConnector with ServicesConfig {
  override val httpGet = WSHttp
  override val taxToPayUrl = baseUrl("SSTTP")
}

trait TaxToPayDataConnector {
  val httpGet: HttpGet
  val taxToPayUrl: String
  def url(path: String) = s"$taxToPayUrl$path"


}
