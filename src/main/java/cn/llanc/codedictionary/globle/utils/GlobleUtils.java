package cn.llanc.codedictionary.globle.utils;

import cn.llanc.codedictionary.globle.constant.ConstantsEnum;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.Arrays;

/**
 * @author liulancong
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
}
