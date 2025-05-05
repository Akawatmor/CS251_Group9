package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "country")
public class Country {
    
    @Id
    @Column(length = 3)
    private String countryCode;
    
    @Column(nullable = false)
    private String countryName;
    
    private String region;
    
    // Getters and setters
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
}
