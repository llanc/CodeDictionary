package cn.llanc.codedictionary.fileprocess.loader;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.llanc.codedictionary.entity.CodeDictionaryEntryData;
import cn.llanc.codedictionary.globle.data.GlobEntryDataCache;
import cn.llanc.codedictionary.globle.utils.EventLogUtils;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Optional;

/**
 * 代码词典文件加载器
 *
 * @author liulancong
 * @date 2020/11/12
 **/
public class CodeDictionaryFileLoader {

    private static final String ERROR_MAG = "导入词典失败:";

    public static void loading(Project project) {
        Optional<InputStream> inputStreamOptional = loadFileAsStream(project);
        if (!inputStreamOptional.isPresent()) {
            return;
        }
        InputStream fileStream = inputStreamOptional.get();
        try (BufferedReader bufIn = new BufferedReader(new InputStreamReader(fileStream))) {
            String line;
            CodeDictionaryEntryData entryDataCache = null;
            StringBuilder contentCache = null;
            LinkedList<CodeDictionaryEntryData> entryDataList = new LinkedList<>();
            while ((line = bufIn.readLine()) != null) {
                if (line.startsWith("------") || "# 代码词典".equals(line) || "[TOC]".equals(line)) {
                    continue;
                }
                if (line.startsWith("## ")) {
                    entryDataCache = new CodeDictionaryEntryData();
                    entryDataCache.setName(line.substring(3));
                    continue;
                }
                if (ObjectUtil.isNull(entryDataCache)) {
                    continue;
                }
                if (line.startsWith("- ")) {
                    entryDataCache.setDesc(line.substring(2));
                    continue;
                }
                if (line.startsWith("```") && line.length() > 3) {
                    contentCache = new StringBuilder();
                    entryDataCache.setContentType(line.substring(3));
                    continue;
                }
                if (ObjectUtil.isNull(contentCache)) {
                    continue;
                }
                if ("```".equals(line)) {
                    entryDataCache.setContent(contentCache.toString());
                    entryDataCache.setId(IdUtil.fastSimpleUUID());
                    entryDataList.add(entryDataCache);
                    continue;
                }
                contentCache.append(line).append(System.getProperty("line.separator"));
            }
            GlobEntryDataCache.initGlobEntryDataCache(entryDataList);
            fileStream.close();
        } catch (IOException e) {
            // 判断数据缓存是否有数据
            EventLogUtils.exception(ERROR_MAG, e);
        }
    }


    /**
     * 词典分析器
     * @param line 当前解析行
     * @param contentCache  条目内容缓存
     * @param entryDataCache 条目缓存
     * @param entryDataList 条目集合
     */
    private static void entryAnalyzer(String line, StringBuilder contentCache, CodeDictionaryEntryData entryDataCache, LinkedList<CodeDictionaryEntryData> entryDataList) {
        if (line.startsWith("------") || "# 代码词典".equals(line) || "[TOC]".equals(line)) {
            return;
        }
        if (line.startsWith("## ")) {
            entryDataCache = new CodeDictionaryEntryData();
            entryDataCache.setName(line.substring(3));
            return;
        }
        if (ObjectUtil.isNull(entryDataCache)) {
            return;
        }
        if (line.startsWith("- ")) {
            entryDataCache.setDesc(line.substring(2));
            return;
        }
        if (line.startsWith("```") && line.length() > 3) {
            contentCache = new StringBuilder();
            entryDataCache.setContentType(line.substring(3));
            return;
        }
        if (ObjectUtil.isNull(contentCache)) {
            return;
        }
        if ("```".equals(line)) {
            entryDataCache.setContent(contentCache.toString());
            entryDataList.add(entryDataCache);
            return;
        }
        contentCache.append(line).append(System.getProperty("line.separator"));
    }


    /**
     * 以输入流的方式加载文件
     * @return
     */
    private static Optional<InputStream> loadFileAsStream(Project project) {
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileDescriptor("md"), project, null);
        InputStream stream = null;
        try {
            stream = virtualFile.getInputStream();
        } catch (IOException e) {
            EventLogUtils.exception(ERROR_MAG, e);
        }
        return Optional.ofNullable(stream);
    }

}
