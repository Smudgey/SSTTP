package uk.gov.hmrc.SSTTP.services

import uk.gov.hmrc.play.microservice.controller.BaseController

/**
  * Created by MacZ on 24/08/2016.
  */
trait InterestRateCalculator extends BaseController{

  val name: String = "Bob"
  val interestRate: Double = 0.015
  val Days:Int = 23
  val result: Long
  val rounded: Double
  def CalculateInterest(amount: Double, rate: Double, numberDays: Int) =  {
    val calculator = (amount * rate * numberDays) / 36600
  }//number 36600 used for calculation

}
