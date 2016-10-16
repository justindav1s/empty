package com.ba.captwo.eda.demo.clients;

import com.ba.captwo.eda.demo.coreservices.FlightService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * Created by justin on 16/10/2016.
 */
public class PassThroughErrorHandler implements ResponseErrorHandler {


    private final Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Override
    public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {

        if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
            logger.debug(HttpStatus.FORBIDDEN + " response. Throwing authentication exception");
        }
    }

    @Override
    public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {

        if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
            logger.debug("Status code: " + clienthttpresponse.getStatusCode());
            logger.debug("Response" + clienthttpresponse.getStatusText());

            if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
                logger.debug("Call returned a error 403 forbidden resposne ");
                return true;
            }
        }
        return false;
    }

}