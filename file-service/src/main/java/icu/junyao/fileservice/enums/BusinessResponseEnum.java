package icu.junyao.fileservice.enums;

import icu.junyao.fileservice.exception.assertion.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>通用返回结果</p>
 *
 * @author johnson
 * @date 2021-10-02
 */
@Getter
@AllArgsConstructor
public enum BusinessResponseEnum implements BusinessExceptionAssert {


    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FILED("410", "文件上传失败!"),
    /**
     * 文件为空, 请正确上传文件!
     */
    FILE_EMPTY("410", "文件为空, 请正确上传文件!"),
    /**
     * 文件名为空, 请正确上传文件!
     */
    FILE_NAME_EMPTY("410", "文件名为空, 请正确上传文件!");

    private final String code;

    private final String message;

}
