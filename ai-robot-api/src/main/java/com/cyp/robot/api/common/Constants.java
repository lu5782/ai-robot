package com.cyp.robot.api.common;

public class Constants {
    
    public static final String UPLOAD_PATH = "\\temp\\upload\\";
    public static final String DOWNLOAD_PATH = "\\temp\\download";
    public static final String TEMP_PATH = "\\temp\\temp";
    public static final int IS_DELETED_0 = 0;
    public static final int IS_DELETED_1 = 1;
    public static final String OPT_BY = "SYS";


    public enum FileType {
        /**
         * JPEG
         */
        JPEG("FFD8FF", "jpg"),

        /**
         * PNG
         */
        PNG("89504E47", "png"),

        /**
         * GIF
         */
        GIF("47494638", "gif"),

        /**
         * TIFF
         */
        TIFF("49492A00"),

        /**
         * Windows bitmap
         */
        BMP("424D"),

        /**
         * CAD
         */
        DWG("41433130"),

        /**
         * Adobe photoshop
         */
        PSD("38425053"),

        /**
         * Rich Text Format
         */
        RTF("7B5C727466"),

        /**
         * XML
         */
        XML("3C3F786D6C"),

        /**
         * HTML
         */
        HTML("68746D6C3E"),

        /**
         * Outlook Express
         */
        DBX("CFAD12FEC5FD746F "),

        /**
         * Outlook
         */
        PST("2142444E"),

        /**
         * doc;xls;dot;ppt;xla;ppa;pps;pot;msi;sdw;db
         */
        OLE2("0xD0CF11E0A1B11AE1"),

        /**
         * Microsoft Word/Excel
         */
        XLS_DOC("D0CF11E0"),

        /**
         * Microsoft Access
         */
        MDB("5374616E64617264204A"),

        /**
         * Word Perfect
         */
        WPB("FF575043"),

        /**
         * Postscript
         */
        EPS_PS("252150532D41646F6265"),

        /**
         * Adobe Acrobat
         */
        PDF("255044462D312E"),

        /**
         * Windows Password
         */
        PWL("E3828596"),

        /**
         * ZIP Archive
         */
        ZIP("504B0304"),

        /**
         * ARAR Archive
         */
        RAR("52617221"),

        /**
         * WAVE
         */
        WAV("57415645"),

        /**
         * AVI
         */
        AVI("41564920"),

        /**
         * Real Audio
         */
        RAM("2E7261FD"),

        /**
         * Real Media
         */
        RM("2E524D46"),

        /**
         * Quicktime
         */
        MOV("6D6F6F76"),

        /**
         * Windows Media
         */
        ASF("3026B2758E66CF11"),

        /**
         * MIDI
         */
        MID("4D546864");

        private String fileType;
        private String value;

        FileType(String fileType) {
            this.fileType = fileType;
        }

        FileType(String fileType, String value) {
            this.fileType = fileType;
            this.value = value;
        }

        public String getFileType() {
            return fileType;
        }

        public String getValue() {
            return value;
        }
    }
}
