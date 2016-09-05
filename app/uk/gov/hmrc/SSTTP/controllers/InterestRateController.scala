package uk.gov.hmrc.SSTTP.controllers

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import play.api.libs.json
import play.api.mvc.Action
import uk.gov.hmrc.SSTTP.connectors.TaxToPayDataConnector
import uk.gov.hmrc.SSTTP.models.TaxToPayData
import uk.gov.hmrc.SSTTP.services.InterestRateCalculator
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object InterestRateController extends InterestRateController

trait InterestRateController extends BaseController with InterestRateCalculator {


  case class createItem(amount: Double, days: Int, interest: Double)

  case class submit(id: Int, amount: Double, days: Int, Interest: Double)

  var bob = submit(1, 1.3, 4, 5.5)
  var venesa = submit(2, 2.2, 5, 7.7)
  val People = Set(bob, venesa)


  def show() = Action.async { implicit request =>
    Future.successful(Ok("Interest Rate Calculator"))
  }
  def hello: Action[JsValue]  = Action.async(parse.json){ implicit request =>
    //		withJsonBody[Results]{
    //			userDetails =>
    //				Calculate(userDetails)
    //		}
    //		Future.successful(Redirect(helloWorld.hello + "/SSTTP-frontend/hello-world"))
    val passedJson = request.body
    val referenceNumber = passedJson \ "amount"

    println("++++++++++++++++++++" + referenceNumber.getClass)


    println("\n\n\n\n\n\n\n\n\n\n++++++++++++++++++++eeee" + referenceNumber)

    Future.successful(Ok(JsString("from FE we got = " + referenceNumber)))

  }

  //def reads(json: JsValue): JsResult[createItem]

  implicit val reads = Json.reads[createItem]
  implicit val readsCreateItem: Reads[createItem] = (
    (JsPath \ "amount").read[Double] and
      (JsPath \ "days").read[Int] and
      (JsPath \ "interest").read[Double]
    ) (createItem.apply _)

 /* val create = Action(parse.json) { implicit request =>
    request.body.validate[createItem] match {
  case JSSuccess(createItem, _) => val item1 = createItem(createItem.amount, createItem.days, createItem.Interest)
  case  json.JsError(errors) => BadRequest
}
}*/



  def calculateInterest2: Action[JsValue] = Action.async(parse.json) { implicit request =>
    withJsonBody[TaxToPayData] {
      taxDetails =>
        InterestRateController.CalculateInterest(taxDetails.debtAmount, taxDetails.numberDays, taxDetails.taxRate) map {
          interestAmount: Double =>
            TaxToPayDataConnector.submitFeedback(interestAmount)
            Ok(Json.toJson(
              interestAmount.toString
            ))
        }
    }
  }

  def respond2(id: Long) = Action {
    id match {
      case 1 =>
        Ok(Json.obj(
          "amount" -> bob.amount,
          "days" -> bob.days,
          "interest" -> bob.Interest
    ) )
      case 2 =>  Ok(Json.obj(
        "amount" -> bob.amount,
        "days" -> bob.days,
        "interest" -> bob.Interest
      ) )
      case _ => NotFound
    }
  }

  def respond = Action {
    Ok("RESPOND!!!")
  }

  def calculateInterest: Action[JsValue] = Action.async(parse.json) { implicit request =>
    val passedJson = request.body
    val referenceNumber = passedJson \ "days"

    println("++++++++++++++++++++" + referenceNumber.getClass)

    val test = referenceNumber match {
      case JsNumber(x) => x
    }

    val res = test + 3.0


    println("++++++++++++++++++++" + res)

    Future.successful(Ok(JsString("from FE we got = " + res.toString)))

  }
}
