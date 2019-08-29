package com.cubodrom.metacollector.repository;

import com.cubodrom.metacollector.model.MetaFileField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaFileFieldRepository extends JpaRepository<MetaFileField, String> {

    List<MetaFileField> findByFilePath(String filePath);

    void deleteByFilePath(String filePath);
}
