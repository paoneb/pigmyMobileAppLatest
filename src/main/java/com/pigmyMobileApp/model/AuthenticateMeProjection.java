package com.pigmyMobileApp.model;

import java.time.LocalDate;

public interface AuthenticateMeProjection {

    Long getlimitAmount();
    int getGraceDays();
    LocalDate getLastDepositDate();
    String getRefreshToken();
    boolean isAgentRevoked();
}
