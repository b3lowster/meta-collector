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
public class CsvMetaCollectorTest {

    @Autowired
    private MetaCollector csvMetaCollector;

    @Test
    public void shouldCollectMetaFromCsvFile() {
        // Given
        InputStream csvInputStream = getClass().getResourceAsStream("/data.csv");

        // When
        List<MetaFileField> fieldsMeta = csvMetaCollector.collect(csvInputStream, "data.csv");

        // Then
        Assert.assertEquals(4, fieldsMeta.size());
        MetaFileField eventFileField = null;
        MetaFileField widthFileField = null;
        MetaFileField heightFileField = null;
        MetaFileField clientuidtFileField = null;

        for (MetaFileField meta : fieldsMeta) {
            if ("event".equals(meta.getFieldName())) {
                eventFileField = meta;
            } else if ("width".equals(meta.getFieldName())) {
                widthFileField = meta;
            } else if ("height".equals(meta.getFieldName())) {
                heightFileField = meta;
            } else if ("client_uid".equals(meta.getFieldName())) {
                clientuidtFileField = meta;
            }
        }

        Assert.assertNotNull(eventFileField);
        Assert.assertEquals(4, eventFileField.getNonNullValueAmount());
        Assert.assertEquals(0, eventFileField.getNullValueAmount());

        Assert.assertNotNull(widthFileField);
        Assert.assertEquals(2, widthFileField.getNonNullValueAmount());
        Assert.assertEquals(2, widthFileField.getNullValueAmount());

        Assert.assertNotNull(heightFileField);
        Assert.assertEquals(2, heightFileField.getNonNullValueAmount());
        Assert.assertEquals(2, heightFileField.getNullValueAmount());

        Assert.assertNotNull(clientuidtFileField);
        Assert.assertEquals(4, clientuidtFileField.getNonNullValueAmount());
        Assert.assertEquals(0, clientuidtFileField.getNullValueAmount());
    }
}
