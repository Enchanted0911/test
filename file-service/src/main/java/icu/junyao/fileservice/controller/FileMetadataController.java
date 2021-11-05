package icu.junyao.fileservice.controller;


import icu.junyao.fileservice.res.FileMetadataRes;
import icu.junyao.fileservice.res.R;
import icu.junyao.fileservice.service.FileMetadataService;
import icu.junyao.fileservice.util.FileUtils;
import icu.junyao.fileservice.util.IpAddressUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author johnson
 * @since 2021-11-04
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/file-metadata")
@RequiredArgsConstructor
public class FileMetadataController {
    private final FileMetadataService fileMetadataService;

    @ApiOperation("文件上传, 不大于3GB")
    @PostMapping
    public R<String> uploadFile(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        String ip = IpAddressUtils.getIpAddress(request);
        return R.data(fileMetadataService.uploadFile(file, ip));
    }

    @ApiOperation("通过文件id获取文件元数据")
    @GetMapping("{id}")
    public R<FileMetadataRes> gainFileMetadata(@PathVariable String id) {
        return R.data(fileMetadataService.gainFileMetadata(id));
    }

    @ApiOperation("通过文件id下载文件")
    @GetMapping("file/{id}")
    public void downloadFile(@PathVariable String id, HttpServletResponse response) throws IOException {
        FileMetadataRes fileMetadataRes = fileMetadataService.gainFileMetadata(id);
        FileUtils.downloadFile(fileMetadataRes, response);
    }
}

