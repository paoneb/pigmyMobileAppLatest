package com.pigmyMobileApp.route;

import com.pigmyMobileApp.serviceactivators.AgentLoginService;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionRoute  extends RouteBuilder {


    private final Logger LOGGER = LoggerFactory.getLogger(AgentLoginService.class);

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .log(LoggingLevel.ERROR, "An error occurred while transaction route - ${exception.message}")
                .logStackTrace(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .setBody(simple("${exception.message}"));

        from("direct:addTransaction")
                .routeId("addTransactionRouteId")
                .log(LoggingLevel.INFO, "Add transaction request: ${body}")
                .bean("transactionService", "addTransaction");

        from("direct:fetchTransaction")
                .routeId("fetchTransactionRouteId")
                .log(LoggingLevel.INFO,"fetch transaction request: ${body}")
                .bean("transactionService","fetchTransaction");

        from("direct:deleteTransaction")
                .routeId("deleteTransactionRouteId")
                .log(LoggingLevel.INFO,"delete User request: ${body}")
                .bean("transactionService","deleteTransaction");


    }
}