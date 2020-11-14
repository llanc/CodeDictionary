package cn.llanc.codedictionary.globle.data;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 全局条目数据缓存
 *
 * @author Langel
 * @date 2020/11/5
 **/
public class GlobEntryDataCache {
    /**
     * 最大序号
     */
    private int maxSerialNumber;

    /**
     * 全局数据缓存
     */
    private static Map<String, CodeDictionaryEntryData> globeEntryDataMapCache = new HashMap<>();

    /**
     * 初始化全局数据缓存
     *
     * @param sourceData
     */
    public static void initGlobEntryDataCache(List<CodeDictionaryEntryData> sourceData) {
        globeEntryDataMapCache = sourceData.parallelStream()
                //.sorted(Comparator.comparing(CodeDictionaryEntryData::getSerialNumber))
                .collect(Collectors.toMap(CodeDictionaryEntryData::getId, e -> e));

    }

    /**
     * 获取所有条目数据
     * @return
     */
    public static List<CodeDictionaryEntryData> getEntrySource() {
        return new ArrayList<>(globeEntryDataMapCache.values());
    }


    /**
     * 根据条目主键查找条目
     *
     * @param id
     * @return
     */
    public static Optional<CodeDictionaryEntryData> findById(@Nonnull String id) {
        return Optional.ofNullable(globeEntryDataMapCache.get(id));
    }

    /**
     * 根据用户名模糊查找
     *
     * @param name
     * @return
     */
    public static List<CodeDictionaryEntryData> findByName(String name) {
        if (StrUtil.isBlank(name)) {
            return (List<CodeDictionaryEntryData>) globeEntryDataMapCache.values();
        }
        return globeEntryDataMapCache.values()
                .stream()
                .filter(e -> e.getName().contains(name))
                .collect(Collectors.toList());
    }

    /**
     * 添加新条目
     *
     * @param entry
     */
    public static void addEntry(@Nonnull CodeDictionaryEntryData entry) {
        entry.setSerialNumber(globeEntryDataMapCache.size() + 1);
        globeEntryDataMapCache.put(entry.getId(), entry);
    }

    /**
     * 根据id删除条目
     */
    public static void removeById(@Nonnull String id) {
        globeEntryDataMapCache.remove(id);
    }
}
