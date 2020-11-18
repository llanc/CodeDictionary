package cn.llanc.codedictionary.globle.data;

import cn.hutool.core.date.DateUtil;
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
                .collect(Collectors.toMap(CodeDictionaryEntryData::getId, e -> e));

    }

    /**
     * 获取所有条目数据
     * @return
     */
    public static List<CodeDictionaryEntryData> getEntrySource() {
        ArrayList<CodeDictionaryEntryData> codeDictionaryEntryData = new ArrayList<>(globeEntryDataMapCache.values());
        codeDictionaryEntryData.sort(Comparator.comparing(CodeDictionaryEntryData::getRecentModifyData).reversed());
        return codeDictionaryEntryData;
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
     * 根据条目名模糊查找
     *
     * @param name
     * @return
     */
    public static List<CodeDictionaryEntryData> findByName(String name) {
        if (StrUtil.isBlank(name)) {
            return (List<CodeDictionaryEntryData>) globeEntryDataMapCache.values();
        }
        return globeEntryDataMapCache.values()
                .parallelStream()
                .filter(e -> e.getName().contains(name))
                .sorted(Comparator.comparing(CodeDictionaryEntryData::getRecentModifyData).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 添加新条目
     *
     * @param entry
     */
    public static void addEntry(@Nonnull CodeDictionaryEntryData entry) {
        globeEntryDataMapCache.put(entry.getId(), entry);
    }

    /**
     * 根据id删除条目
     */
    public static void removeById(@Nonnull String id) {
        globeEntryDataMapCache.remove(id);
    }

    /**
     * 根据id更新数据
     * @param entryData
     */
    public static void modifyById(CodeDictionaryEntryData entryData) {
        CodeDictionaryEntryData codeDictionaryEntryData = globeEntryDataMapCache.get(entryData.getId());
        codeDictionaryEntryData.setName(entryData.getName());
        codeDictionaryEntryData.setDesc(entryData.getDesc());
        codeDictionaryEntryData.setContent(entryData.getContent());
        codeDictionaryEntryData.setRecentModifyData(DateUtil.date());
        globeEntryDataMapCache.put(entryData.getId(), codeDictionaryEntryData);
    }
    /**
     * 根据id更新数据
     * @param entryData
     */
    public static void modifyNameDescById(CodeDictionaryEntryData entryData) {
        CodeDictionaryEntryData codeDictionaryEntryData = globeEntryDataMapCache.get(entryData.getId());
        codeDictionaryEntryData.setName(entryData.getName());
        codeDictionaryEntryData.setDesc(entryData.getDesc());
        codeDictionaryEntryData.setRecentModifyData(DateUtil.date());
        globeEntryDataMapCache.put(entryData.getId(), codeDictionaryEntryData);
    }
}
