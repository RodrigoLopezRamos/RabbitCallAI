package com.analia.web.util;


import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;

import java.io.IOException;

public class MimeTypeUtil {
    public static int getIdForMimeType(MimeType mimetype) throws IOException {
        int id = 0;
        for (MimeType mimeType : MimeType.values()) {
            if (mimeType == mimetype) {
                id = mimeType.getId();
            }
        }
        return id;
    }

    public static int getMimeTypeIdForString(String mimeType) throws IOException {
        int id = 0;
        for (MimeType mimeTypeObject : MimeType.values()) {
            if (mimeType.equals(mimeTypeObject.getMimeType())) {
                id = getIdForMimeType(mimeTypeObject);
            }
        }
        return id;
    }

    public static String getMimeTypeNameById(int mimeTypeId) throws AnaliaException {
        for (MimeType mimeTypeObject : MimeType.values()) {
            if (mimeTypeId == mimeTypeObject.getId()) {
                return mimeTypeObject.getMimeType();
            }
        }
        throw new AnaliaException(ExceptionCode.SERVER_ERROR, "MimeType is not defined !");

    }


    public enum MimeType {

        JPEGIMAGE(1, "image/jpeg"), JPGIMAGE(2, "image/jpg"), PNGIMAGE(3, "image/png"), VIDEOMP4(4, "video/mp4"), VIDEOAVI(5, "video/avi"), VIDEOQUICKTIME(6, "video/quicktime"), PDF(7, "application/pdf"), HTML(8, "text/html");

        private int id;
        private String mimeType;

        MimeType(int id, String mimeType) {
            this.id = id;
            this.mimeType = mimeType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

    }
}
