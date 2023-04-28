package dao

import Model.{EmployeeTable, EmployeeInfo}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Inject
import scala.concurrent.Future

class EmployeeDatabaseAccess @Inject()(protected val dbConfigProvider :DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{

  val employees = TableQuery[EmployeeTable]

  def createNewEmployee(employee: EmployeeInfo): Future[Int] = {
    val insertAction = employees += employee
    db.run(insertAction)
  }

  def getAll : Future[Seq[EmployeeInfo]] ={

     db.run(employees.result)
  }

  def updateEmployeeByCmpAndEmpId(empId: Int, updatedEmployee: EmployeeInfo): Future[Option[EmployeeInfo]] = {
    val updateAction = employees
      .filter(_.empId === empId)
      .map(employee => (employee.empName,employee.empSalary,employee.empAge,employee.empCity,employee.empEmail,employee.empMobile,employee.empTeam))
      .update((updatedEmployee.empName,updatedEmployee.empSalary,updatedEmployee.empAge,updatedEmployee.empCity,updatedEmployee.empEmail,updatedEmployee.empMobile,updatedEmployee.empTeam))

    db.run(updateAction).flatMap {
      case 0 => Future.successful(None)
      case _ => Future.successful(Some(updatedEmployee.copy(empId = Some(empId))))
    }
  }

  def deleteEmployeeByEmpId(empId:Int) : Future[Int] ={
    val query = employees.filter(_.empId === empId)
    db.run(query.delete)
  }
}