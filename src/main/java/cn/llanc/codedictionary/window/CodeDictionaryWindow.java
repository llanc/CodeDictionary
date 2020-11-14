package cn.llanc.codedictionary.window;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.entity.ProcessorSourceData;
import cn.llanc.codedictionary.fileprocess.loader.CodeDictionaryFileLoader;
import cn.llanc.codedictionary.globle.data.EntryDataCenter;
import cn.llanc.codedictionary.globle.data.GlobEntryDataCache;
import cn.llanc.codedictionary.globle.utils.GlobleUtils;
import cn.llanc.codedictionary.fileprocess.FreeMarkProcessor;
import cn.llanc.codedictionary.globle.utils.SettingUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.JBSplitter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

import static cn.llanc.codedictionary.globle.data.EntryDataCenter.COLUMN_NAMES;

/**
 * @author Langel
 * @ClassName CodeDictionaryWindow
 * @Description 创建代码字典窗口
 * @date 2020/8/15
 **/
public class CodeDictionaryWindow {
    private JPanel formPanel;
    private JButton importDictionary;
    private JButton updateDictionary;
    private JButton thisTimeOnly;
    private JTextField entryNameQuerier;
    private JTable entryInfoTable;
    private EditorTextField entryContent;
    private JLabel querierLabel;
    private JScrollPane entryTreeScrollPane;
    private JPanel entryTreePanel;
    private JSplitPane splitPane;

    private static final String FILENAME = "代码词典.md";
    private static final String NOTIFICATION_GROUP_ID = "markbook_id";


    /**
     * 初始化表格
     */
    private void initTable(DefaultTableModel defaultTableModel) {
        entryInfoTable.setModel(defaultTableModel);
        entryInfoTable.setEnabled(true);
        GlobleUtils.hideTableColumn(entryInfoTable,2);
    }

    public CodeDictionaryWindow(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        splitPane.setContinuousLayout(true);
        entryContent.setOneLineMode(false);
        // 读取词典路径
        String dictionaryPath = SettingUtil.getDictionaryPath();
        if (StrUtil.isBlank(dictionaryPath)) {
            // 加载空表格
            EntryDataCenter.ENTRY_INFO_TABLE_MODEL = new DefaultTableModel(null, COLUMN_NAMES);
            initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
            bindEventListener(project);
            return;
        }

        // 从文件读取并加载
        CodeDictionaryFileLoader.loading(dictionaryPath);

        EntryDataCenter.ENTRY_INFO_TABLE_MODEL =  new DefaultTableModel(GlobleUtils.getEntryDataAsTableFormat(), COLUMN_NAMES);
        initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
        bindEventListener(project);
    }

    private void bindEventListener( Project project) {
        // 导入
        importDictionary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CodeDictionaryFileLoader.loading(project);
                DefaultTableModel searchTableModel = new DefaultTableModel(GlobleUtils.getEntryDataAsTableFormat(), COLUMN_NAMES);
                initTable(searchTableModel);
            }
        });
        // 保存
        updateDictionary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel model = (DefaultTableModel) entryInfoTable.getModel();
                EntryDataCenter.ENTRY_INFO_TABLE_MODEL.setDataVector( model.getDataVector(), new Vector(Arrays.asList(COLUMN_NAMES)));

                // 选择保存路径
                VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, ProjectUtil.guessProjectDir(project));
                if (ObjectUtil.isNotNull(virtualFile)) {
                    String path = virtualFile.getPath();
                    String realPath = path + File.separator + FILENAME;

                    ProcessorSourceData processorSourceData = new ProcessorSourceData(realPath,GlobEntryDataCache.getEntrySource());

                    FreeMarkProcessor mdFreeMarkProcessor = new FreeMarkProcessor();
                    try {
                        mdFreeMarkProcessor.process(processorSourceData);
                    } catch (Exception exception) {
                        NotificationGroup notificationGroup = new NotificationGroup(NOTIFICATION_GROUP_ID, NotificationDisplayType.BALLOON);
                        Notification notification = notificationGroup.createNotification("生成文档失败！" , MessageType.INFO);
                        Notifications.Bus.notify(notification);
                        exception.printStackTrace();
                    }
                    NotificationGroup notificationGroup = new NotificationGroup(NOTIFICATION_GROUP_ID, NotificationDisplayType.BALLOON);
                    Notification notification = notificationGroup.createNotification("生成文档成功：" + realPath, MessageType.INFO);
                    Notifications.Bus.notify(notification);
                }
            }
        });
        // 本次
        thisTimeOnly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                        initTable(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
                    }
                    DefaultTableModel searchTableModel = new DefaultTableModel(GlobleUtils.getEntryDataAsTableFormat(), COLUMN_NAMES);
                    initTable(searchTableModel);
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
                entryContent.setFileType(FileTypes.ARCHIVE);
                entryContent.setText(codeDictionaryEntryData.getContent());
            }
        });


        // entryInfoTable.getModel().addTableModelListener(new TableModelListener()
        // {
        //     int i = 0;
        //     @Override
        //     public void tableChanged(TableModelEvent e)
        //     {
        //
        //     }
        // });

    }


    public JPanel getFormPanel() {
        return formPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


}
