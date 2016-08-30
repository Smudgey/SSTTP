package uk.gov.hmrc.SSTTP.services

import com.github.nscala_time.time.Imports._
import play.api.http.Status
import play.api.test.FakeRequest
import uk.gov.hmrc.SSTTP.controllers.InterestRateController
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}


class InterestRateCalculatorSpec extends UnitSpec with WithFakeApplication {

  val fakeRequest = FakeRequest("GET", "/")

  "GET /" should {
    "return 200" in {
      val result = InterestRateController.calculate()(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }

  "CalculateInterest " should {
    "return £ 35.81, with £3070.22 amount, 3.5% Interest, 122 Days" in {
      InterestRateController.CalculateInterest(3070.22, 3.5, 122) shouldBe 35.81
    }

    "return £ 24.71 with £1233.00 amount, 1.5% Interest, 489 Days" in {
      InterestRateController.CalculateInterest(1233.00, 1.5, 489) shouldBe 24.71
    }
  }

  "calcWorkingDays" should {
    "return 7 when calculating new debit working days from current date" in {
      InterestRateController.calcWorkingDays(false) shouldBe 7
    }

    "return current date +3 for Mon, Tues, Sun, +4 for Sat, +5 Wed, Thu, Fri when calculating existing debit working days from current date" in {
      InterestRateController.calcWorkingDays(true) shouldBe {
        DateTime.now().getDayOfWeek match {
          case 1 => 3 //Mon
          case 2 => 3 //Tue
          case 3 => 5 //Wed
          case 4 => 5 //Thu
          case 5 => 5 //Fri
          case 6 => 4 //Sat
          case 7 => 3 //Sun
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
