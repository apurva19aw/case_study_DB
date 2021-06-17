package com.example.case_study.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BahnController {

    @RequestMapping("/betriebsstelle/{name}")
    public ResponseEntity<Object> getData(@PathVariable String name) throws IOException {

        if (searchCsvLine(0,name.toUpperCase()) != null) {
            String[] resultArray = searchCsvLine(0,name.toUpperCase()).split(";");
            Map<String, String> entity = new LinkedHashMap<String, String>();
            entity.put("Name", resultArray[1]);
            entity.put("Kurz_name", resultArray[2]);
            entity.put("Type", resultArray[3]);
            return new ResponseEntity<Object>(entity, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Object>("No Data Found !!!", HttpStatus.OK);
        }
    }


    public String searchCsvLine(int searchColumnIndex, String searchString) throws IOException {
        String resultRow = null;
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Sample.csv"));
        String line;
        while ( (line = br.readLine()) != null ) {
            String[] values = line.split(";");
            if(values[searchColumnIndex].equals(searchString)) {
                resultRow = line;
                break;
            }
        }
        br.close();
        return resultRow;
    }
}
