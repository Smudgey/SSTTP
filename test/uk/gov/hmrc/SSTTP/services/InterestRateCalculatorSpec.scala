package uk.gov.hmrc.SSTTP.services

import com.github.nscala_time.time.Imports._
import uk.gov.hmrc.SSTTP.controllers.InterestRateController
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

class InterestRateCalculatorSpec extends UnitSpec with WithFakeApplication {

  "CalculateInterest " should {
    "return £ 35.81, with £3070.22 amount, 3.5% Interest, 122 Days" in {
      InterestRateController.CalculateInterest(3070.22, 3.5, 122).onComplete(implicit result =>
      result.get shouldBe 35.81)
    }

    "return £ 24.71 with £1233.00 amount, 1.5% Interest, 489 Days" in {
      InterestRateController.CalculateInterest(1233.00, 1.5, 489).onComplete(implicit result =>
        result shouldBe 24.71)
    }
  }

  "calcWorkingDays" should {
    "return 7 when calculating new debit working days from current date" in {
      InterestRateController.calcWorkingDays(false) shouldBe 7
    }

    "return current date +3 for Mon, Tues, Sun, +4 for Sat, +5 Wed, Thu, Fri when calculating existing debit working days from current date" in {
      InterestRateController.calcWorkingDays(true) shouldBe {
        DateTime.now().getDayOfWeek match {
          case 1 | 2 | 7 => 3 //Mon, Tue, Sun
          case 3 | 4 | 5 => 5 //Wed, Thu Fri
          case 6 => 4 //Sat
        }
      }
    }
  }

  "validateStartDate" should {
    "return true when Date within 30 days of the initial pay date" in {
      InterestRateController.validateStartDate(DateTime.now(), DateTime.now().plusDays(15)) shouldBe true
    }

    "return false when Date older than 30 days from the initial pay date" in {
      InterestRateController.validateStartDate(DateTime.now(), DateTime.now().plusDays(35)) shouldBe false
    }
  }

}
