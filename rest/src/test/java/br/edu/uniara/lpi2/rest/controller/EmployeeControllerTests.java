package br.edu.uniara.lpi2.rest.controller;

import br.edu.uniara.lpi2.rest.model.Employee;
import br.edu.uniara.lpi2.rest.model.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeController controller;

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = controller.one(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetAllEmployees() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = controller.all(1, 10);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testInsertEmployee() {
        Employee employee = new Employee();
        when(repository.save(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = controller.insert(employee);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    void testDeleteEmployee() {
        when(repository.existsById(1L)).thenReturn(true);

        ResponseEntity<?> response = controller.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1 was removed", response.getBody());
    }
}