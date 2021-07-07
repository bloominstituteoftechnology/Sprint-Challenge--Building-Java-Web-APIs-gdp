package com.lambdaschool.gdp.handler

import com.lambdaschool.gdp.exception.ResourceNotFoundException
import com.lambdaschool.gdp.model.ErrorDetail
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import javax.servlet.http.HttpServletRequest
import java.util.Date


//Add appropriate exception handling routines. This is the standard exception handling covered in class. Required exceptions to handle are when

// bean shared across controller classes
//       a resource is not found
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(rnfe: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<*> {
        val errorDetail = ErrorDetail()
        errorDetail.setTimestamp(Date().time)
        errorDetail.status = HttpStatus.NOT_FOUND.value()
        errorDetail.title = "Resource Not Found"
        errorDetail.detail = rnfe.message
        errorDetail.developerMessage = rnfe.javaClass.name

        return ResponseEntity(errorDetail, null, HttpStatus.NOT_FOUND)
    }

    //        the wrong data type is used for a path variable

    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errorDetail = ErrorDetail()
        errorDetail.setTimestamp(Date().time)
        errorDetail.status = HttpStatus.BAD_REQUEST.value()
        errorDetail.title = ex.propertyName
        errorDetail.detail = ex.message
        errorDetail.developerMessage = request.getDescription(true)

        return ResponseEntity(errorDetail, null, HttpStatus.NOT_FOUND)
    }


    //        a non-handled endpoint is accessed (a URL not found exception)
    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errorDetail = ErrorDetail()
        errorDetail.setTimestamp(Date().time)
        errorDetail.status = HttpStatus.NOT_FOUND.value()
        errorDetail.title = ex.requestURL
        errorDetail.detail = request.getDescription(true)
        errorDetail.developerMessage = "Rest Handler Not Found (check for valid URI)"

        return ResponseEntity(errorDetail, null, HttpStatus.NOT_FOUND)
    }
}
