package org.qvit.hybrid.enums;

public enum InvalidStatus {
    /** 打款失败 */
    INVALID(1, "无效"),
    /** 打款成功 */
    EFFECTIVE(2, "有效");
    
    private Integer value;
    private String desc;

    private InvalidStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static InvalidStatus getEnum(Integer value) {
        if (null == value) {
            return null;
        }
        InvalidStatus[] values = values();
        for (InvalidStatus loanPayStatus : values) {
            if (loanPayStatus.getValue().equals(value)) {
                return loanPayStatus;
            }
        }
        return null;
    }
}
