package com.cubodrom.metacollector;

import com.cubodrom.metacollector.model.MetaFileField;
import com.cubodrom.metacollector.service.ActionService;
import com.cubodrom.metacollector.utils.Constances;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication
public class MetaCollectorApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(MetaCollectorApplication.class);

    @Autowired
    private ActionService crawlService;

    @Autowired
    private ActionService describeService;

    public static void main(String[] args) {
        SpringApplication.run(MetaCollectorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : meta collector application");

        if (args.length == 2) {
            String action = args[0];
            String filePath = args[1];

            if(Constances.CRAWL.equals(action)) {
                crawlService.act(filePath);
                LOG.info("Meta information has been successfully fetched and saved");
            } else if (Constances.DESCRIBE.equals(action)) {
                List<MetaFileField> metaFileFields = describeService.act(filePath);
                LOG.info("Fields meta list");
                LOG.info(metaFileFields.stream().map(Objects::toString).collect(Collectors.joining(",")));
            } else {
                LOG.info(String.format("Unsupported action type. Actual is %s, expected are %s", action, String.join(",", Constances.CRAWL, Constances.DESCRIBE)));
            }
        } else {
            LOG.info("ERROR : incorrect args numbers");
        }
    }
}
