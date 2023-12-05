# SpigotDependencyManager

允许spigot插件在线拉取依赖，而不用打包到jar里

使用方法: 

1. 在 `plugin.yml` 中定义依赖
``` yaml
lib:
  httpcore5:
    url: 'https://repo.maven.apache.org/maven2/'
    groupId: 'org.apache.httpcomponents.core5'
    artifactId: 'httpcore5'
    version: '5.2.4'
  httpclient:
    url: 'https://repo.maven.apache.org/maven2/'
    groupId: 'org.apache.httpcomponents.client5'
    artifactId: 'httpclient5'
    version: '5.2.3'
```
可以忽略url项，默认使用https://repo.maven.apache.org/maven2/

2. 插件启动类使用 `DependencyManager.loadDependency(this);` 加载依赖
![image](https://github.com/meteorOSS/SpigotDependencyManager/assets/61687266/8c5de5e0-c5d0-4b5d-a3f7-ac5adaba3c25)

拉取代码可打包为一个示例bukkit插件.可能会有许多隐患(比如网络原因可能会挂起主线程过久崩溃)待解决

部分代码参考了 https://github.com/Revxrsal/PluginLib/tree/master/src/main/java/pluginlib

