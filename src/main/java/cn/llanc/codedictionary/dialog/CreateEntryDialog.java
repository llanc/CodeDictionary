package cn.llanc.codedictionary.dialog;

import cn.hutool.core.util.StrUtil;
import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.fileprocess.exporter.CodeDictionaryFileExporter;
import cn.llanc.codedictionary.globle.constant.ConstantsEnum.CreateEntry;
import cn.llanc.codedictionary.globle.data.EntryDataCenter;
import cn.llanc.codedictionary.globle.data.GlobEntryDataCache;
import cn.llanc.codedictionary.globle.data.PluginContext;
import cn.llanc.codedictionary.globle.utils.GlobalUtils;
import cn.llanc.codedictionary.globle.utils.SettingUtil;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.EditorTextField;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
     * 条目类型
     */
    private JComboBox entryContentType;
    /**
     * 条目解释
     */
    private EditorTextField entryDesc;

    /**
     * 代码段
     */
    private JTextArea entryContent;

    /**
     * 上下文
     */
    private PluginContext context;




    /**
     * 构造方法，指定title
     */
    public CreateEntryDialog(PluginContext context) {
        super(true);
        this.context = context;
        init();
        setTitle(CreateEntry.TITLE.getValue());
        // 绑定事件
        bindFocusListener4EntryDialog();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        createComponent();

        return drawDialog();
    }

    /**
     * 创建面板组件
     */
    private void createComponent() {
        initEntryName();
        initEntryType();
        initEntryDesc();
        initEntryContent();
    }


    /**
     * 确认操作
     */
    @Override
    protected void doOKAction() {
        CodeDictionaryEntryData codeDictionaryEntryData = new CodeDictionaryEntryData(entryName.getText(), entryDesc.getText(), entryContent.getText(), EntryDataCenter.ENTRY_CONTENT_TYPE);
        GlobEntryDataCache.addEntry(codeDictionaryEntryData);
        EntryDataCenter.ENTRY_INFO_TABLE_MODEL.insertRow(0,getNewEntryRow(codeDictionaryEntryData));
        super.doOKAction();

        //Auto Save
        String dictionaryPath = SettingUtil.getDictionaryPath();
        if (StrUtil.isBlank(dictionaryPath) || !GlobalUtils.isFileExists(dictionaryPath)) {
            GlobalUtils.initExport(context.getProject());
        }else{
            CodeDictionaryFileExporter.export(dictionaryPath);
        }
    }

    private String[] getNewEntryRow(CodeDictionaryEntryData data) {
        return new String[]{data.getName(), data.getDesc(),data.getId()};
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
                }
            }
        });

        entryContentType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                EntryDataCenter.ENTRY_CONTENT_TYPE = entryContentType.getSelectedItem().toString();
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
                }
            }
        });
    }

    /**
     * 初始化条目框
     */
    private void initEntryName() {
        entryName = new JTextField(CreateEntry.NAME_TEXT_PLACEHOLDER.getValue());
        entryName.setPreferredSize(new Dimension(320, 0));
        entryName.setMinimumSize(new Dimension(320, 0));
    }

    /**
     * 初始化类型笔条目类型
     */
    private void initEntryType() {
        DefaultComboBoxModel<String> entryTypeValue = new DefaultComboBoxModel<>(GlobalUtils.EntryTypeGetter());
        entryContentType = new JComboBox<>(entryTypeValue);
        entryTypeValue.setSelectedItem(EntryDataCenter.ENTRY_CONTENT_TYPE);
    }

    /**
     * 初始化描述框
     */
    private void initEntryDesc() {
        entryDesc = new EditorTextField(CreateEntry.DESC_TEXT_PLACEHOLDER.getValue());
        entryDesc.setOneLineMode(false);
    }

    /**
     * 初始化条内容
     */
    private void initEntryContent() {
        entryContent = new JTextArea(EntryDataCenter.ENTRY_CONTENT);
    }

    /**
     * 绘制面板
     * @return
     */
    private JPanel drawDialog() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        // entryName entryType pane
        JPanel entryNorthPane = new JPanel(new BorderLayout());
        entryNorthPane.add(entryName, BorderLayout.WEST);
        entryNorthPane.add(entryContentType, BorderLayout.EAST);

        // entryDesc ScrollPane
        JBScrollPane descScrollPane = new JBScrollPane(entryDesc);
        descScrollPane.setPreferredSize(new Dimension(400,50));
        descScrollPane.setMinimumSize(new Dimension(400,50));
        descScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        descScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // entryContent ScrollPane
        JBScrollPane entryContentScrollPane = new JBScrollPane(entryContent);
        entryContentScrollPane.setPreferredSize(new Dimension(400,200));
        entryContentScrollPane.setMinimumSize(new Dimension(400,200));
        entryContentScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        entryContentScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // add to dialog
        panel.add(entryNorthPane, BorderLayout.NORTH);
        panel.add(descScrollPane, BorderLayout.CENTER);
        panel.add(entryContentScrollPane, BorderLayout.SOUTH);
        return panel;
    }

}
