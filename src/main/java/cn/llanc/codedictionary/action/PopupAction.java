package cn.llanc.codedictionary.action;

import cn.llanc.codedictionary.dialog.CreateEntryDialog;
import cn.llanc.codedictionary.globle.constant.ConstantsEnum;
import cn.llanc.codedictionary.globle.data.EntryDataCenter;
import cn.llanc.codedictionary.globle.utils.GlobalUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

public class PopupAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        EntryDataCenter.ENTRY_CONTENT = getSelectedText(e);
        EntryDataCenter.ENTRY_CONTENT_TYPE = getEntryType(e);

        // 创建条目
        new CreateEntryDialog().show();
    }

    /**
     * 获取选中内容
     * @param e
     * @return
     */
    private String getSelectedText(AnActionEvent e) {
        // 获取编辑器对象
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        // 选取的数据模型
        SelectionModel selectionModel = editor.getSelectionModel();
        return selectionModel.getSelectedText();
    }

    /**
     * 获取条目类型
     * @param e
     * @return
     */
    private String getEntryType(AnActionEvent e) {
        String fileName = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        for (String s : GlobalUtils.EntryTypeGetter()) {
            if (s.equals(fileExtensionName)) {
                return fileExtensionName;
            }
        }
        return ConstantsEnum.EntryType.TXT.getValue();
    }
}
