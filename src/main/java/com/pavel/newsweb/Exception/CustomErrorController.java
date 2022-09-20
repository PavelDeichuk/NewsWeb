package com.pavel.newsweb.Exception;

import com.pavel.newsweb.Model.Error;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
@Controller
public class CustomErrorController implements ErrorController {
    private static final String ERROR = "/error";

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(ERROR)
    public ResponseEntity<Error> error(WebRequest request) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                request,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE)
        );

        return ResponseEntity
                .status((Integer) attributes.get("status"))
                .body(Error
                        .builder()
                        .error((String) attributes.get("error"))
                        .error_description((String) attributes.get("message"))
                        .build()
                );
    }
}
