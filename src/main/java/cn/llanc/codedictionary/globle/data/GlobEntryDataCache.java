package cn.llanc.codedictionary.globle.data;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import com.sun.jna.platform.win32.Guid;

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
    private static LinkedHashMap<String, CodeDictionaryEntryData> globleEntryDataMapCache = new LinkedHashMap<>();

    /**
     * 初始化全局数据缓存
     *
     * @param sourceData
     */
    public static void initGlobEntryDataCache(List<CodeDictionaryEntryData> sourceData) {
        globleEntryDataMapCache = sourceData.stream()
                .sorted(Comparator.comparing(CodeDictionaryEntryData::getSerialNumber))
                .collect(Collectors.toMap(CodeDictionaryEntryData::getId, e -> e, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * 获取所有条目数据
     * @return
     */
    public static List<CodeDictionaryEntryData> getEntrySource() {
        return (List<CodeDictionaryEntryData>) globleEntryDataMapCache.values();
    }


    /**
     * 根据条目主键查找条目
     *
     * @param id
     * @return
     */
    public Optional<CodeDictionaryEntryData> findById(@Nonnull String id) {
        return Optional.ofNullable(globleEntryDataMapCache.get(id));
    }

    /**
     * 根据用户名模糊查找
     *
     * @param name
     * @return
     */
    public List<CodeDictionaryEntryData> findByName(String name) {
        if (StrUtil.isBlank(name)) {
            return (List<CodeDictionaryEntryData>) globleEntryDataMapCache.values();
        }
        return globleEntryDataMapCache.values()
                .stream()
                .filter(e -> e.getName().contains(name))
                .collect(Collectors.toList());
    }

    /**
     * 添加新条目
     *
     * @param entry
     */
    private void addEntry(@Nonnull CodeDictionaryEntryData entry) {
        entry.setSerialNumber(globleEntryDataMapCache.size() + 1);
        globleEntryDataMapCache.put(IdUtil.fastSimpleUUID(), entry);
    }

    /**
     * 根据id删除条目
     */
    private void removeById(@Nonnull String id) {
        globleEntryDataMapCache.remove(id);
    }
}
