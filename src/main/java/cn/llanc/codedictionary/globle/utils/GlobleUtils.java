package cn.llanc.codedictionary.globle.utils;

import cn.llanc.codedictionary.globle.constant.ConstantsEnum;
import cn.llanc.codedictionary.globle.data.GlobEntryDataCache;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.Arrays;
import java.util.List;
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
                .stream()
                .map(codeDictionaryEntryData -> new String[]{codeDictionaryEntryData.getName(), codeDictionaryEntryData.getDesc(), codeDictionaryEntryData.getId()})
                .collect(Collectors.toList());
        String[][] result = new String[entrySources.size()][3];
        for (int i = 0; i < entrySources.size(); i++) {
            for (int j = 0; j < entrySources.get(i).length; j++) {
                result[i][j] = entrySources.get(i)[j];
            }
        }
        return result;
    }
}
