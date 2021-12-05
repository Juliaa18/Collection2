package ru.vsu.csv.read;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Division {

    private Integer id;
    private String name;
}
