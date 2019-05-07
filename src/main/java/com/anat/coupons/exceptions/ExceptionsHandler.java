package com.anat.coupons.exceptions;


import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.anat.coupons.beans.ErrorBean;

	@ResponseBody
	@ControllerAdvice
	public class ExceptionsHandler extends ResponseEntityExceptionHandler { //implements ExceptionMapper<Throwable>{ 
	
		@ExceptionHandler (Throwable.class)//, Throwable.class})
		protected  ResponseEntity<Object> ExceptionHandler (HttpServletResponse response , Throwable exception) {
		
			
			exception.printStackTrace();
			
			if (exception instanceof ApplicationException) {
				
				ApplicationException appException = (ApplicationException) exception;
				String errorMessage = appException.getErrorType().getErrorDefinition();
				String internalMessage = exception.getMessage();
				int errorCode = appException.getErrorType().getErrorCode();
				
				System.out.println(errorCode +" "+errorMessage+"\n"+internalMessage);
				
				ErrorBean errorBean = new ErrorBean(errorCode, internalMessage, errorMessage);

				return new ResponseEntity<Object>(errorBean ,HttpStatus.BAD_REQUEST);//BAD_REQUEST);// HttpStatus.BAD_REQUEST);
				
			}
			else {
	
			//here we handle an exception that we didn't catch and wrapped 
				String internalMessage = exception.getMessage();
				
				ErrorBean errorBean = new ErrorBean(601, internalMessage, "GENERAL_ERROR");

				
				return new ResponseEntity<Object>(errorBean, HttpStatus.BAD_REQUEST);
			}
		}
	
		
		
		
//		@ExceptionHandler ({ApplicationException.class, Throwable.class})
//		public Throwable ExceptionHandling (HttpServletResponse response , Throwable exception) {
//		
//			
//			exception.printStackTrace();
//			if (exception instanceof ApplicationException) {
//				ApplicationException appException = (ApplicationException) exception;
//				String errorMessage = appException.getErrorType().getErrorDefinition();
//				String internalMessage = exception.getMessage();
//				int errorCode = appException.getErrorType().getErrorCode();
//				System.out.println(errorCode+" " + errorMessage+"\n"+internalMessage);
//				response.setStatus(errorCode);
//				response.setHeader("exception", internalMessage);
////				response.setHeader(errorMessage, internalMessage);
//				return appException;
//				
//			}
//			else {
//	
//			//here we handle an exception that we didn't catch and wrapped 
//				String internalMessage = exception.getMessage();
//				response.setStatus(601);
//				response.setHeader("exception", internalMessage);
////				response.setHeader("general exception", internalMessage);
//				return exception;
//			}
//		}
}	
		
		