package icu.junyao.fileclient;

/**
 * @author johnson
 * @date 2021-11-05
 */
public interface CommonConstant {
    String TWO_HYPHENS = "--";
    String BOUNDARY = "*****";
    String END = "\r\n";
    String DATA_REGEX = "\"data\":\"([\\w]*?)\"";
    String FILE_NAME_REGEX = "filename=(.*)";
    String FILE_METADATA_REGEX = "\"data\":(.*),\"msg";
}
