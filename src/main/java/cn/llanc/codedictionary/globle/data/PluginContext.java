package cn.llanc.codedictionary.globle.data;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author liulancong
 * @date 2021-04-16
 **/
public class PluginContext {
    private AnActionEvent actionEvent;
    private Project project;

    public AnActionEvent getActionEvent() {
        return actionEvent;
    }

    public void setAction(AnActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PluginContext(Builder builder) {
        this.actionEvent = builder.actionEvent;
        this.project = builder.project;
    }

    public static class Builder{
        private  AnActionEvent actionEvent;
        private  Project project;

        public Builder() {
        }

        public Builder addAnAction(AnActionEvent actionEvent) {
            this.actionEvent = actionEvent;
            return this;
        }
        public Builder addProject(Project project) {
            this.project = project;
            return this;
        }

        public PluginContext build() {
            return new PluginContext(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
