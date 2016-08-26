package uk.gov.hmrc.SSTTP.services

import com.github.nscala_time.time.Imports._
import uk.gov.hmrc.play.microservice.controller.BaseController

trait InterestRateCalculator extends BaseController{

  /**
    * Calculate the amount of interest to pay
    * @param amount
    * @param rate
    * @param numberDays
    * @return
    */
  def CalculateInterest(amount: Double, rate: Double, numberDays: Int): Double =
    BigDecimal((amount * rate * numberDays) / 36600).setScale(2, BigDecimal.RoundingMode.FLOOR).toDouble

  /**
    * For new Direct Debits, SSTTP must allow for either 3 days (for existing Direct Debit Instructions) or 5 days (for new Direct Debit Instructions)
    * for the DDI to be processed. This is to allow for the DDI to be processed by the banks, so that HMRC can subsequently submit the Payment
    * Instruction to collect the funds. The start date and initial collection date must both be at least 3/5 working days in the future.
    * @param existingDebit
    * @return
    */
  def calcWorkingDays(existingDebit: Boolean) : Int = existingDebit match {
    case true => DateTime.now().getDayOfWeek match {
      case 1 => 3 //Mon
      case 2 => 3 //Tue
      case 3 => 5 //Wed
      case 4 => 5 //Thu
      case 5 => 5 //Fri
      case 6 => 4 //Sat
      case 7 => 3 //Sun
    }
    case false => 7
  }

  /**
    * Check to see if the startDate is over 30 days after the initialPayDate
    * @param initialPayDate
    * @param startDate
    * @return
    */
  def validateStartDate(initialPayDate: DateTime, startDate: DateTime): Boolean = (initialPayDate + 30.days) > startDate

  //workingDays, interestCharged
  /* User will have a start date, we must validate the start date. If okay, take this start date, look at the dueDate and paymentFrequency
  * to see how often they want to repay. Calculate a list of dates*/
  //def paymentSchedule(initialPayDate: DateTime, startDate: DateTime, dueDate: DateTime, paymentFrequency: String): List[String]

}
