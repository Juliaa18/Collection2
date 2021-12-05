package ru.vsu.csv.read;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Builder
@ToString
public class Person {

    private Integer id;
    private String name;
    private String gender;
    private LocalDate birthday;
    private Division division;
    private Integer salary;
}
