package cn.llanc.codedictionary.entity;

import java.util.List;

/**
 * @author Langel
 * @ClassName ProcessorSourceData
 * @Description 处理器源数据
 * @date 2020/8/24
 **/
public class ProcessorSourceData {
    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 条目数据
     */
    private List<CodeDictionaryEntryData> entryData;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<CodeDictionaryEntryData> getEntryData() {
        return entryData;
    }

    public void setEntryData(List<CodeDictionaryEntryData> entryData) {
        this.entryData = entryData;
    }

    public ProcessorSourceData() {
    }

    public ProcessorSourceData(String filePath, List<CodeDictionaryEntryData> entryData) {
        this.filePath = filePath;
        this.entryData = entryData;
    }
}