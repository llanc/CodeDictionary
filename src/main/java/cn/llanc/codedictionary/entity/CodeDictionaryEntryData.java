package cn.llanc.codedictionary.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import org.jsoup.helper.DataUtil;

import java.text.DateFormat;
import java.util.Date;

/**
 * @author Langel
 * @ClassName CodeDictionaryEntryData
 * @Description 代码词典条目数据对象
 * @date 2020/8/14
 **/
public class CodeDictionaryEntryData {

    private String id;

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
     * 条目类别
     */
    private String contentType;

    /**
     * 最近修改时间
     */
    private Date recentModifyData;

    private String recentModifyDataStr;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public CodeDictionaryEntryData() {
    }

    public Date getRecentModifyData() {
        return recentModifyData;
    }

    public void setRecentModifyData(Date recentModifyData) {
        this.recentModifyData = recentModifyData;
        this.recentModifyDataStr = DateUtil.format(recentModifyData, "yyyy-MM-dd hh:mm:ss");
    }

    public String getRecentModifyDataStr() {
        return recentModifyDataStr;
    }

    public CodeDictionaryEntryData(String name, String desc, String content, String contentType) {
        this.id = IdUtil.fastSimpleUUID();
        this.name = name;
        this.desc = desc;
        this.content = content;
        this.contentType = contentType;
        this.recentModifyData = DateUtil.date();
        this.recentModifyDataStr = DateUtil.formatDateTime(recentModifyData);
    }

    @Override
    public String toString() {
        return "CodeDictionaryEntryData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", content='" + content + '\'' +
                ", contentType='" + contentType + '\'' +
                ", recentModifyData='" + recentModifyData + '\'' +
                '}';
    }
}
