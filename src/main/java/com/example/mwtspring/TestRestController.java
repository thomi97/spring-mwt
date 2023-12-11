package com.example.mwtspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apartments")
public class TestRestController {

    @Autowired
    ApartmentRepository apartmentRepository;
    List<Apartment> apartmentList = new ArrayList<>();

    @GetMapping("/list")
    public ResponseEntity<List<String>> getApartments(){
        List<Apartment> apartmentList = apartmentRepository.findAll();
        List<String> returnList;

        if(apartmentList.isEmpty())
            return ResponseEntity.notFound().build();
        else
            returnList = apartmentList.stream()
                    .map(Apartment::getApartmentName)
                    .toList();

        return ResponseEntity.ok(returnList);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public @ResponseBody String getApartmentById(@PathVariable int id){
        return apartmentList.isEmpty() ? "Unfortunately there are no students" :  apartmentList.get(id).getApartmentName();
    }



    @PostMapping("/addApartment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createString(@RequestBody String apartmentName) {
        Apartment apartment = new Apartment();
        apartment.setApartmentName(apartmentName);
        apartment.setId((long) apartmentList.size());
        //needs to be adjusted
        if (apartmentList.contains(apartment)) {
            // If the string already exists, return a conflict response (HTTP 409).
            return ResponseEntity.status(HttpStatus.CONFLICT).body("String " + apartmentName + " already exists");
        } else {
            // Add the new string and return a created response (HTTP 201).
            apartmentRepository.save(apartment);
            apartmentList.add(apartment);
            System.out.println(apartmentList);
            return ResponseEntity.status(HttpStatus.CREATED).body("String " + apartmentName + " created successfully");
        }
    }
}
