package com.pigmyMobileApp.route;

import com.pigmyMobileApp.model.AgentLoginRequest;
import com.pigmyMobileApp.model.TransactionRequest;
import com.pigmyMobileApp.model.UserData;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PigmyMobileAppRoute extends RouteBuilder {
    @Value("${rest.api.base.url}")
    private String restApiBaseUrl;

    @Value("${agent.login.path}")
    private String agentLoginPath;

    @Value("${transaction.resource.path}")
    private String transactionPath;

    @Value("${user.resource.path}")
    private String userPath;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.auto)
                .contextPath(restApiBaseUrl)
                .apiProperty("cors", "true");

        rest(agentLoginPath)
                .consumes("application/json").produces("application/json")
                .post()
                .type(AgentLoginRequest.class)
                .to("direct:agentLogin");

        rest(transactionPath)
                .consumes("application/json").produces("application/json")
                .post()
                .type(TransactionRequest.class)
                .to("direct:addTransaction")

                .get()
                .description("fetch transaction details based on agentCode")
                .param().name("agentCode").type(RestParamType.query).dataType("Integer").required(true).endParam()
                .param().name("date").type(RestParamType.query).dataType("LocaleDate").required(true).endParam()
                .param().name("bankCode").type(RestParamType.query).dataType("String").required(true).endParam()
                .to("direct:fetchTransaction")

                .delete()
                .param().name("transactionId").type(RestParamType.query).dataType("Long").required(true).endParam()
                .description("delete transaction details based on agentCode")
                .to("direct:deleteTransaction");

        rest(userPath)
                .get()
                .param().name("agentCode").type(RestParamType.query).dataType("Integer").required(false).endParam()
                .param().name("bankCode").type(RestParamType.query).dataType("String").required(false).endParam()
                .type(UserData.class)
                .to("direct:fetchCustomers");


    }
}
