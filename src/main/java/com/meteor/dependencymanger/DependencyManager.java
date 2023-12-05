package com.meteor.dependencymanger;

import com.meteor.dependencymanger.data.LibData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import sun.misc.Unsafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class DependencyManager {

    public static String repository = "https://repo1.maven.org/maven2";

    private static MethodHandle methodHandle;
    private static Object ucp;

    private static void init(JavaPlugin plugin){
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            Field ucpField = URLClassLoader.class.getDeclaredField("ucp");
            ucp = unsafe.getObject(plugin.getClass().getClassLoader(), unsafe.objectFieldOffset(ucpField));
            Field lookupField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            MethodHandles.Lookup lookup = (MethodHandles.Lookup) unsafe.getObject(unsafe.staticFieldBase(lookupField), unsafe.staticFieldOffset(lookupField));
            methodHandle = lookup.findVirtual(ucp.getClass(), "addURL", MethodType.methodType(void.class, URL.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // get plugin.yml
    private static YamlConfiguration getPluginYaml(JavaPlugin plugin){
        try(InputStreamReader inputStreamReader = new InputStreamReader(plugin.getClass().getClassLoader().getResourceAsStream("plugin.yml"));
        ) {
            return YamlConfiguration.loadConfiguration(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void loadDependency(JavaPlugin plugin){
        if(methodHandle==null||ucp==null){
            init(plugin);
        }
        YamlConfiguration pluginYaml = getPluginYaml(plugin);
        ConfigurationSection libConfigSection = pluginYaml.getConfigurationSection("lib");
        List<LibData> list = new ArrayList<>();
        libConfigSection.getKeys(false).forEach(s->{
            list.add(new LibData(libConfigSection.getConfigurationSection(s)));
        });

        File dataFolder = new File(plugin.getDataFolder()+"/lib");
        if(!dataFolder.exists()) dataFolder.mkdirs();

        for (LibData libData : list) {
            File libFile = new File(dataFolder,libData.jarName());
            if(!libFile.exists()){
                // 下载依赖
                libData.download(libFile);
            }
            try {
                methodHandle.invoke(ucp,libFile.toURI().toURL());
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }


    }

}
