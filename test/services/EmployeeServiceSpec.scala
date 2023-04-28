package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers.{GET, _}

class EmployeeServiceSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "EmployeeController GET" should {

    "retrieve a specific employee from a company" in {
      val companyName = "ThoughtGenesis"
      val employeeId = 1
      val request = FakeRequest(GET, s"/api/employee/$companyName/$employeeId")
      val response = route(app, request).get

      status(response) mustBe OK
      contentType(response) mustBe Some("application/json")
//      contentAsString(response) must include("{\"empId\":1,\"empCompany\":\"ThoughtGenesis\",\"empName\":\"Harsh Kedia\",\"empSalary\":10,\"empAge\":22,\"empCity\":\"Jaipur\",\"empEmail\":\"harsh.kedia@thoughtgenesis.com\",\"empMobile\":\"1234567789\",\"empTeam\":\"maps\"}")
    }
  }
    "EmployeeController DELETE" should {
      "deleting an employee using its  employee id" in {
        val employeeId = 1
        val request = FakeRequest(DELETE, s"/api/employee/delete/$employeeId")
        val response = route(app, request).get

        status(response) mustBe OK
        contentType(response) mustBe Some("application/json")
      }
    }

}

