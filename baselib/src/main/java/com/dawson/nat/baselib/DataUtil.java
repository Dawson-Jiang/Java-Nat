package com.dawson.nat.baselib;

import com.dawson.nat.baselib.bean.BaseBean;
import com.dawson.nat.baselib.bean.CmdConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 车辆信息 配置 状态本地与内存操作
 *
 * @author dawson
 */
public class DataUtil {
    private static final String CONFIG_PATH = Common.CLASS_PATH + File.separator;

    /**
     * 配置信息
     */
    private static final String CONFIG_FILE = CONFIG_PATH + "configs.json";

    private static List<CmdConfig> configs = new ArrayList<>();
    private static Gson gson = new Gson();

    static {

        init(CONFIG_FILE, configs, new TypeToken<List<CmdConfig>>() {
        }.getType());
    }

    private static <T> void init(String file, List<T> values, Type type) {
        if (values.size() == 0) {
            String vs = FileUtil.read(file);
            if (!vs.isEmpty()) {
                values.addAll(gson.fromJson(vs, type));
            }
        }
    }

    private static <T> void init(String path, List<T> values, Class<T> type) {
        if (values.size() == 0) {
            File file = new File(path);
            File[] fs = file.listFiles();
            if (fs == null || fs.length <= 0) {
                return;
            }
            for (File f : fs) {
                String vs = FileUtil.read(f.getAbsolutePath());
                if (!vs.isEmpty()) {
                    values.add(gson.fromJson(vs, type));
                }
            }
        }
    }


    public static List<CmdConfig> getConfigs() {
        return configs;
    }

    /**
     * 新增与修改数据
     *
     * @return 0失败 1新增成功 2修改成功
     */
    private static synchronized <T extends BaseBean> int save1(String file, List<T> values, T value) {
        int type;
        int index = values.indexOf(value);
        if (index != -1) {
            values.remove(index);
            type = 2;
        } else {
            if (value.getId() == null || value.getId().isEmpty()) {
                value.setId(UUID.randomUUID().toString());
            }
            type = 1;
        }
        values.add(value);
        String newJson = gson.toJson(values);
        boolean res = FileUtil.write(file, newJson);
        return res ? type : 0;
    }

    /**
     * 获取与查找制定数据
     */
    private static synchronized <T> T get(List<T> values, T value) {
        int index = values.indexOf(value);
        if (index != -1) {
            return values.get(index);
        } else {
            return null;
        }
    }

    public static CmdConfig getConfig(String cmd) {
        return getConfig(new CmdConfig(cmd));
    }

    public static CmdConfig getConfig(CmdConfig config) {
        return get(configs, config);
    }

}
