package com.cubodrom.metacollector.collectors;

import com.cubodrom.metacollector.MetaCollectorApplication;
import com.cubodrom.metacollector.exception.FileParseException;
import com.cubodrom.metacollector.model.MetaFileField;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvMetaCollector implements MetaCollector {

    private static Logger LOG = LoggerFactory.getLogger(CsvMetaCollector.class);

    private static final String REGEX_IS_DIGITS = "\\d+";

    @Override
    public List<MetaFileField> collect(InputStream file, String filePath) {
        try {
            MetaFileField[] metaFileFields;
            try (CSVParser csvParser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.DEFAULT)) {
                boolean isHeaderLine = true;
                metaFileFields = null;
                for (CSVRecord csvRecord : csvParser) {
                    if (isHeaderLine) {
                        metaFileFields = new MetaFileField[csvRecord.size()];
                        for (int i = 0; i < csvRecord.size(); i++) {
                            metaFileFields[i] = new MetaFileField(filePath, csvRecord.get(i), null, 0, 0);
                        }
                        isHeaderLine = false;
                    } else {
                        for (int i = 0; i < csvRecord.size(); i++) {
                            MetaFileField metaFileField = metaFileFields[i];
                            String value = csvRecord.get(i);
                            if ("null".equals(value)) {
                                metaFileField.setNullValueAmount(metaFileField.getNullValueAmount() + 1);
                            } else {
                                if (metaFileField.getFieldType() == null) {
                                    if (value.matches(REGEX_IS_DIGITS)) {
                                        metaFileField.setFieldType(Integer.class.getSimpleName());
                                    } else {
                                        metaFileField.setFieldType(String.class.getSimpleName());
                                    }
                                }
                                metaFileField.setNonNullValueAmount(metaFileField.getNonNullValueAmount() + 1);
                            }
                        }
                    }
                }
            }
            return Arrays.asList(metaFileFields);
        } catch (IOException e) {
            LOG.error(String.format("Error while parse csv file %s", filePath), e);
            throw new FileParseException(String.format("Error while parse csv file %s", filePath), e);
        }
    }
}
