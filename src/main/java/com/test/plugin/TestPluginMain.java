package com.test.plugin;

import com.meteor.dependencymanger.DependencyManager;
//import okhttp3.OkHttpClient;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.text.ParseException;

public class TestPluginMain extends JavaPlugin {

    @Override
    public void onEnable() {
        DependencyManager.loadDependency(this);
        doGet();
    }

    public void doGet() {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://localhost:12345/doGetControllerOne");
        System.out.println(httpGet.getClass().getName());
    }
}
