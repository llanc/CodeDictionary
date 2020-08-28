package cn.llanc.codedictionary.processor;

import cn.llanc.codedictionary.entity.ProcessorSourceData;

/**
 * @author Langel
 * @ClassName Processor
 * @Description 处理器接口
 * @date 2020/8/24
 **/
public interface Processor {
    void process(ProcessorSourceData processorSourceData) throws Exception;
}
