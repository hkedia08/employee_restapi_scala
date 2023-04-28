package controllers.api

import Model.EmployeeInfo
import javax.inject._
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.libs.json.Format.GenericFormat
import play.api.mvc._
import services.EmployeeService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class EmployeeController @Inject()(val controllerComponents: ControllerComponents, val employeeService: EmployeeService) extends BaseController {

  /**
   * Add an employee.
   */

  def createNewEmployee(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[EmployeeInfo].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("errorMessage" -> "Bad Request", "errors" -> JsError.toJson(errors))))
      },
      employeeData => {
        employeeService.createNewEmployee(employeeData).map { _ =>
          Ok(Json.obj("message" -> "Employee created successfully"))
        }
      }
    )
  }

  /**
   * Get an employee by EmpID.
   * Make sure only the company that the employee belongs to can access the data.
   */
  def getEmployeeByCmpAndEmpId(empCompany: String, empId: Int): Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>
    employeeService.getEmployeeByCmpAndEmpId(empCompany, empId).map {
      case Some(employee) =>
        Ok(Json.toJson(employee))
      case None =>
        NotFound(Json.obj("errorMessage" -> "Employee Not Found"))
    }
  }

  /**
   * Update an employee.
   * only the company that the employee belongs to can update the data.
   * If the employee is not found, return an error response.
   */

  def updateEmployeeByCmpAndEmpId(empCompany: String,empId: Int): Action[JsValue] = Action(parse.json) async { implicit request =>
    request.body.validate[EmployeeInfo].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("errorMessage" -> "Bad Request", "errors" -> JsError.toJson(errors))))
      },
      employeeData => {
        employeeService.updateEmployeeByCmpAndEmpId(empCompany, empId, employeeData).map {
          case Some(updatedEmployee) =>
            Ok(Json.obj("message" -> "Employee updated successfully", "employee" -> Json.toJson(updatedEmployee)))
          case None =>
            NotFound(Json.obj("errorMessage" -> "Data Not Found"))
        }
      }
    )
  }

  /**
   * Delete an employee.
   * only the company that the employee belongs to can delete the data.
   * If the employee is not found, return an error response.
   */

  def deleteEmployeeByEmpId(empId: Int): Action[AnyContent] = Action.async { implicit request =>
    employeeService.deleteEmployeeByEmpId(empId).map {
      case count if count > 0 =>
        Ok(Json.obj("message" -> "Employee deleted successfully"))
      case _ =>
        NotFound(Json.obj("errorMessage" -> "Employee Not Found"))
    }
  }

  /**
   * Get all employees for a company.
   * only the company that the employees belong to can access the data.
   * If the company is not found, return an error response.
   */
  def getEmployeesByCompany(empCompany: String): Action[AnyContent] = Action.async { implicit request =>
    employeeService.getEmployeesByCompany(empCompany).map { employees =>
      if (employees.nonEmpty) {
        Ok(Json.toJson(employees))
      } else {
        NotFound(Json.obj("errorMessage" -> "Company Not Found"))
      }
    }
  }
}







