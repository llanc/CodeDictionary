package cn.llanc.codedictionary.window;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.llanc.codedictionary.dialog.CreateEntryDialog;
import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.fileprocess.exporter.CodeDictionaryFileExporter;
import cn.llanc.codedictionary.fileprocess.loader.CodeDictionaryFileLoader;
import cn.llanc.codedictionary.globle.constant.ConstantsEnum;
import cn.llanc.codedictionary.globle.data.EntryDataCenter;
import cn.llanc.codedictionary.globle.data.GlobEntryDataCache;
import cn.llanc.codedictionary.globle.data.PluginContext;
import cn.llanc.codedictionary.globle.utils.GlobalUtils;
import cn.llanc.codedictionary.globle.utils.SettingUtil;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Optional;

/**
 * @author Langel
 * @ClassName CodeDictionaryWindow
 * @Description 创建代码字典窗口
 * @date 2020/8/15
 **/
public class CodeDictionaryWindow {
    private JPanel formPanel;
    private JButton importDictionary;
    private JButton addEntry;
    private JButton deleteEntry;
    private JTextField entryNameQuerier;
    private JTable entryInfoTable;
    private EditorTextField entryContent;
    private JLabel querierLabel;
    private JScrollPane entryTreeScrollPane;
    private JPanel entryTreePanel;
    private JSplitPane splitPane;

    private static String currentId;



    /**
     * 初始化表格
     */
    private void initTable(DefaultTableModel defaultTableModel) {
        entryInfoTable.setModel(defaultTableModel);
        entryInfoTable.setEnabled(true);
        GlobalUtils.hideTableColumn(entryInfoTable,2);
    }

    public CodeDictionaryWindow(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        splitPane.setContinuousLayout(true);
        entryContent.setOneLineMode(false);
        // 读取词典路径
        String dictionaryPath = SettingUtil.getDictionaryPath();
        if (StrUtil.isBlank(dictionaryPath) || !GlobalUtils.isFileExists(dictionaryPath)) {
            // 加载空表格
            GlobalUtils.reBuildTableModel(null);
            initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
            bindEventListener(project);
            return;
        }

        // 从文件读取并加载
        CodeDictionaryFileLoader.loading(dictionaryPath);
        GlobalUtils.reBuildTableModel(GlobalUtils.getEntryDataAsTableFormat());
        initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
        bindEventListener(project);
    }

    private void bindEventListener( Project project) {
        // 导入
        importDictionary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CodeDictionaryFileLoader.loading(project);
                GlobalUtils.reBuildTableModel(GlobalUtils.getEntryDataAsTableFormat());
                initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
            }
        });
        // 新增
        addEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PluginContext context = PluginContext.builder().addProject(project).build();
                EntryDataCenter.ENTRY_CONTENT = "";
                EntryDataCenter.ENTRY_CONTENT_TYPE = ConstantsEnum.EntryType.TXT.getValue();
                new CreateEntryDialog(context).show();
            }
        });
        // 删除
        deleteEntry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GlobEntryDataCache.removeById(currentId);
                GlobalUtils.reBuildTableModel(GlobalUtils.getEntryDataAsTableFormat());
                initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
                String dictionaryPath = SettingUtil.getDictionaryPath();
                CodeDictionaryFileExporter.export(dictionaryPath);
            }
        });
        // 查询框
        entryNameQuerier.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
                String searchText = entryNameQuerier.getText().trim();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (StrUtil.isBlank(searchText)) {
                        GlobalUtils.reBuildTableModel(GlobalUtils.getEntryDataAsTableFormat());
                        initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
                    }
                    GlobalUtils.reBuildTableModel(GlobalUtils.getEntryDataAsTableFormatForSearch(searchText));
                    initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
                }
            }
        });

        // 条目选中
        entryInfoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (entryInfoTable.getSelectedRow() == -1) {
                    return;
                }
                Optional<CodeDictionaryEntryData> content = GlobEntryDataCache.findById(entryInfoTable.getValueAt(entryInfoTable.getSelectedRow(), 2).toString());
                if (!content.isPresent()) {
                    return;
                }
                CodeDictionaryEntryData codeDictionaryEntryData = content.get();
                currentId = codeDictionaryEntryData.getId();
                entryContent.setFileType(FileTypes.ARCHIVE);
                entryContent.setText(codeDictionaryEntryData.getContent());
            }
        });

        entryContent.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = entryContent.getText();
                Optional<CodeDictionaryEntryData> dataOptional = GlobEntryDataCache.findById(currentId);
                if (!dataOptional.isPresent()) {
                    return;
                }
                CodeDictionaryEntryData codeDictionaryEntryData = dataOptional.get();
                GlobEntryDataCache.findById(codeDictionaryEntryData.getId()).ifPresent(o -> {
                    if (!ObjectUtil.equal(o.getContent(), text)) {
                        codeDictionaryEntryData.setContent(text);
                        GlobEntryDataCache.modifyById(codeDictionaryEntryData);

                        String dictionaryPath = SettingUtil.getDictionaryPath();
                        CodeDictionaryFileExporter.export(dictionaryPath);
                    }
                });
            }
        });
    }


    public JPanel getFormPanel() {
        return formPanel;
    }

    private void createUIComponents() {

    }


}
