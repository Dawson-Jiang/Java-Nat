package com.dawson.nat.baselib.bean;

import java.util.Arrays;
import java.util.List;

/**
 * @author dawson
 * date:2018/12/8
 */
public class CommonBean {

    public static final String RES_SUCCESS = "success";
    public static final String RES_FAILED = "failed";


    /**
     * 控制命令相关常量
     */
    public static class ControlTypeConst {
        /**
         * 终端注册信息
         */
        public static final byte TYPE_REG_INFO = 1;
        /**
         * 新客户端连接请求
         */
        public static final byte TYPE_NEW_CONN = 2;
        /**
         * 获取注册的终端
         */
        public static final byte TYPE_GET_TERMINALS = 3;
        /**
         * 获取支持的命令配置
         */
        public static final byte TYPE_GET_CMD_CONFIGS = 4;

    }

    public static class SessionStateConst {
        /**
         * 准备就绪
         */
        public static final String STATE_READY = "ready";
        /**
         * 运行中
         */
        public static final String STATE_RUNNING = "running";
        /**
         * 已经结束关闭
         */
        public static final String STATE_CLOSE = "close";
    }

    /**
     * 特殊告警ID
     */
    public static class AlarmIDS {
        /**
         * PAD连接告警 需要忽略
         */
        public static final int PAD_CONNECT = 0x020A0253;
        /**
         * 自定义告警  由超声波障碍物转为告警
         */
//        public static final long OBSTACLE = 0x030100001;

        /**
         * 自定义告警  由自动驾驶转为告警
         */
//        public static final long AUTO_SATE = 0x030100002;

        public static final long GEOFENCE_WARN = 0x0251A000; //geofence forbiden region  occure
        public static final long GEOFENCE_ERROR = 0x0251A001; //geofence forbiden region  occure


        public static final int BMS_LOW_POWER_ERROR = 0x020A00F2; //chassis bms ultra low power
        public static final int BMS_LOW_POWER_WARN = 0x020A0081; //chassis bms low power

        public static final int GPS_NO_SIGNAL = 0x02510005; //gps no signal
        public static final int GPS_SIGNAL_WEAK = 0x02510006; //gps  signal weak

        /**
         * 不影响手动和自动驾驶的告警
         */
        public static final List<Long> NO_EFFECT_AUTO_AND_MAN = Arrays.asList(0x01010008L,
                0x0101000FL, 0x01010011L, 0x01040003L, 0x01040004L, 0x01040005L, 0x01040006L,
                0x01040007L, 0x01040008L, 0x01040009L, 0x0104000AL, 0x0104000BL, 0x0104000CL,
                0x0104000DL, 0x0104000EL, 0x0104000FL, 0x01040010L, 0x01040011L, 0x01040012L,
                0x01060000L, 0x01060001L, 0x01060002L, 0x01060003L, 0x01060004L, 0x01060017L,

                0x02010021L, 0x02010051L, 0x02013001L, 0x02013011L, 0x02013021L, 0x02013031L,
                0x02013041L, 0x02013051L, 0x02013061L, 0x02013071L, 0x02013081L, 0x02013091L,
                0x020130A1L, 0x020130B1L, 0x020130C1L, 0x020130D1L, 0x020130E1L, 0x020130F1L,
                0x02013101L, 0x02013111L, 0x02013121L, 0x02013131L, 0x02013141L, 0x02013151L,
                0x02013161L, 0x02013171L, 0x02016001L, 0x02016011L, 0x02016021L, 0x02016031L,
                0x02016041L, 0x02016051L, 0x02016061L, 0x02016071L, 0x02016081L, 0x02016091L,
                0x020160A1L, 0x020160B1L, 0x020160C1L, 0x020160D1L, 0x020160E1L, 0x020160F1L,
                0x02016101L, 0x02016111L, 0x02016121L, 0x02016131L, 0x02016141L, 0x02016151L,
                0x02016161L, 0x02016171L, 0x02017001L, 0x02017011L, 0x02017021L, 0x02017031L,
                0x02017041L, 0x02017051L, 0x0201D001L, 0x0201D011L, 0x02090000L, 0x02090001L,
                0x02090010L, 0x02090011L, 0x02090020L, 0x02090021L,

                0x02510003L, 0x02510005L, 0x02511001L, 0x02511004L, 0x02511005L, 0x02514000L,
                0x02515000L, 0x02517002L, 0x02517008L, 0x02517009L, 0x02519003L, 0x0251A000L);
    }


    /**
     * 自动驾驶状态
     */
    public static class AutoStateConst {
        /**
         * 不可自动驾驶
         */
        public static final int DISABLE = 0;


        /**
         * 起点
         */
        public static final int START = 1;

        /**
         * 在自动驾驶路线中
         */
        public static final int IN = 2;

        /**
         * 不在自动驾驶路线中
         */
        public static final int OUT = 3;

        /**
         * 终点
         */
        public static final int END = 4;
        /**
         * 离终点20米内
         */
        public static final int NEAR_END = 5;
    }

    /**
     * 主动安全 避障
     */
    public static class ObstacleConst {
        /**
         * 障碍物方位 前方
         */
        public static final String OBSTACLE_FORWARD = "OBSTACLE_FORWARD";
        /**
         * 障碍物方位 右方
         */
        public static final String OBSTACLE_RIGHT = "OBSTACLE_RIGHT";
        /**
         * 障碍物方位 后方
         */
        public static final String OBSTACLE_BACK = "OBSTACLE_BACK";
        /**
         * 障碍物方位 左方
         */
        public static final String OBSTACLE_LEFT = "OBSTACLE_LEFT";
    }

    /**
     * 地理围栏
     */
    public static class GeofenceConst {
        /**
         * 可通行区
         */
        public static final int REGION_PASS = 0;
        /**
         * 警示区
         */
        public static final int REGION_WARN = 1;
        /**
         * 可后退禁止区
         */
        public static final int REGION_HALF_FORBIDDEN = 2;
        /**
         * 完全禁止区
         */
        public static final int REGION_FORBIDDEN = 3;
    }
}
