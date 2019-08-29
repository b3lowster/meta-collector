package com.cubodrom.metacollector.collectors;

import com.cubodrom.metacollector.MetaCollectorApplication;
import com.cubodrom.metacollector.model.MetaFileField;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MetaCollectorApplication.class)
public class JsonMetaCollectorTest {

    @Autowired
    private MetaCollector jsonMetaCollector;

    @Test
    public void shouldCollectMetaFromJsonFile() {
        // Given
        MetaCollector jsonMetaCollector = new JsonMetaCollector();
        InputStream jsonInputStream = getClass().getResourceAsStream("/data.json");

        // When
        List<MetaFileField> fieldsMeta = jsonMetaCollector.collect(jsonInputStream, "data.json");

        // Then
        Assert.assertEquals(8, fieldsMeta.size());
    }
}
