package com.cubodrom.metacollector.service;

import com.cubodrom.metacollector.collectors.JsonMetaCollector;
import com.cubodrom.metacollector.collectors.MetaCollector;
import com.cubodrom.metacollector.exception.InvalidFileException;
import com.cubodrom.metacollector.exception.UnsupportedFileExtensionException;
import com.cubodrom.metacollector.model.MetaFileField;
import com.cubodrom.metacollector.repository.MetaFileFieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class CrawlService implements ActionService {

    private static Logger LOG = LoggerFactory.getLogger(JsonMetaCollector.class);

    private MetaCollector csvMetaCollector;

    private MetaCollector jsonMetaCollector;

    private MetaFileFieldRepository metaFileFieldRepository;

    private static final String CSV_EXTENTION = ".csv";
    private static final String JSON_EXTENTION = ".json";

    @Autowired
    public CrawlService(MetaCollector csvMetaCollector, MetaCollector jsonMetaCollector, MetaFileFieldRepository metaFileFieldRepository) {
        this.csvMetaCollector = csvMetaCollector;
        this.jsonMetaCollector = jsonMetaCollector;
        this.metaFileFieldRepository = metaFileFieldRepository;
    }

    @Override
    @Transactional
    public List<MetaFileField> act(String filePath) {
        try {
            List<MetaFileField> metaFileFields = null;
            if (filePath.endsWith(CSV_EXTENTION)) {
                metaFileFields = csvMetaCollector.collect(new FileInputStream(filePath), filePath);
            } else if (filePath.endsWith(JSON_EXTENTION)) {
                metaFileFields = jsonMetaCollector.collect(new FileInputStream(filePath), filePath);
            } else {
                LOG.error(String.format("File extention is not supported. Actual is %s expected are %s", filePath, String.join(",",CSV_EXTENTION, JSON_EXTENTION)));
                throw new UnsupportedFileExtensionException(String.format("File extention is not supported. Actual is %s expected are %s", filePath, String.join(",",CSV_EXTENTION, JSON_EXTENTION)));
            }
            metaFileFieldRepository.deleteByFilePath(filePath);
            return metaFileFieldRepository.saveAll(metaFileFields);
        } catch (FileNotFoundException e) {
            LOG.error(String.format(String.format("File by file path has not been found, %s", filePath), e));
            throw new InvalidFileException(String.format("File by file path has not been found, %s", filePath), e);
        }
    }
}
