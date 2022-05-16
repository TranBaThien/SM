/*
 * @(#) FileUtil.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tqvu1
 * Create Date: 2020/01/07
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.common.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tqvu1
 *
 */

public class FileUtil {

    /**
     * Check virus attack file
     *
     * @param name String
     * @param file MultipartFile
     * @return boolean
     */
    public static boolean isOkCheckVirus(String name, MultipartFile file) {
        try {
            if (!CheckVirus.validateNoVirus(file.getInputStream())) {
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Check file is exist or not.
     *
     * @param file MultipartFile
     * @return boolean
     */
    public static boolean fileExistenceCheck(MultipartFile file) {
        try {
            Resource resource = file.getResource();
            String fileName = resource.getFilename();
            File fileCheckExist = new File(fileName);
            return fileCheckExist.exists();
        } catch (Exception e) {
            return false;
        }
    }

}
