/*
 *
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/1/2017
 * Author: Loi Tran <email:loi.tran@mediastep.com>
 *
 */

package com.mediastep.beecow.common.errors;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mediastep.beecow.common.config.BeecowConfig;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
public class CommonExceptionTranslator implements InitializingBean {

    private static final String ERR_VALIDATION = "error.validation";

    private static final String ERR_PARAM_NOT_FOUND = "error.paramNotFound";

    private static final String ERR_DATA_CONFLICT = "error.dataConflictError";
    
    private static final String ERR_TRANSACTION = "error.transaction";

    private static final String ERR_INTERNAL_SERVER = "error.internalServerError";

    private boolean debugMode = false;

    @Inject
    private BeecowConfig beecowConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        debugMode = beecowConfig.isDebugMode();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ParameterizedErrorVM processError(UnauthorizedException ex) {
        return ex.getErrorVM();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processError(HttpMessageNotReadableException ex) {
        ErrorVM errorVM = new ErrorVM(ERR_VALIDATION, ex.getMessage());
        return errorVM;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processError(BindException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ParameterizedErrorVM processError(InvalidInputException ex) {
        return ex.getErrorVM();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processError(MissingServletRequestParameterException ex) {
        ErrorVM errorVM = new ErrorVM(ERR_PARAM_NOT_FOUND, ex.getMessage());
        return errorVM;
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorVM processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return processFieldErrors(fieldErrors);
	}
    
    @ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorVM processValidationError(ConstraintViolationException ex) {
		ErrorVM dto = new ErrorVM(ErrorConstants.ERR_VALIDATION);
		for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
			Iterator<Node> iterator = constraintViolation.getPropertyPath().iterator();
			Node lastNode = iterator.next();
			while (iterator.hasNext()) {
				lastNode = iterator.next();
			}
			dto.add(constraintViolation.getInvalidValue().getClass().getSimpleName(), lastNode.toString(),
					constraintViolation.getMessage());
		}
		return dto;
	}

    private ErrorVM processFieldErrors(List<FieldError> fieldErrors) {
        ErrorVM errorVM = new ErrorVM(ERR_VALIDATION);
        for (FieldError fieldError : fieldErrors) {
            errorVM.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }
        return errorVM;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ParameterizedErrorVM processError(EntityNotFoundException ex) {
        return ex.getErrorVM();
    }

    @ExceptionHandler(EntityExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ParameterizedErrorVM processError(EntityExistException ex) {
        return ex.getErrorVM();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorVM processError(DataIntegrityViolationException ex) {
        ErrorVM errorVM;
        if (debugMode) {
            errorVM = new ErrorVM(ERR_DATA_CONFLICT, ExceptionUtils.getStackTrace(ex));
        }
        else {
            errorVM = new ErrorVM(ERR_DATA_CONFLICT, "Data Integrity Violation Exception");
        }
        return errorVM;
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorVM processTransactionError(TransactionSystemException ex) {
        ErrorVM errorVM;
        if (debugMode) {
            errorVM = new ErrorVM(ERR_TRANSACTION, ExceptionUtils.getStackTrace(ex));
        }
        else {
            errorVM = new ErrorVM(ERR_TRANSACTION, "Transaction System Exception");
        }
        return errorVM;
    }

    @ExceptionHandler(BeecowException.class)
    public ResponseEntity<ParameterizedErrorVM> processTransactionError(BeecowException ex) {
        BodyBuilder builder;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
        }
        else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return builder.body(ex.getErrorVM());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorVM> processRuntimeException(Exception ex) {
//        // Build error response based on root cause
//        ResponseEntity<ErrorVM> errorResponse = handlerRootCause(ExceptionUtils.getRootCause(ex));
//        if (errorResponse != null) {
//            return errorResponse;
//        }
//        // Build error response
//        errorResponse = handlerRootCause(ex);
//        if (errorResponse != null) {
//            return errorResponse;
//        }
//        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, ERR_INTERNAL_SERVER, "Internal server error");
//    }

    /**
     * Handle root cause and return error response
     * @param rootCause
     * @return return null if not handled
     */
    private ResponseEntity<ErrorVM> handlerRootCause(Throwable rootCause) {
        if (rootCause != null && rootCause instanceof ConstraintViolationException) {
            return handleError((ConstraintViolationException) rootCause);
        }
        return null;
    }

    /**
     * Handle database validation error and return error response
     * @param ex
     * @return
     */
    public ResponseEntity<ErrorVM> handleError(ConstraintViolationException ex) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, ERR_DATA_CONFLICT, "Data conflict");
    }

    /**
     * Build error response
     * @param ex
     * @param status
     * @param error
     * @param description
     * @return
     */
    public ResponseEntity<ErrorVM> buildErrorResponse(Throwable ex, HttpStatus status, String error, String description) {
        BodyBuilder builder;
        ErrorVM errorVM;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorVM = new ErrorVM("error." + responseStatus.value().value(), responseStatus.reason());
        }
        else if (debugMode) {
            builder = ResponseEntity.status(status);
            errorVM = new ErrorVM(error, ExceptionUtils.getStackTrace(ex));
        }
        else {
            builder = ResponseEntity.status(status);
            errorVM = new ErrorVM(error, description);
        }
        return builder.body(errorVM);
    }
}
