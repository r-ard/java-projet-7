package com.nnk.springboot.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class CustomErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @GetMapping("/error")
    public void handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            logger.error("Handling error with status code: {}", statusCode);

            if (statusCode == 403) {
                logger.warn("Forbidden error encountered: {}", "Vous n'êtes pas autorisé à consulter cette page");
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "Vous n'êtes pas autorisé à consulter cette page");
            } else if (statusCode == 404) {
                logger.warn("Not Found error encountered: {}", "La page que vous cherchez n'existe pas");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La page que vous cherchez n'existe pas");
            } else {
                logger.error("Internal Server Error encountered: {}", "Une erreur interne est survenue");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue");
            }
        }
    }
}
