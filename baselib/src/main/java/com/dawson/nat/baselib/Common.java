package com.dawson.nat.baselib;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author dawson
 */
public class Common {
    /**
     * 配置信息 classes文件夹路径
     */
//    public final static String CLASS_PATH = System.getProperty("user.dir") + File.separator;
//    public final static String CLASS_PATH = System.getProperty("java.class.path") + File.separator;
    public static String CLASS_PATH;

    static {
        String str = Common.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        GLog.println("CLASS_PATH get str:" + str);

        //使用该方法将/D:/asd/asd 转换为 D:\asd\asd
        File file = new File(str);
        try {
            Path root = Paths.get(file.getCanonicalPath()).getRoot();
            str = file.getCanonicalPath();
        } catch (IOException e) {
            GLog.println(e.getMessage());
        }
        Path path = Paths.get(str);
        if (!path.isAbsolute()) {
            path = path.toAbsolutePath();
        }
        CLASS_PATH = path.getParent().toString() + File.separator;
    }

    public static boolean isEmpty(final String str) {
        return str == null || "".equals(str);
    }

    public static String creatUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
