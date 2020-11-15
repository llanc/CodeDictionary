package cn.llanc.codedictionary.globle.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.globle.constant.ConstantsEnum;
import cn.llanc.codedictionary.globle.data.EntryDataCenter;
import cn.llanc.codedictionary.globle.data.GlobEntryDataCache;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @author Langel
 * @ClassName GlobleUtils
 * @Description 工具类
 * @date 2020/8/14
 **/
public class GlobleUtils {
    private GlobleUtils() {

    }

    /**
     * 以数组方式获取所有EntryType
     * @return
     */
    public static String[] EntryTypeGetter() {
        return Arrays.stream(
                ConstantsEnum.EntryType.values())
                .map(ConstantsEnum.EntryType::getValue)
                .sorted()
                .toArray(String[]::new);
    }

    /**
     * 隐藏jtable指定列
     * @param table
     * @param column
     */
    public static void hideTableColumn(JTable table, int column)
    {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setMinWidth(0);
        tc.setMaxWidth(0);
    }

    /**
     * 以数据表格元素的形式获取条目
     * @return
     */
    public static String[][] getEntryDataAsTableFormat() {
        // 刷新表格
        List<String[]> entrySources = GlobEntryDataCache.getEntrySource()
                .parallelStream()
                .map(GlobleUtils::formatEntryData)
                .collect(Collectors.toList());
        return transToTwoDimensionalArray(entrySources);
    }

    /**
     * 列表查询
     * @param searchText
     * @return
     */
    public static String[][] getEntryDataAsTableFormatForSearch(String searchText) {
        List<String[]> searchResult = GlobEntryDataCache.getEntrySource()
                .parallelStream()
                .filter(codeDictionaryEntryData -> ObjectUtil.isNotEmpty(codeDictionaryEntryData.getName()) && codeDictionaryEntryData.getName().contains(searchText))
                .map(GlobleUtils::formatEntryData)
                .collect(Collectors.toList());
        return transToTwoDimensionalArray(searchResult);
    }

    /**
     * 转为二维数组
     * @param searchResult
     * @return
     */
    private static String[][] transToTwoDimensionalArray(List<String[]> searchResult) {
        String[][] result = new String[searchResult.size()][3];
        for (int i = 0; i < searchResult.size(); i++) {
            for (int j = 0; j < searchResult.get(i).length; j++) {
                result[i][j] = searchResult.get(i)[j];
            }
        }
        return result;
    }

    /**
     * 格式化条目
     * @param codeDictionaryEntryData
     * @return
     */
    private static String[] formatEntryData(CodeDictionaryEntryData codeDictionaryEntryData) {
        return new String[]{codeDictionaryEntryData.getName(), codeDictionaryEntryData.getDesc(), codeDictionaryEntryData.getId()};

    }

    /**
     * 反格式化条目
     * @param vector
     * @return
     */
    public static CodeDictionaryEntryData unformatEntryData(Vector vector) {
        CodeDictionaryEntryData codeDictionaryEntryData = new CodeDictionaryEntryData();
        codeDictionaryEntryData.setName((String) vector.get(0));
        codeDictionaryEntryData.setDesc((String) vector.get(1));
        codeDictionaryEntryData.setId((String) vector.get(2));
        return codeDictionaryEntryData;

    }
}
