package com.pigmyMobileApp.route;

import com.pigmyMobileApp.config.JwtUtil;
import com.pigmyMobileApp.model.AgentLoginRequest;
import com.pigmyMobileApp.model.AgentLoginResponse;
import com.pigmyMobileApp.model.RefreshToken;
import com.pigmyMobileApp.repository.RefreshTokenRepo;
import com.pigmyMobileApp.serviceactivators.AgentLoginService;
import com.pigmyMobileApp.serviceactivators.RefreshTokenService;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;


@Component
public class AgentLoginRoute extends RouteBuilder {

    @Autowired
    private AgentLoginService agentLoginService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;


    private final Logger LOGGER= LoggerFactory.getLogger(AgentLoginService.class);

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "An error occurred while logging - ${exception.message}")
                .logStackTrace(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .setBody(simple("{\"error\":\"${exception.message}\"}"));

        from("direct:agentLogin")
                .routeId(AgentLoginRoute.class.getSimpleName())
                .log(LoggingLevel.INFO,"agent login request: ${body}")
                .process(exchange -> {
                    AgentLoginRequest agentLoginRequest = exchange.getIn().getBody(AgentLoginRequest.class);

                    if (agentLoginService.validate(agentLoginRequest.getMobileNumber(),agentLoginRequest.getPassword(),exchange)) {
                        String refreshToken = refreshTokenService.generateRefreshToken(exchange);
                        String accessToken = jwtUtil.generateToken(agentLoginRequest.getMobileNumber());
                        exchange.getMessage().setBody(new AgentLoginResponse(exchange.getProperty("agentName",String.class),exchange.getProperty("agentCode",Integer.class),exchange.getProperty("bankCode",String.class),exchange.getProperty("bankName",String.class),exchange.getProperty("phoneNumber",String.class),exchange.getProperty("lastDepositDate",String.class),exchange.getProperty("limitAmount",Long.class),exchange.getProperty("graceDays",Integer.class),refreshToken,accessToken));
                        LOGGER.info("Agent logged in",exchange.getProperty("agentName",String.class));
                    } else {
                        exchange.getMessage().setHeader("CamelHttpResponseCode", 401);
                        exchange.getMessage().setBody("Invalid credentials/Agent not found/Agent is not active");
                    }
                });

        from("direct:authenticateAgent")
                .routeId("authenticateAgentRouteId")
             .bean("agentLoginService", "authenticateAgent");

        from("direct:refreshToken")
                .routeId("refreshTokenRouteId")
                .process(exchange -> {

                    Map<String, Object> bodyMap = exchange.getIn().getBody(Map.class);

                    // 2. Fetch your specific key from the map
                    String refreshToken = (String) bodyMap.get("refreshToken");
                    //String refreshToken = exchange.getIn().getHeader("refreshToken", String.class);
                    System.out.println("refreshToken: " + refreshToken);
                    String mobileNumber = (String) bodyMap.get("mobileNumber");
                    System.out.println("mobileNumber: " + mobileNumber);
                   // String mobileNumber = exchange.getIn().getHeader("mobileNumber", String.class);

                    if (refreshTokenService.validateRefreshToken(refreshToken, mobileNumber)) {
                        String newAccessToken = jwtUtil.generateToken(mobileNumber);
                       // exchange.getMessage().setBody(newAccessToken);
                        exchange.getIn().setBody(Map.of(
                                "accessToken", newAccessToken,
                                "refreshToken", refreshToken));
                    } else {

                        refreshTokenService.reGenerateRefreshToken(refreshToken,mobileNumber,exchange);
                    }
                });

    }
}
