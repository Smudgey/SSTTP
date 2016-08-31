package uk.gov.hmrc.SSTTP.services

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import uk.gov.hmrc.play.microservice.controller.BaseController

/**
  * Created by MacZ on 24/08/2016.
  */
trait InterestRateCalculator extends BaseController{

  val name: String = "Bob" //should be inputted by user
  val amount : Double = 1233.00 //should be inputted by user
  val interestRate: Double = 0.015 //should be inputted by user
  val Days:Int = 23 //should be inputted by user
  val result: Long = 200

  val reference: String = "" //tax identifier
  //val liabilities = Array
  //val initialPayment = ???
  val ExistingDD = true //existing Direct debit
  val dateformat = new SimpleDateFormat("dd-MM-yyyy")
  val initialPaymentDate: String = dateformat.format(Calendar.getInstance().getTime())

  //inputs for payment schedule
  val startDate : String = dateformat.format(Calendar.getInstance().getTime())
  val endDate : String = "14-11-2016"
  val paymentFreq: String = ""

  //Interest Charged
  def CalculateInterest(amount: Double, rate: Double, numberDays: Int): Double =
    BigDecimal((amount * rate * numberDays) / 36600).setScale(2, BigDecimal.RoundingMode.FLOOR).toDouble



  val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) match  {

  case Calendar.SUNDAY => if (ExistingDD) 3 else 7
  case Calendar.MONDAY => if (ExistingDD) 3 else 7
  case Calendar.TUESDAY => if (ExistingDD) 3 else 7
  case Calendar.WEDNESDAY => if (ExistingDD) 5 else 7
  case Calendar.THURSDAY =>  if (ExistingDD) 5 else 7
  case Calendar.FRIDAY => if (ExistingDD) 5 else 7
  case Calendar.SATURDAY => if (ExistingDD) 4 else 7

  }

//Outputs
  //startDate.before(DueDate)/total amount payable
   val total: Double  = amount + CalculateInterest(amount, interestRate, Days)


  def paymentSchedule (startDate: Calendar, DueDate: Calendar, initialPayDate: Date, interestCharged: Double, paymentFreq: String,WorkingDays: Int): Unit ={

    val currentDate = Calendar.getInstance().getTime()
    val limitDate = startDate.add(Calendar.DATE, 30)

    //getting current week number
    val ca1 = Calendar.getInstance()
    ca1.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_WEEK)//YYMMDAY
    ca1.setMinimalDaysInFirstWeek(1)
    val wk = ca1.get(Calendar.WEEK_OF_MONTH)
    System.out.println("Week of Month :" + wk)

    //getting end date week number
    DueDate.setMinimalDaysInFirstWeek(1)
    val wk2 = DueDate.get(Calendar.WEEK_OF_MONTH)
    System.out.println("Week of Month :" + wk2)

   val difference =  (wk2 - wk)/4 //months between the two dates
  }


 // }


}
