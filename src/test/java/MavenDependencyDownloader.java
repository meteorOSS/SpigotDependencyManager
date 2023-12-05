import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;

public class MavenDependencyDownloader {

    public static void main(String[] args) {
        String repository = "https://repo.maven.apache.org/maven2/";
        String groupId = "org.apache.httpcomponents.core5";
        String artifactId = "httpcore5";
        String version = "5.2.4";
        try {
            // 构建 URL
            URL url = buildDependencyUrl(repository, groupId, artifactId, version);

            // 指定保存位置
            File saveLocation = new File("downloaded/" + artifactId + "-" + version + ".jar");

            // 下载 JAR 文件
            downloadJarFile(url, saveLocation);

            System.out.println("Download completed: " + saveLocation.getAbsolutePath());
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
    }

    private static URL buildDependencyUrl(String repository, String groupId, String artifactId, String version) throws MalformedURLException {
        if (!repository.endsWith("/")) {
            repository += "/";
        }
        String groupPath = groupId.replace('.', '/');
        String url = String.format("%s%s/%s/%s/%s-%s.jar",
                repository, groupPath, artifactId, version, artifactId, version);

        return new URL(url);
    }

    private static void downloadJarFile(URL url, File saveLocation) throws IOException {
        URLConnection connection = url.openConnection();
        saveLocation.getParentFile().mkdirs();
        byte[] buffer = new byte[4096];
        try (InputStream in = new BufferedInputStream(connection.getInputStream());
             FileOutputStream outStream = new FileOutputStream(saveLocation);
             BufferedOutputStream out = new BufferedOutputStream(outStream, buffer.length)) {

            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

    }
}
