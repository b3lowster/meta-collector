package com.cubodrom.metacollector.collectors;

import com.cubodrom.metacollector.model.MetaFileField;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface MetaCollector {

    public List<MetaFileField> collect(InputStream file, String filePath);
}