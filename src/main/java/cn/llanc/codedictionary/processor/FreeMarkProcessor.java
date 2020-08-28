package cn.llanc.codedictionary.processor;

import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.entity.ProcessorSourceData;
import com.intellij.ide.fileTemplates.impl.UrlUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Langel
 * @ClassName MDFreeMarkProcessor
 * @Description FreeMark处理器
 * @date 2020/8/24
 **/
public class FreeMarkProcessor extends AbstractFreeMarkProcessorTemplate {

    /**
     * 模板名
     */
    private static final String TEMPLATE_NAME = "codeDictionaryTemplate";
    /**
     * ftl文件路径
     */
    private static final String FTL_PATH = "/template/codeDictionary.ftl";

    @Override
    Map<String,List<CodeDictionaryEntryData>>  getModel(ProcessorSourceData sourceData) {
        Map<String, List<CodeDictionaryEntryData>> model = new HashMap<>();
        model.put("entryData", sourceData.getEntryData());
        return model;
    }

    @Override
    Template getTemplate() throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        String templateContent = UrlUtil.loadText(FreeMarkProcessor.class.getResource(FTL_PATH));
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate(TEMPLATE_NAME, templateContent);
        configuration.setTemplateLoader(stringTemplateLoader);
        return configuration.getTemplate(TEMPLATE_NAME);
    }

    @Override
    Writer getWriter(ProcessorSourceData sourceData) throws FileNotFoundException {
        return new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(
                                new File(sourceData.getFilePath())), StandardCharsets.UTF_8));
    }
}
