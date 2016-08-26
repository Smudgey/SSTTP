package uk.gov.hmrc.SSTTP.controllers

import play.api.mvc.Action
import play.mvc.Controller
import uk.gov.hmrc.SSTTP.services.InterestRateCalculator
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

object InterestRateController extends InterestRateController

trait InterestRateController extends BaseController with InterestRateCalculator {

  def calculate() = Action.async { implicit request =>
    Future.successful(Ok("Hello world"))
  }
}
