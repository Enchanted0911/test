package icu.junyao.fileservice.controller;


import icu.junyao.fileservice.res.R;
import icu.junyao.fileservice.service.FileMetadataService;
import icu.junyao.fileservice.util.IpAddressUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
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

    @ApiOperation("文件上传")
    @PostMapping
    public R<String> uploadFile(@RequestPart("file") MultipartFile file, HttpServletRequest request) {
        String ip = IpAddressUtils.getIpAddress(request);
        return R.data(fileMetadataService.uploadFile(file, ip));
    }
}

