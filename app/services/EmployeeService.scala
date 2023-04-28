package services

import Model.EmployeeInfo
import dao.EmployeeDatabaseAccess
import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
@Singleton
class EmployeeService @Inject() (val employeedbaccess: EmployeeDatabaseAccess) {

  def createNewEmployee(employeeinfo: EmployeeInfo): Future[Int] = {
    employeedbaccess.createNewEmployee(employeeinfo)
  }

  def getEmployeeByCmpAndEmpId(empCompany: String, empId: Int): Future[Option[EmployeeInfo]] = {
    employeedbaccess.getAll.map(_.find(y => y.empId.contains(empId) && y.empCompany == empCompany))
  }

  def updateEmployeeByCmpAndEmpId(empCompany: String,empId: Int, employee: EmployeeInfo): Future[Option[EmployeeInfo]] = {
    getEmployeeByCmpAndEmpId(empCompany, empId).flatMap {
      case Some(_) =>
        val updatedEmployee = employee.copy(empId = Some(empId), empCompany = empCompany)
        employeedbaccess.updateEmployeeByCmpAndEmpId(empId, updatedEmployee).map(_ => Some(updatedEmployee))
      case None =>
        Future.successful(None)
    }
  }

  def deleteEmployeeByEmpId(empId: Int): Future[Int] = {
    employeedbaccess.deleteEmployeeByEmpId(empId)
  }

  def getEmployeesByCompany(empCompany: String): Future[Seq[EmployeeInfo]] = {
    employeedbaccess.getAll.map(_.filter(_.empCompany == empCompany))
  }

}
