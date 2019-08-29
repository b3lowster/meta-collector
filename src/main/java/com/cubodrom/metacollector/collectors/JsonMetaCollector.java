package com.cubodrom.metacollector.collectors;

import com.cubodrom.metacollector.exception.FileParseException;
import com.cubodrom.metacollector.model.MetaFileField;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JsonMetaCollector implements MetaCollector {

    private static Logger LOG = LoggerFactory.getLogger(JsonMetaCollector.class);

    @Override
    public List<MetaFileField> collect(InputStream file, String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> json = objectMapper.readValue(file, new TypeReference<List<Map<String,Object>>>(){});
            Map<String, MetaFileField> mapMetaFileField = new HashMap<>();
            for (Map<String, Object> jsonObject : json) {
                for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    MetaFileField metaFileField = mapMetaFileField.get(entry.getKey());
                    if (metaFileField == null) {
                        String metaFileFieldType = entry.getValue() == null ? null : entry.getValue().getClass().getSimpleName();
                        MetaFileField newMetaFileField = new MetaFileField(filePath, entry.getKey(), metaFileFieldType,
                                entry.getKey() == null ? 1 : 0,
                                entry.getKey() == null ? 0 : 1);
                        mapMetaFileField.put(entry.getKey(), newMetaFileField);
                    } else {
                        if (entry.getValue() == null) {
                            metaFileField.setNullValueAmount(metaFileField.getNullValueAmount() + 1);
                        } else {
                            metaFileField.setNonNullValueAmount(metaFileField.getNonNullValueAmount() + 1);
                        }
                        if (metaFileField.getFieldType() == null && entry.getValue() != null) {
                            metaFileField.setFieldType(entry.getValue().getClass().getSimpleName());
                        }
                    }
                }
            }
            return mapMetaFileField.values().stream().collect(Collectors.toList());
        } catch (IOException e) {
            LOG.error(String.format("Error while parse csv file %s", filePath), e);
            throw new FileParseException(String.format("Error while parse json file %s", filePath), e);
        }
    }
}
