package icu.junyao.fileservice.service.impl;

import icu.junyao.fileservice.constant.CommonConstants;
import icu.junyao.fileservice.entity.FileMetadata;
import icu.junyao.fileservice.enums.BusinessResponseEnum;
import icu.junyao.fileservice.exception.BusinessException;
import icu.junyao.fileservice.exception.assertion.BusinessExceptionAssert;
import icu.junyao.fileservice.mapper.FileMetadataMapper;
import icu.junyao.fileservice.service.FileMetadataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author johnson
 * @since 2021-11-04
 */
@Service
public class FileMetadataServiceImpl extends ServiceImpl<FileMetadataMapper, FileMetadata> implements FileMetadataService {

    @Value("${filepath.basePath}")
    private String basePath;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadFile(MultipartFile file, String ip) {
        // 上传的文件未空文件则抛出异常
        if (file.isEmpty()) {
            throw BusinessResponseEnum.FILE_EMPTY.newException();
        }

        // 先记录元数据, 再把数据返回, 后续需要用到返回数据中的UUID以及目录地址
        FileMetadata fileMetadata = saveMetadata(file, ip);

        // 组合文件名准备上传文件
        String originName = file.getOriginalFilename();

        // 预防NPE
        BusinessResponseEnum.FILE_NAME_EMPTY.assertNotNull(originName);

        String suffix = originName.substring(originName.lastIndexOf(CommonConstants.DOT));
        String newFileName = fileMetadata.getId() + suffix;
        File upLoadFile = new File(fileMetadata.getUrl(), newFileName);

        // 第一次上传时把父级目录创建出来
        if (!upLoadFile.getParentFile().exists()) {
            upLoadFile.getParentFile().mkdirs();
        }

        // 开始上传
        try {
            file.transferTo(upLoadFile);
        } catch (IOException e) {
            throw BusinessResponseEnum.FILE_UPLOAD_FILED.newException();
        }

        return fileMetadata.getId();
    }


    /**
     * 持久化文件元信息
     *
     * @param file 待持久化文件
     * @param ip 上传该文件的ip
     * @return {@link FileMetadata}
     */
    private FileMetadata saveMetadata(MultipartFile file, String ip) {
        // 获取文件大小, 文件类型, 原始文件名, 创建时间插件自动生成
        Long size = file.getSize();
        String contextType = file.getContentType();
        String filename = file.getOriginalFilename();

        // 重置文件名
        String id = UUID.randomUUID().toString().replace(CommonConstants.HYPHEN, CommonConstants.EMPTY);

        // 设置保存地址
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(CommonConstants.DATE_PATTERN);
        String datePath = dateTimeFormatter.format(LocalDate.now());
        String filePath = basePath + datePath;

        // 属性设置
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setId(id);
        fileMetadata.setUrl(filePath);
        fileMetadata.setIpAddress(ip);
        fileMetadata.setName(filename);
        fileMetadata.setSize(size);
        fileMetadata.setType(contextType);

        // 持久化
        super.save(fileMetadata);

        return fileMetadata;
    }
}
