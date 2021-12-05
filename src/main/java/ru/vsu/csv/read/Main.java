package ru.vsu.csv.read;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String DATA_PATH = "foreign_names.csv";
    private static final char SEPARATOR = ';';
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final Map<String, Integer> DIVISION_NAME_ID_MAP = new HashMap<>();

    private static final Integer ID_INDEX = 0,
            NAME_INDEX = 1,
            GENDER_INDEX = 2,
            BIRTHDAY_INDEX = 3,
            DIVISION_INDEX = 4,
            SALARY_INDEX = 5;

    public static void main(String[] args) throws Exception {

        List<Person> persons = new ArrayList<>();

        try (CSVReader reader =  new CSVReaderBuilder(getResourceReader(DATA_PATH))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(SEPARATOR)
                        .build())
                .build()) {
            reader.skip(1);

            String[] line;
            while ((line = reader.readNext()) != null) {
                persons.add(rowToPerson(line));
            }
        }

        for (int i = 0; i < persons.size(); i++) {
            if (i % 100 == 0) {
                System.out.println(persons.get(i));
            }
        }
    }

    private static Person rowToPerson(String[] row) {
        String divisionName = row[DIVISION_INDEX];
        return Person.builder()
                .id(Integer.parseInt(row[ID_INDEX]))
                .name(row[NAME_INDEX])
                .gender(row[GENDER_INDEX])
                .division(Division.builder()
                        .id(generateDivisionId(divisionName))
                        .name(divisionName)
                        .build())
                .salary(Integer.parseInt(row[SALARY_INDEX]))
                .birthday(LocalDate.parse(row[BIRTHDAY_INDEX], DATE_FORMATTER))
                .build();
    }

    private static Integer generateDivisionId(String name) {
        Integer id = DIVISION_NAME_ID_MAP.get(name);
        if (id == null) {
            id = DIVISION_NAME_ID_MAP.size() + 1;
            DIVISION_NAME_ID_MAP.put(name, id);
        }
        return id;
    }

    private static Reader getResourceReader(String path) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new RuntimeException("Не удалось прочитать файл");
        }
        return new InputStreamReader(inputStream);
    }
}
