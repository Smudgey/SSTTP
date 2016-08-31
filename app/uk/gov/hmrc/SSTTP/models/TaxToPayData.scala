package uk.gov.hmrc.SSTTP.models

import org.joda.time.DateTime
import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.libs.json.Reads
import play.api.libs.json.Format

case class TaxToPayData(//dueDate: DateTime,
                        debtAmount: Double,
                        numberDays: Int,
                        //startDate: DateTime,
                        //paymentFrequency: String,
                        taxRate: Double)

object TaxToPayData {
  //private val pattern = "dd/MM/yyyy"
  //implicit val dateFormat = Format[DateTime](Reads.jodaDateReads(pattern), Writes.jodaDateWrites(pattern))
  implicit val formats = Json.format[TaxToPayData]
}

