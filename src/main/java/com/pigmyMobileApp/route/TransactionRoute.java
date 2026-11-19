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
                .handled(true)
                .log(LoggingLevel.ERROR, "An error occurred while transaction route - ${exception.message}")
                .logStackTrace(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .setBody(simple("{\"error\":\"${exception.message}\"}"));

        from("direct:addTransaction")
                .routeId("addTransactionRouteId")
                .log(LoggingLevel.INFO, "Add banksoft transaction request: ${body}")
                .bean("transactionService", "addTransaction");

        from("direct:addTransactionPeocit")
                .routeId("addTransactionPeocitRouteId")
                .log(LoggingLevel.INFO, "Add transaction peocit request: ${body}")
                .bean("transactionService", "addTransactionPeocit");

        from("direct:addTransactionSledger")
                .routeId("addTransactionSledgerRouteId")
                .log(LoggingLevel.INFO, "Add transaction sledger request: ${body}")
                .bean("transactionService", "addTransactionSledger");

        from("direct:fetchTransaction")
                .routeId("fetchTransactionRouteId")
                .log(LoggingLevel.INFO,"fetch transaction request: ${body}")
                .bean("transactionService","fetchTransaction");

        from("direct:fetchCustomers")
                .routeId("fetchCustomersRouteId")
                .choice()
                .when(header("bankType").isEqualTo("banksoft"))
                .log(LoggingLevel.INFO,"fetch banksoft User request: ${body}")
                .bean("addUserService","fetchCustomers")
                .when(header("bankType").isEqualTo("peocit"))
                .log(LoggingLevel.INFO,"fetch peocit User request: ${body}")
                .bean("addUserService","fetchCustomersPeocit")
                .when(header("bankType").isEqualTo("sledger"))
                .log(LoggingLevel.INFO,"fetch sledger User request: ${body}")
                .bean("addUserService","fetchCustomersSledger");

        from("direct:fetchCollections")
                .routeId("fetchCollectionRouteId")
                .log(LoggingLevel.INFO,"fetch banksoft Collection request: ${body}")
                .bean("transactionService","fetchCollection");


    }
}