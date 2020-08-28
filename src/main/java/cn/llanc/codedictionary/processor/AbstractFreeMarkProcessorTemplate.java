package cn.llanc.codedictionary.processor;

import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.entity.ProcessorSourceData;
import freemarker.template.Template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * @author Langel
 * @ClassName AbstractFreeMarkProcessor
 * @Description 处理器接口的FreeMark版本抽象模板实现
 * @date 2020/8/24
 **/
public abstract class AbstractFreeMarkProcessorTemplate implements Processor{

    /**
     * 获取数据
     * @param sourceData
     * @return
     */
    abstract  Map<String,List<CodeDictionaryEntryData>>  getModel(ProcessorSourceData sourceData);

    /**
     * 获取模板
     * @return
     * @throws IOException
     */
    abstract Template getTemplate() throws IOException;

    /**
     * 获取写入器
     * @param sourceData
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    abstract Writer getWriter(ProcessorSourceData sourceData) throws FileNotFoundException, UnsupportedEncodingException;

    @Override
    public final void process(ProcessorSourceData processorSourceData) throws Exception{
        Template template = getTemplate();
        Object model = getModel(processorSourceData);
        Writer writer = getWriter(processorSourceData);
        template.process(model,writer);
    }
}
