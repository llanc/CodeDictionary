package cn.llanc.codedictionary.fileprocess.exporter;

import cn.llanc.codedictionary.entity.ProcessorSourceData;
import cn.llanc.codedictionary.fileprocess.FreeMarkProcessor;
import cn.llanc.codedictionary.globle.data.GlobEntryDataCache;
import cn.llanc.codedictionary.globle.utils.EventLogUtils;

public class CodeDictionaryFileExporter {


    /**
     * 导出器
     * @param pathWithFileName
     */
    public static void export (String pathWithFileName) {
        ProcessorSourceData processorSourceData = new ProcessorSourceData(pathWithFileName, GlobEntryDataCache.getEntrySource());

        FreeMarkProcessor mdFreeMarkProcessor = new FreeMarkProcessor();
        try {
            mdFreeMarkProcessor.process(processorSourceData);
        } catch (Exception exception) {
            EventLogUtils.error("Unknown error, please try again");
        }
        EventLogUtils.info("Saved");
    }
}
