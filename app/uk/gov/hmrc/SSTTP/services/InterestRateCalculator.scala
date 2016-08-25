package uk.gov.hmrc.SSTTP.services

import uk.gov.hmrc.play.microservice.controller.BaseController

/**
  * Created by MacZ on 24/08/2016.
  */
trait InterestRateCalculator extends BaseController{

  val name: String = "Bob"
  val amount : Double = 1233.00
  val interestRate: Double = 0.015
  val Days:Int = 23
  val result: Long
  val roundedCalculation = BigDecimal(CalculateInterest(amount, interestRate, Days)).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  def CalculateInterest(amount: Double, rate: Double, numberDays: Int): Double = {
    val result: Double = (amount * rate * numberDays) / 36600
    result
  }//number 36600 used for calculation

}
