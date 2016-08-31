package uk.gov.hmrc.SSTTP.services

import scala.concurrent.Future
import com.github.nscala_time.time.Imports._
import play.api.mvc.Result
import uk.gov.hmrc.play.microservice.controller.BaseController

object InterestRateCalculator extends InterestRateCalculator

trait InterestRateCalculator extends BaseController{

  def CalculateInterest(amount: Double, rate: Double, numberDays: Int): Future[Double] =
    Future.successful(BigDecimal((amount * rate * numberDays) / 36600).setScale(2, BigDecimal.RoundingMode.FLOOR).toDouble)

  def calcWorkingDays(existingDebit: Boolean) : Int = existingDebit match {
    case true => DateTime.now().getDayOfWeek match {
      case 1 | 2 | 7 => 3 //Mon, Tue, Sun
      case 3 | 4 | 5 => 5 //Wed, Thu Fri
      case 6 => 4 //Sat
    }
    case false => 7
  }

  def validateStartDate(initialPayDate: DateTime, startDate: DateTime): Boolean = (initialPayDate + 30.days) > startDate

  //workingDays, interestCharged
  /* User will have a start date, we must validate the start date. If okay, take this start date, look at the dueDate and paymentFrequency
  * to see how often they want to repay. Calculate a list of dates*/
  //def paymentSchedule(initialPayDate: DateTime, startDate: DateTime, dueDate: DateTime, paymentFrequency: String): List[String]

}
