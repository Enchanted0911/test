package icu.junyao.fileservice.service;

import icu.junyao.fileservice.entity.FileMetadata;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author johnson
 * @since 2021-11-04
 */
public interface FileMetadataService extends IService<FileMetadata> {

    /**
     * 文件上传, 同时将文件元数据存入数据库
     *
     * @param file 待上传文件
     * @param ip 上传ip
     * @return 文件唯一标识UUID
     */
    String uploadFile(MultipartFile file, String ip);
}
