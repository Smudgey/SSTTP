package uk.gov.hmrc.SSTTP.models

import com.github.nscala_time.time.Imports._
import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.libs.json.Reads
import play.api.libs.json.Format

object TaxToPayData {
  private val pattern = "dd/MM/yyyy"
  implicit val dateFormat = Format[DateTime](Reads.jodaDateReads(pattern), Writes.jodaDateWrites(pattern))
  implicit val formats = Json.format[TaxToPayData]
}

case class TaxToPayData(dueDate: DateTime, numberDays: Int, debtAmount: Double, startDate: DateTime, paymentFrequency: String)