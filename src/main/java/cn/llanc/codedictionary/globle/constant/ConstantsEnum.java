package cn.llanc.codedictionary.globle.constant;

/**
 * @author liulancong
 * @ClassName LableConstants
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

    private ConstantsEnum() {

    }

}
