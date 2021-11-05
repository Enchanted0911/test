package icu.junyao.fileclient;

import icu.junyao.fileclient.util.RegexUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author johnson
 * @date 2021-11-05
 */
public class FileClient {

    /**
     * 文件上传
     *
     * @param actionUrl 上传服务器url
     * @param filePath  待上传文件路径
     * @return 文件上传后uuid
     */
    public static String upload(String actionUrl, String filePath) throws Exception {
        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuilder resultBuilder = new StringBuilder();
        String tempLine;
        FileInputStream fStream = null;
        HttpURLConnection httpURLConnection = null;
        String filename = filePath.substring(filePath.lastIndexOf("\\") + 1);
        try {
            // 统一资源
            URL url = new URL(actionUrl);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            httpURLConnection = (HttpURLConnection) urlConnection;

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码连接参数
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置请求内容类型
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + CommonConstant.BOUNDARY);
            // 设置连接超时
            httpURLConnection.setConnectTimeout(5000);

            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());

            ds.writeBytes(CommonConstant.TWO_HYPHENS + CommonConstant.BOUNDARY + CommonConstant.END);
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + "\";filename=\"" + filename + "\"" + CommonConstant.END);
            ds.writeBytes(CommonConstant.END);

            // 将待上传文件写入流中传输给服务端
            fStream = new FileInputStream(filePath);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1) {
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(CommonConstant.END);
            ds.writeBytes(CommonConstant.TWO_HYPHENS + CommonConstant.BOUNDARY + CommonConstant.TWO_HYPHENS + CommonConstant.END);
            ds.flush();


            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                resultBuilder = new StringBuilder();

                // 获取服务端响应数据
                while ((tempLine = reader.readLine()) != null) {
                    resultBuilder.append(tempLine);
                    resultBuilder.append("\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (fStream != null) {
                    fStream.close();
                }
                if (ds != null) {
                    ds.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 正则匹配出uuid
        String resJson = resultBuilder.toString();
        return RegexUtils.gainData(resJson, CommonConstant.DATA_REGEX);
    }

    /**
     * 文件下载
     *
     * @param actionUrl   文件下载url
     * @param uuid        文件的id
     * @param downloadDir 下载到本地的地址
     */
    public static void download(String actionUrl, String uuid, String downloadDir) {
        BufferedInputStream bin = null;
        OutputStream out = null;
        HttpURLConnection httpURLConnection = null;
        try {
            // 统一资源
            URL url = new URL(actionUrl + "/" + uuid);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            httpURLConnection = (HttpURLConnection) urlConnection;
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            //设置连接超时
            httpURLConnection.setConnectTimeout(5000);
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 正则匹配出完整文件名 通过getFile函数获得的文件名没带后缀
            String containsFilename = httpURLConnection.getHeaderFields().get("Content-Disposition").get(0);

            // 文件名
            String fileFullName = RegexUtils.gainData(containsFilename, CommonConstant.FILE_NAME_REGEX);

            bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileFullName;
            File file = new File(path);

            // 上级目录若不存在则先创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // 开始下载
            out = new FileOutputStream(file);
            int size;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                out.write(buf, 0, size);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bin != null) {
                    bin.close();
                }
                if (out != null) {
                    out.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据文件id获取文件元数据
     *
     * @param targetUrl 服务器url
     * @param uuid 文件id
     * @return 文件元数据json字符串
     */
    public static String fetchFileMetadata(String targetUrl, String uuid) {
        HttpURLConnection conn = null;
        BufferedReader br = null;
        try {
            URL url = new URL(targetUrl + "/" + uuid);
            conn = (HttpURLConnection) url.openConnection();
            PrintWriter out;

            //设置连接超时
            conn.setConnectTimeout(5000);
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            //获取到服务器返回的输入流，字节输入流InputStream对象
            InputStream in = conn.getInputStream();

            //对获取到的输入流进行读取
            br = new BufferedReader(new InputStreamReader(in));

            // 构造返回值
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            return RegexUtils.gainData(response.toString(), CommonConstant.FILE_METADATA_REGEX);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }
}
