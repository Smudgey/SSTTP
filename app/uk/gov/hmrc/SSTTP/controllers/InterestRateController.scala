package uk.gov.hmrc.SSTTP.controllers

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Action
import uk.gov.hmrc.SSTTP.connectors.TaxToPayDataConnector
import uk.gov.hmrc.SSTTP.models.TaxToPayData
import uk.gov.hmrc.SSTTP.services.InterestRateCalculator
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object InterestRateController extends InterestRateController

trait InterestRateController extends BaseController with InterestRateCalculator {

  def show() = Action.async { implicit request =>
    Future.successful(Ok("Interest Rate Calculator"))
  }

  def calculateInterest: Action[JsValue] = Action.async(parse.json) { implicit request =>
    withJsonBody[TaxToPayData] {
      taxDetails =>
        InterestRateController.CalculateInterest(taxDetails.debtAmount, taxDetails.taxRate, taxDetails.numberDays) map {
          interestAmount: Double =>
            TaxToPayDataConnector.submitFeedback(interestAmount)
            Ok(Json.toJson(
              interestAmount.toString
            ))
        }
    }
  }
}
