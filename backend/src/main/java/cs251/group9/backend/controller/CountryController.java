package cs251.group9.backend.controller;

import cs251.group9.backend.entity.Country;
import cs251.group9.backend.service.CountryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    
    @Autowired
    private CountryService countryService;
    
    // Get all countries
    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }
    
    // Get country by code
    @GetMapping("/{code}")
    public ResponseEntity<Country> getCountryByCode(@PathVariable String code) {
        return countryService.getCountryByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Search countries by name
    @GetMapping("/search")
    public List<Country> searchCountries(@RequestParam String name) {
        return countryService.findCountriesByName(name);
    }
    
    // Get countries by region
    @GetMapping("/region/{region}")
    public List<Country> getCountriesByRegion(@PathVariable String region) {
        return countryService.findCountriesByRegion(region);
    }
    
    // Add new country
    @PostMapping
    public Country addCountry(@RequestBody Country country) {
        return countryService.saveCountry(country);
    }
    
    // Update country
    @PutMapping("/{code}")
    public ResponseEntity<Country> updateCountry(@PathVariable String code, @RequestBody Country country) {
        return countryService.getCountryByCode(code)
                .map(existingCountry -> {
                    country.setCountryCode(code);
                    return ResponseEntity.ok(countryService.saveCountry(country));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Delete country
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCountry(@PathVariable String code) {
        if (countryService.getCountryByCode(code).isPresent()) {
            countryService.deleteCountry(code);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
