package uk.gov.hmrc.SSTTP.connectors

/**
  * Created by MacZ on 05/09/2016.
  */
import uk.gov.hmrc.play.config.ServicesConfig

/**
  * Created by yuan on 30/08/16.
  */
object helloWorldConnector extends ServicesConfig{
  lazy val hello = baseUrl("hello-world")
}