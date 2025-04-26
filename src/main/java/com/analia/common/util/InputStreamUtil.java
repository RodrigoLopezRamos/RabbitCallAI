package com.analia.common.util;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamUtil {
    /**
     * @param is
     * @return
     */
    public static String getStringFromInputStream(InputStream is) throws AnaliaException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            throw new AnaliaException(ExceptionCode.SERVER_ERROR, e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new AnaliaException(ExceptionCode.SERVER_ERROR, e.getMessage(), e);
                }
            }
        }
        return sb.toString();

    }
}
