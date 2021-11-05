package icu.junyao.fileservice.util;

import icu.junyao.fileservice.constant.CommonConstants;
import icu.junyao.fileservice.res.FileMetadataRes;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 文件工具类
 *
 * @author johnson
 * @since 2021-11-04
 */
@Slf4j
public class FileUtils {

    public static void downloadFile(FileMetadataRes file, HttpServletResponse response) throws IOException {

        // 拼文件完整路径
        String originName = file.getName();
        String suffix = originName.substring(originName.lastIndexOf(CommonConstants.DOT));
        String fileName = file.getId() + suffix;
        String filePath = file.getUrl() + File.separatorChar + fileName;
        File f = new File(filePath);

        // 判空
        if (!f.exists()) {
            response.sendError(410, "File not found!");
        }

        BufferedInputStream br = null;
        OutputStream out = null;
        try {
            br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;

            // 清空下载文件空白行
            response.reset();

            response.setContentType("application/x-msdownload");
            // 设置响应头和文件名
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            out = response.getOutputStream();

            log.info("文件开始下载-----id为{}", file.getId());
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.flush();
            log.info("文件结束下载-----id为{}", file.getId());
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(410, "download failed");

        } finally {

            // 关闭流
            try {
                if (br != null) {
                    br.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
