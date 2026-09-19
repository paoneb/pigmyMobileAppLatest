package com.pigmyMobileApp.model.peocit;

public class SchemesDTO {
    private String schemeID;
    private String schemeName;

    public SchemesDTO(String schemeID, String schemeName) {
        this.schemeID = schemeID;
        this.schemeName = schemeName;
    }

    public String getSchemeID() {
        return schemeID;
    }

    public void setSchemeID(String schemeID) {
        this.schemeID = schemeID;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
}
