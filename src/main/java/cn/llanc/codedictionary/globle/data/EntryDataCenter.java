package cn.llanc.codedictionary.globle.data;

import cn.llanc.codedictionary.globle.constant.ConstantsEnum;

import javax.swing.table.DefaultTableModel;

/**
 * @author Langel
 * @ClassName EntryDataCenter
 * @Description 条目数据中心
 * @date 2020/8/14
 **/
public class EntryDataCenter {
    /**
     * 选中数据
     */
    public static String ENTRY_CONTENT;

    /**
     * 条目内容类型
     */
    public static String ENTRY_CONTENT_TYPE = ConstantsEnum.EntryType.TXT.getValue();

    /**
     * 列名
     */
    public static String[] COLUMN_NAMES = new String[]{ConstantsEnum.EntryInfoTable.NAME.getValue(), ConstantsEnum.EntryInfoTable.DESC.getValue(), ConstantsEnum.EntryInfoTable.ID.getValue()};

    /**
     * 条目列表数据模型
     */
    public static DefaultTableModel ENTRY_INFO_TABLE_MODEL;

}
