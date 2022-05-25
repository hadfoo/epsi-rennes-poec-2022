package fr.epsi.rennes.poec.manucre.controller;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.epsi.rennes.poec.manucre.domain.Response;
import fr.epsi.rennes.poec.manucre.exception.BusinessException;

@ControllerAdvice
public class ExceptionController {
	
	private static final Logger logger = LogManager.getLogger(ExceptionController.class);
	
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public Response<String> onBusinessException(BusinessException e){
		Response<String> response = new Response<>();
		response.setSuccess(false);
		response.setData(e.getCode());
		
		return response;
	}
	
	@ExceptionHandler
	@ResponseBody
	public Response<Void> onTechnicalException(Exception e){
		logger.error(e.getMessage(), e);
		
		Response<Void> response = new Response<>();
		response.setSuccess(false);
		
		return response;
	}

}
