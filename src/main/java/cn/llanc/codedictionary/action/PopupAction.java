package cn.llanc.codedictionary.action;

import cn.llanc.codedictionary.dialog.CreateEntryDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

public class PopupAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取编辑器对象
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        // 选取的数据模型
        SelectionModel selectionModel = editor.getSelectionModel();
        // 选取的文本对象
        String selectedText = selectionModel.getSelectedText();
        System.out.println(selectedText);
        // 创建条目
        new CreateEntryDialog(selectedText).show();
    }
}
