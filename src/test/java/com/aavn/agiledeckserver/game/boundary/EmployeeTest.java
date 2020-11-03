package com.aavn.agiledeckserver.game.boundary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void setId() {
        Employee employee = new Employee();
        employee.setId(1);
        assertEquals(1, employee.getId());
    }

    @Test
    void getId() {
        Employee employee = new Employee();
        employee.setId(1);
        assertEquals(1, employee.getId());
    }
}