package cs251.group9.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cs251.group9.backend.entity.Country;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
    
    @Query(value = "SELECT * FROM country WHERE countryName LIKE :name", nativeQuery = true)
    List<Country> findByCountryNameContaining(@Param("name") String name);
    
    @Query(value = "SELECT * FROM country WHERE region = :region", nativeQuery = true)
    List<Country> findByRegion(@Param("region") String region);

    @Query("SELECT c FROM Country c WHERE c.countryName LIKE %:keyword%")
    List<Country> searchCountries(@Param("keyword") String keyword);
}
