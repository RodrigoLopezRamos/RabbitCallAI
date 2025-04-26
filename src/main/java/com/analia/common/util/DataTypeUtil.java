package com.analia.common.util;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;

import java.util.Date;
import java.util.List;


public class DataTypeUtil {

    /***
     *
     * @param dataTypeId
     * @param value
     * @return
     * @throws WutzWhatException
     */
    @SuppressWarnings("unchecked")
    public static Object convertData(int dataTypeId, boolean singleValue, Object value) throws AnaliaException {
        switch (DataType.getDataType(dataTypeId)) {
            case DATA_TYPE_BOOLEAN:
                if (singleValue) {
                    value = value;
                } else {
                    value = value;
                }
                break;
            case DATA_TYPE_DATE:
                if (singleValue) {
                    value = value;
                } else {
                    value = value;
                }
                break;
            case DATA_TYPE_DOUBLE:
                if (singleValue) {
                    value = value;
                } else {
                    value = value;
                }
                break;
            case DATA_TYPE_INTEGER:
                if (singleValue) {
                    value = value;
                } else {
                    value = value;
                }
                break;
            case DATA_TYPE_STRING:
                if (singleValue) {
                    value = value;
                } else {
                    value = value;
                }
                break;
            default:
                System.out.println("There is not data type associated to the following value : " + value);
                break;
        }
        return value;
    }

    public enum DataType {
        DATA_TYPE_STRING(0, "String"), DATA_TYPE_INTEGER(1, "Integer"), DATA_TYPE_DOUBLE(2, "Double"), DATA_TYPE_DATE(3, "Date"), DATA_TYPE_PROFILE(4, "Profile"), DATA_TYPE_BOOLEAN(5, "Boolean"), DATA_TYPE_ARRAY_STRING(6, "Array of Strings");

        private int dataType;
        private String dataTypeString;

        DataType(int typeValue, String typeString) {
            this.dataType = typeValue;
            this.dataTypeString = typeString;
        }

        public static DataType getDataType(int dataTypeId) throws AnaliaException {
            for (DataType dataType : DataType.values()) {
                if (dataType.getDataType() == dataTypeId) {
                    return dataType;
                }
            }
            throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "Data Type is invalid ");
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public String getDataTypeString() {
            return dataTypeString;
        }

        public void setDataTypeString(String dataTypeString) {
            this.dataTypeString = dataTypeString;
        }
    }

}