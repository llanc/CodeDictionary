package cn.llanc.codedictionary.globle.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.fileprocess.exporter.CodeDictionaryFileExporter;
import cn.llanc.codedictionary.globle.constant.ConstantsEnum;
import cn.llanc.codedictionary.globle.data.EntryDataCenter;
import cn.llanc.codedictionary.globle.data.GlobEntryDataCache;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import static cn.llanc.codedictionary.globle.data.EntryDataCenter.COLUMN_NAMES;

/**
 * @author Langel
 * @ClassName GlobleUtils
 * @Description 工具类
 * @date 2020/8/14
 **/
public class GlobalUtils {

    private static final String DEFAULT_FILENAME = "Code Dictionary.md";
    private static final String SUFFIX = ".md";


    private GlobalUtils() {

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
                .map(GlobalUtils::formatEntryData)
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
                .map(GlobalUtils::formatEntryData)
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

    /**
     * 重新渲染表格
     */
    public static void reBuildTableModel(String[][] data) {
        EntryDataCenter.ENTRY_INFO_TABLE_MODEL = new DefaultTableModel(data, COLUMN_NAMES);
        EntryDataCenter.ENTRY_INFO_TABLE_MODEL.addTableModelListener(new TableModelListener()
        {
            @Override
            public void tableChanged(TableModelEvent e)
            {
                if (e.getType() == 0 && e.getFirstRow() == e.getLastRow()) {
                    DefaultTableModel source = (DefaultTableModel) e.getSource();
                    Vector dataVector = source.getDataVector();
                    Vector formatData = (Vector) dataVector.get(e.getFirstRow());
                    CodeDictionaryEntryData codeDictionaryEntryData = GlobalUtils.unformatEntryData(formatData);
                    GlobEntryDataCache.findById(codeDictionaryEntryData.getId()).ifPresent(o -> {
                        if (!ObjectUtil.equal(o.getName(), codeDictionaryEntryData.getName()) || !ObjectUtil.equal(o.getDesc(), codeDictionaryEntryData.getDesc()) ) {

                            GlobEntryDataCache.modifyNameDescById(codeDictionaryEntryData);

                            String dictionaryPath = SettingUtil.getDictionaryPath();
                            CodeDictionaryFileExporter.export(dictionaryPath);
                        }
                    });

                }
            }
        });
    }

    /**
     * 判断文件是否存在
     * @return
     */
    public static boolean isFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void initExport(Project project) {
        initExport(project, "");
    }

    /**
     * 导出文件
     * @param project
     * @param filename
     */
    public static void initExport(Project project, String filename) {
        if (StrUtil.isBlank(filename)) {
            filename = DEFAULT_FILENAME;
        }
        if (!filename.endsWith(SUFFIX)) {
            filename = filename + SUFFIX;
        }
        // 选择保存路径
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, ProjectUtil.guessProjectDir(project));
        if (ObjectUtil.isNotNull(virtualFile)) {
            String path = virtualFile.getPath();
            String realPath = path + File.separator + filename;
            CodeDictionaryFileExporter.export(realPath);
            SettingUtil.saveDictionaryPath(realPath);
        }
    }

}
