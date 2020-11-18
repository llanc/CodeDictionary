package cn.llanc.codedictionary.globle.constant;

/**
 * @author Langel
 * @ClassName ConstantsEnum
 * @Description 常量枚举类
 * @date 2020/8/13
 **/
public class ConstantsEnum {

    /**
     * 创建条目
     */
    public enum CreateEntry {

        /**
         * 弹窗标题
         */
        TITLE("新建条目"),
        /**
         * 条目标题
         */
        NAME_TEXT_PLACEHOLDER("--条目标题--"),
        /**
         * 条目解释
         */
        DESC_TEXT_PLACEHOLDER("--条目解释--");

        private final String value;

        CreateEntry(String constant) {
            this.value = constant;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 条目类型
     */
    public enum EntryType{
        JAVA("java"),
        SQL("sql"),
        XML("xml"),
        HTML("html"),
        CSS("css"),
        JS("js"),
        SHELL("shell"),
        JSON("json"),
        TXT("txt"),
        PYTHON("python"),
        VUE("vue"),
        TYPESCRIPT("typescript"),
        C("c"),
        C_PLUS("c++"),
        SCALA("scala"),
        ;
        private final String value;

        EntryType(String entryType) {
            this.value = entryType;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 条目类型
     */
    public enum EntryInfoTable{
        NAME("名称"),
        DESC("解释"),
        ID("id"),
        ;
        private final String value;

        EntryInfoTable(String entryType) {
            this.value = entryType;
        }

        public String getValue() {
            return value;
        }
    }

    private ConstantsEnum() {

    }

}
