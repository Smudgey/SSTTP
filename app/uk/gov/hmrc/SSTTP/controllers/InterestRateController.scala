package uk.gov.hmrc.SSTTP.controllers

import play.api.mvc.Action
import play.mvc.Controller
import uk.gov.hmrc.play.microservice.controller.BaseController

import scala.concurrent.Future

/**
  * Created by MacZ on 24/08/2016.
  */
object InterestRateController extends InterestRateController

trait InterestRateController extends BaseController{

  def calculate() = Action.async { implicit request =>
    Future.successful(Ok("Hello world"))
  }
}
