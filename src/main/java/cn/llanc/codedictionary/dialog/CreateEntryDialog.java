package cn.llanc.codedictionary.dialog;

import cn.hutool.core.util.StrUtil;
import cn.llanc.codedictionary.globle.constant.ConstantsEnum.CreateEntry;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author Langel
 * @ClassName CreateEntryDialog
 * @Description 创建条目弹窗
 * @date 2020/8/13
 **/
public class CreateEntryDialog extends DialogWrapper {

    /**
     * 条目名称
     */
    private JTextField entryName;

    /**
     * 条目解释
     */
    private JTextArea entryDesc;

    /**
     * 代码段
     */
    private JTextArea entryContent;

    /**
     * 条目解释滚动面板
     */
    private JBScrollPane descScrollPane;

    /**
     * 条目解释滚动面板
     */
    private JBScrollPane codeScrollPane;



    /**
     * 构造方法，指定title
     * @param selectedText 选中的代码段
     */
    public CreateEntryDialog(String selectedText) {
        super(true);
        initEntryName();
        initEntryDesc();
        initEntryCode(selectedText);
        init();
        setTitle(CreateEntry.TITLE.getValue());
        // 绑定事件
        bindFocusListener4EntryDialog();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.add(entryName, BorderLayout.NORTH);
        panel.add(descScrollPane, BorderLayout.CENTER);
        panel.add(codeScrollPane, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * 为创建词条弹窗控件绑定事件
     */
    private void bindFocusListener4EntryDialog() {
        entryName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (CreateEntry.NAME_TEXT_PLACEHOLDER.getValue().equals(entryName.getText())) {
                    entryName.setText("");
                    entryName.setForeground(JBColor.black);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (StrUtil.isBlank(entryName.getText())) {
                    entryName.setText(CreateEntry.NAME_TEXT_PLACEHOLDER.getValue());
                    entryName.setForeground(JBColor.lightGray);
                }
            }
        });

        entryDesc.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (CreateEntry.DESC_TEXT_PLACEHOLDER.getValue().equals(entryDesc.getText())) {
                    entryDesc.setText("");
                    entryDesc.setForeground(JBColor.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (StrUtil.isBlank(entryDesc.getText())) {
                    entryDesc.setText(CreateEntry.DESC_TEXT_PLACEHOLDER.getValue());
                    entryDesc.setForeground(JBColor.lightGray);
                }
            }
        });
    }

    /**
     * 初始化条目框
     */
    private void initEntryName() {
        entryName = new JTextField(CreateEntry.NAME_TEXT_PLACEHOLDER.getValue());
        entryName.setForeground(JBColor.lightGray);
    }
    /**
     * 初始化描述框
     */
    private void initEntryDesc() {
        entryDesc = new JTextArea(CreateEntry.DESC_TEXT_PLACEHOLDER.getValue());
        entryDesc.setForeground(JBColor.lightGray);
        descScrollPane = new JBScrollPane(entryDesc);
        descScrollPane.setPreferredSize(new Dimension(400,50));
        descScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * 初始化条目代码
     * @param selectedText 选中的代码段
     */
    private void initEntryCode(String selectedText) {
        entryContent = new JTextArea(selectedText);
        codeScrollPane = new JBScrollPane(entryContent);
        codeScrollPane.setPreferredSize(new Dimension(400,200));
        codeScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        codeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

}
