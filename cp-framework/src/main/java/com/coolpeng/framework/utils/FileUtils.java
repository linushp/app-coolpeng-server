package com.coolpeng.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tan Liang
 * @since 2015-07-16
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public  String buildResourceAbsFilename(String relFilename) {
        return this.getClass().getResource(relFilename).getFile();
    }
}
