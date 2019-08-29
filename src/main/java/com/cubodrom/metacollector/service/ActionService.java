package com.cubodrom.metacollector.service;

import com.cubodrom.metacollector.model.MetaFileField;

import java.util.List;

public interface ActionService {

    public List<MetaFileField> act(String filePath);
}
