package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FirstLevelDivisions {

    private int divisionId; //PK
    private String division;

    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId; //FK

    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId; // ??
    }

    // Getters
    public int getDivisionId() {
        return divisionId;
    }

    public String getDivision() {
        return division;
    }

    // Setters
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public void setDivision(String division) {
       this.division = division;
    }



}
