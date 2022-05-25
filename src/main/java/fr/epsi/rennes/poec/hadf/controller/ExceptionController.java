package fr.epsi.rennes.poec.hadf.controller;

import fr.epsi.rennes.poec.hadf.domain.Response;
import fr.epsi.rennes.poec.hadf.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class ExceptionController{

    private static final Logger logger = LogManager.getLogger(ExceptionController.class);

    @ExceptionHandler(BusinessException.class)
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
