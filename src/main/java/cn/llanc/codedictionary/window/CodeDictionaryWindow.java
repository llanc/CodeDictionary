package cn.llanc.codedictionary.window;

import cn.llanc.codedictionary.globle.data.EntryDataCenter;
import cn.llanc.codedictionary.globle.utils.GlobleUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * @author liulancong
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

    public void initTable() {
        entryInfoTable.setModel(EntryDataCenter.ENTRY_INFO_TABLE_MODEL);
        entryInfoTable.setEnabled(true);
        GlobleUtils.hideTableColumn(entryInfoTable,2);
    }

    public CodeDictionaryWindow(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        initTable();
        bindEventListener();
    }

    private void bindEventListener() {
        importDictionary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        updateDictionary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
