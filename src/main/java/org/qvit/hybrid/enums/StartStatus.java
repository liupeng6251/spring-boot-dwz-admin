package org.qvit.hybrid.enums;

public enum StartStatus {
    /** 打款失败 */
    INVALID(1, "关闭"),
    /** 打款成功 */
    EFFECTIVE(2, "开启");
    
    private Integer value;
    private String desc;

    private StartStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static StartStatus getEnum(Integer value) {
        if (null == value) {
            return null;
        }
        StartStatus[] values = values();
        for (StartStatus loanPayStatus : values) {
            if (loanPayStatus.getValue().equals(value)) {
                return loanPayStatus;
            }
        }
        return null;
    }
}
