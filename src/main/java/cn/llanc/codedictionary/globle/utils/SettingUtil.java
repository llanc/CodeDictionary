package cn.llanc.codedictionary.globle.utils;

import com.intellij.ide.util.PropertiesComponent;

/**
 * 设置工具
 *
 * @author liulancong
 * @date 2020/11/13
 **/
public class SettingUtil {

    /**
     * 设置词典存储路径
     * @param path
     */
    public static void saveDictionaryPath(String path) {
        PropertiesComponent.getInstance().setValue("cn.llanc.codedictionary.path", path);
    }

    /**
     * 获取词典存储路径
     * @return
     */
    public static String getDictionaryPath() {
        return PropertiesComponent.getInstance().getValue("cn.llanc.codedictionary.path");
    }
}
