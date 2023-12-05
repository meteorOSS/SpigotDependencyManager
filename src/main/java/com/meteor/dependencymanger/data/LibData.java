package com.meteor.dependencymanger.data;

import com.meteor.dependencymanger.DependencyManager;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LibData {

    private String url;
    private String groupId;
    private String artifactId;
    private String version;


    public LibData(ConfigurationSection configurationSection){
        this.url = configurationSection.getString("url", DependencyManager.repository);
        this.groupId = configurationSection.getString("groupId");
        this.artifactId = configurationSection.getString("artifactId");
        this.version = configurationSection.getString("version");
    }

    public URL toURL() throws MalformedURLException {
        if (!url.endsWith("/")) {
            url += "/";
        }
        String groupPath = groupId.replace('.', '/');
        String url = String.format("%s%s/%s/%s/%s-%s.jar",
                this.url,groupPath, artifactId, version, artifactId, version);
        return new URL(url);
    }

    public String getUrl() {
        return url;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String jarName(){
        return artifactId+"-"+version+".jar";
    }

    private static void downloadJarFile(URL url, File saveLocation) throws IOException {
        URLConnection connection = url.openConnection();
        saveLocation.getParentFile().mkdirs();
        try (InputStream in = connection.getInputStream();
             FileOutputStream out = new FileOutputStream(saveLocation)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("Download completed,save to "+saveLocation.toPath());
        }
    }

    public void download(File file){
        try {
            System.out.println("Start download "+jarName()+",Please wait....");
            downloadJarFile(toURL(),file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
