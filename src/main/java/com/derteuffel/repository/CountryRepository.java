package com.derteuffel.repository;

import com.derteuffel.data.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by derteuffel on 02/12/2018.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {

    Country findByCountryName(String countryName);
}
