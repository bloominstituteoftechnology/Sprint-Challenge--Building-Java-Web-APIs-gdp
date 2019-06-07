package com.jakeesveld.gdp

import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.sql.Timestamp
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class RestExceptionHandler: ResponseEntityExceptionHandler(){


    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(rnfe: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<*> {
        val errorDetail = ErrorDetail("Resource not Found",
                HttpStatus.NOT_FOUND.value(),
                rnfe.message.toString(),
                Timestamp(System.currentTimeMillis()),
                rnfe.javaClass.name)


        return ResponseEntity<Any>(errorDetail, null, HttpStatus.NOT_FOUND)
    }

    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(ex.propertyName.toString(),
                HttpStatus.BAD_REQUEST.value(),
                ex.message.toString(),
                Timestamp(System.currentTimeMillis()),
                request.getDescription(true))

        return ResponseEntity(errorDetail, null, HttpStatus.BAD_REQUEST)
    }


    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val errorDetail = ErrorDetail(ex.requestURL,
                HttpStatus.NOT_FOUND.value(),
                request.getDescription(true),
                Timestamp(System.currentTimeMillis()),
                "Handler not found. Check valid URI")

        return ResponseEntity(errorDetail, null, HttpStatus.NOT_FOUND)
    }


}