package com.example.mwtspring;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class TestRestController {

    List<String> studentList = new ArrayList<>();

    @GetMapping("/list")
    public @ResponseBody List<String> getStudents(){
        return studentList;
    }

    @RequestMapping(value = "/get/{index}", method = RequestMethod.GET)
    public @ResponseBody String getStudentByIndex(@PathVariable int index){
        return studentList.isEmpty() ? "Unfortunately there are no students" :  studentList.get(index);
    }

    @PostMapping("/addStudent")
    @ResponseStatus(HttpStatus.CREATED)
    public String addString(@RequestBody String studentName){
        studentList.add(studentName);
        return studentName;
    }



}
