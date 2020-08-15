package cn.llanc.codedictionary.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author Langel
 * @ClassName CodeDictionaryWindowFactory
 * @Description 代码字典创建工厂
 * @date 2020/8/15
 **/
public class CodeDictionaryWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        CodeDictionaryWindow codeDictionaryWindow = new CodeDictionaryWindow(project, toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(codeDictionaryWindow.getFormPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
