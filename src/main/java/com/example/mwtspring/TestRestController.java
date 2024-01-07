package com.example.mwtspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @RequestMapping(value = "/get/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> getApartmentById(@PathVariable String name){
        Optional<Apartment> apartmentToGet = apartmentRepository.findById(name);
        Apartment apartment = apartmentToGet.orElse(null);

        if(apartment != null){
            return ResponseEntity.ok(apartment.getApartmentName());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{oldName}/{newName}")
    public ResponseEntity<String> updateApartment(@PathVariable String oldName, @PathVariable String newName){
        Optional<Apartment> apartmentToUpdate = apartmentRepository.findById(oldName);
        Apartment apartment = apartmentToUpdate.orElse(null);

        if(apartment != null){
            int index = apartmentList.indexOf(apartment);

            apartment.setApartmentName(newName);
            apartmentRepository.save(apartment); // Update repository
            if(index != -1){
                apartmentList.get(index).setApartmentName(newName); // Update list
            }

            return ResponseEntity.ok("Apartment " + oldName + " updated to " + newName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/addApartment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createString(@RequestBody String apartmentName) {
        System.out.println("Creating apartment " + apartmentName);
        Apartment apartment = new Apartment();
        apartment.setApartmentName(apartmentName);
        if (apartmentList.contains(apartment)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("String " + apartmentName + " already exists");
        } else {
            apartmentRepository.save(apartment);
            apartmentList.add(apartment);
            return ResponseEntity.status(HttpStatus.CREATED).body("String " + apartmentName + " created successfully");
        }
    }
}
