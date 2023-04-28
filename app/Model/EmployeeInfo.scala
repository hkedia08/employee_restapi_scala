package model

import slick.jdbc.MySQLProfile.api._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsObject, JsPath, Json, Reads, Writes}

case class EmployeeInfo(empId: Option[Int], empCompany: String, empName: String, empSalary: Double, empAge: Int, empCity: String, empEmail: String, empMobile: String, empTeam: String)

class EmployeeTable(tag: Tag) extends Table[EmployeeInfo](tag, "Employee") {

  def empId = column[Option[Int]]("empId", O.PrimaryKey, O.AutoInc)
  def empCompany = column[String]("empCompany")
  def empName = column[String]("empName")
  def empSalary = column[Double]("empSalary")
  def empAge = column[Int]("empAge")
  def empCity = column[String]("empCity")
  def empEmail = column[String]("empEmail")
  def empMobile = column[String]("empMobile")
  def empTeam = column[String]("empTeam")

  override def * =
    (empId, empCompany, empName, empSalary, empAge, empCity, empEmail, empMobile, empTeam) <>
      ((EmployeeInfo.apply _).tupled, EmployeeInfo.unapply)
}

object EmployeeInfo {

  implicit val writes: Writes[EmployeeInfo] = new Writes[EmployeeInfo] {
    override def writes(o: EmployeeInfo): JsObject = Json.obj(
      "empId" -> o.empId,
      "empCompany" -> o.empCompany,
      "empName" -> o.empName,
      "empSalary" -> o.empSalary,
      "empAge" -> o.empAge,
      "empCity" -> o.empCity,
      "empEmail" -> o.empEmail,
      "empMobile" -> o.empMobile,
      "empTeam" -> o.empTeam
    )
  }

  implicit val reads: Reads[EmployeeInfo] = (
    (JsPath \ "empId").readNullable[Int] and
      (JsPath \ "empCompany").read[String] and
      (JsPath \ "empName").read[String] and
      (JsPath \ "empSalary").read[Double] and
      (JsPath \ "empAge").read[Int] and
      (JsPath \ "empCity").read[String] and
      (JsPath \ "empEmail").read[String] and
      (JsPath \ "empMobile").read[String] and
      (JsPath \ "empTeam").read[String]
    )(EmployeeInfo.apply _)
}
