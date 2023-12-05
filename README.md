# SpigotDependencyManager

Allow spigot plugins to pull dependencies online without packaging them into jars

Usage: 

1. in `plugin.yml` add dependencies
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


2. using `DependencyManager.loadDependency(this);` in onEnable()
![image](https://github.com/meteorOSS/SpigotDependencyManager/assets/61687266/8c5de5e0-c5d0-4b5d-a3f7-ac5adaba3c25)



Partial code references https://github.com/Revxrsal/PluginLib/tree/master/src/main/java/pluginlib

