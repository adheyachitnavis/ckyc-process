package com.docuscan.ckyc.service;

import com.docuscan.ckyc.exception.CsvProcessingException;
import com.docuscan.ckyc.model.Customer;
import com.docuscan.ckyc.model.search.SearchInputBatch;
import com.docuscan.ckyc.util.CsvSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvProcessingService {
    private static final String BASE_PATH = "/home/a/Documents/SFTP/2025-03-03";
    private static final String INPUT_CSV_PATH = BASE_PATH + "/Input/IHFL.csv";


    public List<Customer> readInputCsvFile(String filePath) throws CsvProcessingException {
        List<Customer> customerList = new ArrayList<>();
        try {
            CsvMapper csvMapper = new CsvMapper();
            csvMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator('|');
            MappingIterator<Customer> iterator = csvMapper.readerFor(Customer.class).with(schema).readValues(new File(filePath));
            while (iterator.hasNext()) {
                customerList.add(iterator.next());
            }
        } catch (IOException e) {
            throw new CsvProcessingException("Error reading CSV file: " + filePath, e);
        }
        return customerList;
    }

    public String writeSearchBatchFile(SearchInputBatch batch, String file) throws CsvProcessingException {
        try {
            return CsvSerializer.serializeToCsv(batch, file);
        } catch (IOException e) {
            throw new CsvProcessingException("Error reading CSV file: " + file, e);
        }
    }

    public void readSearchResponse(String path) throws CsvProcessingException {
        try {
            Files.list(Paths.get(path))
                    .filter(file -> file.toString().endsWith(".csv"));
        } catch (IOException e) {
            throw new CsvProcessingException("Error reading CSV file: " + INPUT_CSV_PATH, e);
        }
    }
}



