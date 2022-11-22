package com.brazdore;

import com.brazdore.configs.AppConfig;
import com.brazdore.services.DirectoryWatcher;
import com.brazdore.services.FileService;
import com.brazdore.utils.FileUtility;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        BasicConfigurator.configure();
        LOGGER.info("Application: Started");

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.setProperty("basePath", context.getBean("basePath", String.class));
        System.setProperty("inputPath", context.getBean("inputPath", String.class));
        System.setProperty("outputPath", context.getBean("outputPath", String.class));
        System.setProperty("sizeLimit", context.getBean("sizeLimit", String.class));
        System.setProperty("slashType", System.lineSeparator().equals("\r\n") ? "\\" : "/"); // Windows ou Unix?

        FileUtility.makeDirectory(System.getProperty("basePath"));
        FileUtility.makeDirectory(System.getProperty("inputPath"));
        FileUtility.makeDirectory(System.getProperty("outputPath"));

        DirectoryWatcher.watch(context.getBean(FileService.class));
    }
}
