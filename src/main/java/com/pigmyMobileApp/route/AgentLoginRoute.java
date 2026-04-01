package com.pigmyMobileApp.route;

import com.pigmyMobileApp.config.JwtUtil;
import com.pigmyMobileApp.model.AgentLoginRequest;
import com.pigmyMobileApp.model.AgentLoginResponse;
import com.pigmyMobileApp.serviceactivators.AgentLoginService;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AgentLoginRoute extends RouteBuilder {

    @Autowired
    private AgentLoginService agentLoginService;

    @Autowired
    private JwtUtil jwtUtil;


    private final Logger LOGGER= LoggerFactory.getLogger(AgentLoginService.class);

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .log(LoggingLevel.ERROR, "An error occurred while logging - ${exception.message}")
                .logStackTrace(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .setBody(simple("${exception.message}"));

        from("direct:agentLogin")
                .routeId(AgentLoginRoute.class.getSimpleName())
                .log(LoggingLevel.INFO,"agent login request: ${body}")
                .process(exchange -> {
                    AgentLoginRequest agentLoginRequest = exchange.getIn().getBody(AgentLoginRequest.class);

                    if (agentLoginService.validate(agentLoginRequest.getMobileNumber(),agentLoginRequest.getPassword(),exchange)) {
                        String token = jwtUtil.generateToken(agentLoginRequest.getMobileNumber());
                        exchange.getMessage().setBody(new AgentLoginResponse(exchange.getProperty("agentName",String.class),exchange.getProperty("agentCode",Integer.class),exchange.getProperty("bankCode",String.class),token));
                        LOGGER.info("Agent logged in",exchange.getProperty("agentName",String.class));
                    } else {
                        exchange.getMessage().setHeader("CamelHttpResponseCode", 401);
                        exchange.getMessage().setBody("Invalid credentials");
                    }
                });

    }
}
