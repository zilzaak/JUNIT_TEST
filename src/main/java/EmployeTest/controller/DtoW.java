package EmployeTest.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoW {
    private int id1;
    private int id2;
    private String name;
    private LocalDate dt;


}
