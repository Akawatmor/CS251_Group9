package cs251.group9.backend.service;

import cs251.group9.backend.entity.Country;
import cs251.group9.backend.repository.CountryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Service
public class CountryService {
    
    @Autowired
    private CountryRepository countryRepo;
    
    @PostConstruct
    public void initializeCountries() {
        // Only initialize if database is empty
        if (countryRepo.count() == 0) {
            createInitialCountries();
        }
    }
    
    @Transactional
    private void createInitialCountries() {
        // Sample of major countries with ISO numeric codes
        List<Object[]> countries = Arrays.asList(
            new Object[]{"840", "United States", "North America"},
            new Object[]{"826", "United Kingdom", "Europe"},
            new Object[]{"392", "Japan", "Asia"},
            new Object[]{"250", "France", "Europe"},
            new Object[]{"276", "Germany", "Europe"},
            new Object[]{"356", "India", "Asia"},
            new Object[]{"156", "China", "Asia"},
            new Object[]{"124", "Canada", "North America"},
            new Object[]{"036", "Australia", "Oceania"},
            new Object[]{"076", "Brazil", "South America"},
            new Object[]{"643", "Russia", "Europe/Asia"},
            new Object[]{"764", "Thailand", "Asia"},
            new Object[]{"360", "Indonesia", "Asia"}
            // Add more countries as needed
        );
        
        for (Object[] countryData : countries) {
            Country country = new Country();
            country.setCountryCode((String)countryData[0]);
            country.setCountryName((String)countryData[1]);
            country.setRegion((String)countryData[2]);
            countryRepo.save(country);
        }
    }
    
    public List<Country> getAllCountries() {
        return countryRepo.findAll();
    }
    
    public Optional<Country> getCountryByCode(String code) {
        return countryRepo.findById(code);
    }
    
    public List<Country> findCountriesByName(String name) {
        return countryRepo.findByCountryNameContaining(name);
    }
    
    public List<Country> findCountriesByRegion(String region) {
        return countryRepo.findByRegion(region);
    }
    
    @Transactional
    public Country saveCountry(Country country) {
        return countryRepo.save(country);
    }
    
    @Transactional
    public void deleteCountry(String code) {
        countryRepo.deleteById(code);
    }
}
