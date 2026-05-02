package com.pigmyMobileApp.model;

public class AgentBankDTO {
    private String bankName;
    private String bankCode;
    private Integer agentCode;
    private String name;
    private String password;
    private String status;

    public AgentBankDTO(String bankName, String bankCode, Integer agentCode, String name,String password,String status) {
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.agentCode = agentCode;
        this.name = name;
        this.password = password;
        this.status = status;
    }

    // Getters
    public String getBankName() { return bankName; }
    public String getBankCode() { return bankCode; }
    public Integer getAgentCode() { return agentCode; }
    public String getname() { return name; }
    public String getPassword() { return password; }
    public String getstatus() { return status; }

    // Setters (optional if you only need read-only DTO)
    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setBankCode(String bankCode) { this.bankCode = bankCode; }
    public void setAgentCode(Integer agentCode) { this.agentCode = agentCode; }
    public void setAgentName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setstatus(String status) { this.status = status; }
}