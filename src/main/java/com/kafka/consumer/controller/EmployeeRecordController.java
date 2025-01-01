package com.kafka.consumer.controller;


import com.kafka.consumer.model.Employee;
import com.kafka.consumer.service.EmployeeService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/save")
@AllArgsConstructor
public class EmployeeRecordController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Employee data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> saveEmployee(@Valid @RequestBody Employee employee) {
        try {
            // Validate the employee object if necessary
            if (employee.getName() == null || employee.getName().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Employee name is required.");
            }

            // Call the EmployeeService to save the record
            employeeService.saveEmployeeRecord(employee);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Employee saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while saving the employee: " + e.getMessage());
        }
    }
}