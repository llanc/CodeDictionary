package cn.llanc.codedictionary.globle.data;

import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.globle.constant.ConstantsEnum;

import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.List;

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
    public static String ENTRY_CONTENT_TYPE;
    /**
     * 条目列表
     */
    public static List<CodeDictionaryEntryData> ENTRY_LIST = new LinkedList<>();

    public static String[] COLUMN_NAMES = new String[]{ConstantsEnum.EntryInfoTable.NAME.getValue(), ConstantsEnum.EntryInfoTable.DESC.getValue(), ConstantsEnum.EntryInfoTable.CONTENT.getValue()};

    /**
     * 条目列表数据模型
     */
    public static DefaultTableModel ENTRY_INFO_TABLE_MODEL = new DefaultTableModel(null, COLUMN_NAMES);

}
