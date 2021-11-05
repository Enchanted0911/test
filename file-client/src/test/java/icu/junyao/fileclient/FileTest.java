package icu.junyao.fileclient;

import org.junit.Test;

/**
 * @author johnson
 * @date 2021-11-05
 */
public class FileTest {

    @Test
    public void uploadTest() throws Exception {
        String url = "http://127.0.0.1:3333/test/file-metadata";
        String filename = "C:\\Users\\wu\\Pictures\\001\\001.jpg";
        String result = FileClient.upload(url, filename);
        System.out.println(result);
    }

    @Test
    public void downloadTest() {
        String url = "http://127.0.0.1:3333/test/file-metadata/file";
        String uuid = "83e2d1b0faae4c74a3b8590f76898dbb";
        FileClient.download(url, uuid, "C:\\Users\\wu\\Pictures");
    }

    @Test
    public void fetchFileMetadataTest() {
        String url = "http://127.0.0.1:3333/test/file-metadata";
        String res = FileClient.fetchFileMetadata(url, "83e2d1b0faae4c74a3b8590f76898dbb");
        System.out.println(res);
    }
}
