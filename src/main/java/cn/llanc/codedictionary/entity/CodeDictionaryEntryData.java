package cn.llanc.codedictionary.entity;

/**
 * @author Langel
 * @ClassName CodeDictionaryEntryData
 * @Description 代码词典条目数据对象
 * @date 2020/8/14
 **/
public class CodeDictionaryEntryData {

    /**
     * 条目名称
     */
    private String name;

    /**
     * 条目描述
     */
    private String desc;

    /**
     * 条目内容
     */
    private String content;

    /**
     * 内容格式
     */
    private String contentFormat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(String contentFormat) {
        this.contentFormat = contentFormat;
    }

    @Override
    public String toString() {
        return "CodeDictionaryEntryData{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", content='" + content + '\'' +
                ", contentFormat='" + contentFormat + '\'' +
                '}';
    }
}
