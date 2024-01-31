package com.javatech.StudentCrudExceptionHandling.controller;

import com.javatech.StudentCrudExceptionHandling.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("api")
public class StudentRestController {

    private List<Student> studentList;

    @PostConstruct
    public void loadData(){
        studentList = new ArrayList<>();
        studentList.add(new Student("seema","hebballi"));
        studentList.add(new Student("boya","applebum"));
        studentList.add(new Student("lara","sanon"));
        studentList.add(new Student("sita","roshan"));

    }


    @GetMapping("students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        if (studentId >= studentList.size() || studentId < 0) {
            throw new StudentNotFoundException("Student id not found " + studentId);
        }
        return studentList.get(studentId);
    }
        //add an exception handler using @Exception handler

        @ExceptionHandler
        public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc){
            //create studenterror resposne object
            StudentErrorResponse error = new StudentErrorResponse();
            error.setStatus(HttpStatus.NOT_FOUND.value());
            error.setMessage(exc.getMessage());
            error.setTimeStamp(System.currentTimeMillis());

            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler
        public ResponseEntity<StudentErrorResponse> handleException(Exception exc){
            StudentErrorResponse error = new StudentErrorResponse();
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            error.setMessage(exc.getMessage());
            error.setTimeStamp(System.currentTimeMillis());
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

        }
    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentList;
    }


}
