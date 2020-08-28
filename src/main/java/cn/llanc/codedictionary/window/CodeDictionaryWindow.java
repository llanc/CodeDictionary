package cn.llanc.codedictionary.window;

import cn.hutool.core.util.ObjectUtil;
import cn.llanc.codedictionary.entity.ProcessorSourceData;
import cn.llanc.codedictionary.globle.data.EntryDataCenter;
import cn.llanc.codedictionary.globle.utils.GlobleUtils;
import cn.llanc.codedictionary.processor.FreeMarkProcessor;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.File;

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
    private JTextArea entryContent;
    private JLabel querierLabel;
    private JScrollPane entryTreeScrollPane;
    private JToolBar toolBar;
    private JPanel entryTreePanel;

    private static final String FILENAME = "代码词典.md";
    private static final String NOTIFICATION_GROUP_ID = "markbook_id";
    public void initTable() {
        entryInfoTable.setModel(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
        entryInfoTable.setEnabled(true);
        GlobleUtils.hideTableColumn(entryInfoTable,2);
    }

    public CodeDictionaryWindow(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        initTable();
        bindEventListener(project);
    }

    private void bindEventListener( Project project) {
        importDictionary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        updateDictionary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 选择保存路径
                VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, ProjectUtil.guessProjectDir(project));
                if (ObjectUtil.isNotNull(virtualFile)) {
                    String path = virtualFile.getPath();
                    String realPath = path + File.separator + FILENAME;

                    ProcessorSourceData processorSourceData = new ProcessorSourceData(realPath,EntryDataCenter.ENTRY_LIST);

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
        thisTimeOnly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        entryNameQuerier.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyTyped(e);
            }
        });

        entryInfoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(entryInfoTable.getValueAt(entryInfoTable.getSelectedRow(), 2).toString());
                entryContent.setText(entryInfoTable.getValueAt(entryInfoTable.getSelectedRow(), 2).toString());
            }
        });
    }


    public JPanel getFormPanel() {
        return formPanel;
    }
}
