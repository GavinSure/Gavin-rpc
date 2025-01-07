package com.Gavin.Gavinrpc.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.yaml.YamlUtil;
import lombok.val;

import java.io.InputStream;
import java.util.Map;

/**
 * 配置工具类
 */
public class ConfigUtils {

    /**
     * 加载配置对象
     *
     * @param tClass
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持区分环境
     *
     * @param tClass
     * @param prefix
     * @param environment
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        String propertiesConfig= configFileBuilder.toString()+".properties";
        String ymlConfig= configFileBuilder.toString()+".yml";

        // 尝试从类路径中加载 application.properties 文件
        InputStream inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream(propertiesConfig);
        if (inputStream != null) {
            Props props = new Props(propertiesConfig);
            return props.toBean(tClass, prefix);        //将配置文件中的属性映射到对象中
        }
        //properties不存在，加载yml文件
        inputStream = ConfigUtils.class.getClassLoader().getResourceAsStream(ymlConfig);
        Map<String,Object> ymlMap = YamlUtil.load(inputStream, Map.class);
        Map<String, Object> serverConfigMap = (Map<String, Object>) ymlMap.get("Gavinrpc");

        return BeanUtil.toBean(serverConfigMap, tClass);
    }



}
