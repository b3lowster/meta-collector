package com.cubodrom.metacollector.service;

import com.cubodrom.metacollector.model.MetaFileField;
import com.cubodrom.metacollector.repository.MetaFileFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescribeService implements ActionService {

    private MetaFileFieldRepository metaFileFieldRepository;

    @Autowired
    public DescribeService(MetaFileFieldRepository metaFileFieldRepository) {
        this.metaFileFieldRepository = metaFileFieldRepository;
    }

    @Override
    public List<MetaFileField> act(String filePath) {
        return metaFileFieldRepository.findByFilePath(filePath);
    }
}
