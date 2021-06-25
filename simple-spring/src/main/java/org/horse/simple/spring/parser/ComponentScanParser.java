package org.horse.simple.spring.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.horse.simple.spring.annotation.ComponentScan;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * ComponentScan注解解析类
 *
 * @author horse
 * @date 2021/6/22
 */
public class ComponentScanParser {
    /**
     * class文件后缀
     */
    private static final String CLASS_SUFFIX = ".class";

    /**
     * 获取被扫描路径下的所有类对象
     *
     * @param configClass 启动配置类
     * @return List<Class < ?>>
     * @throws ClassNotFoundException 异常
     */
    public static List<Class<?>> findScanClassSet(Class<?> configClass) throws ClassNotFoundException {
        // 获取扫描包的全路径
        List<String> scanPathList = findScanPackageList(configClass);
        // 获取被扫描包下的所有类的全路径名
        Set<String> classNameSet =
                parseFileToClassName(Objects.requireNonNull(configClass.getClassLoader().getResource("")).getPath().length(), scanPathList);
        // 将全路径名转化为 class 对象
        List<Class<?>> classList = Lists.newArrayList();
        for (String className : classNameSet) {
            classList.add(Class.forName(className));
        }
        return classList;
    }

    /**
     * 获取被扫描包下所有类的全路径名
     *
     * @param length       文件路径中到classes文件下的路径长度
     * @param scanPathList 扫描路径列表
     * @return 全路径列表
     */
    private static Set<String> parseFileToClassName(int length, List<String> scanPathList) {
        Set<String> classNameSet = Sets.newHashSet();
        // 遍历所有被扫描路径
        for (String path : scanPathList) {
            File file = new File(path);
            // 文件不存在 直接返回
            if (!file.exists()) {
                continue;
            }
            // 字节码文件进行处理
            if (file.isFile() && file.getName().endsWith(CLASS_SUFFIX)) {
                String filePath = file.getPath();
                String className = filePath.substring(length).replaceAll(File.separator, "\\.");
                classNameSet.add(className.substring(0, className.lastIndexOf(".")));
                continue;
            }
            // 文件夹
            File[] subFiles = file.listFiles();
            // file不是文件夹，跳过
            if (subFiles == null) {
                continue;
            }
            // 遍历子文件
            for (File subFile : subFiles) {
                // 如果是文件夹继续递归
                if (subFile.isDirectory()) {
                    classNameSet.addAll(parseFileToClassName(length, Lists.newArrayList(subFile.getPath())));
                }
                // 如果是文件则判断是否是字节码文件
                if (subFile.getName().endsWith(CLASS_SUFFIX)) {
                    String subFilePath = subFile.getPath();
                    String className = subFilePath.substring(length).replaceAll(File.separator, "\\.");
                    classNameSet.add(className.substring(0, className.lastIndexOf(".")));
                }
            }
        }
        return classNameSet;
    }

    /**
     * 获取扫描包的全路径
     *
     * @param configClass 启动配置类
     * @return 扫描包的全路径
     */
    private static List<String> findScanPackageList(Class<?> configClass) {
        List<String> scanPaths = Lists.newArrayList();
        // 获取配置类的注解
        ComponentScan componentScanAnno = configClass.getAnnotation(ComponentScan.class);
        // 没有注解抛出异常并停止程序
        if (componentScanAnno == null) {
            throw new RuntimeException("start up spring context must set a configuration annotated by ComponentScan");
        }
        // 默认配置，全包扫描
        if (componentScanAnno.value().length == 1 && StringUtils.isEmpty(componentScanAnno.value()[0])) {
            String path = Objects.requireNonNull(configClass.getClassLoader().getResource("")).getPath();
            scanPaths.add(path);
            return scanPaths;
        }
        // 仅扫描配置包
        for (String path : componentScanAnno.value()) {
            path = path.replaceAll("\\.", File.separator);
            scanPaths.add(Objects.requireNonNull(configClass.getClassLoader().getResource(path)).getPath());
        }
        return scanPaths;
    }
}
