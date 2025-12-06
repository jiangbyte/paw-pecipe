package io.jiangbyte.framework.utils;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * 密码强度评估工具类
 * 强度等级说明：
 * 1 - 弱：只包含单一字符类型（纯数字、纯小写字母、纯大写字母）
 * 2 - 中：包含两种字符类型（数字+字母，字母+符号等）
 * 3 - 强：包含三种或以上字符类型（数字+字母+符号等）
 */
@UtilityClass
public class PasswordStrengthEvaluator {

    // 字符类型定义
    private static final String DIGITS = "0123456789";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SYMBOLS = "\\-_@";

    /**
     * 评估密码强度
     *
     * @param password 待评估的密码
     * @return 强度等级 1-3
     */
    public static int evaluateStrength(String password) {
        if (StrUtil.isBlank(password)) {
            return 1; // 空密码视为弱
        }

        // 检查密码长度
        int length = password.length();
        if (length < 6) {
            return 1; // 长度不足6位，直接视为弱
        }

        // 统计字符类型
        boolean hasDigits = false;
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasSymbols = false;

        for (char c : password.toCharArray()) {
            if (DIGITS.indexOf(c) != -1) {
                hasDigits = true;
            } else if (LOWERCASE_LETTERS.indexOf(c) != -1) {
                hasLowercase = true;
            } else if (UPPERCASE_LETTERS.indexOf(c) != -1) {
                hasUppercase = true;
            } else if (SYMBOLS.indexOf(c) != -1) {
                hasSymbols = true;
            }
        }

        // 计算字符类型数量
        int typeCount = 0;
        if (hasDigits) typeCount++;
        if (hasLowercase) typeCount++;
        if (hasUppercase) typeCount++;
        if (hasSymbols) typeCount++;

        // 根据字符类型数量确定强度等级
        if (typeCount >= 3) {
            return 3; // 强：包含三种或以上字符类型
        } else if (typeCount == 2) {
            return 2; // 中：包含两种字符类型
        } else {
            return 1; // 弱：只包含一种字符类型
        }
    }

    /**
     * 获取密码强度的文字描述
     *
     * @param password 密码
     * @return 强度描述
     */
    public static String getStrengthDescription(String password) {
        int strength = evaluateStrength(password);
        return getStrengthDescription(strength);
    }

    /**
     * 根据强度等级获取文字描述
     *
     * @param strength 强度等级 1-3
     * @return 强度描述
     */
    public static String getStrengthDescription(int strength) {
        switch (strength) {
            case 1:
                return "弱";
            case 2:
                return "中";
            case 3:
                return "强";
            default:
                return "未知";
        }
    }

    /**
     * 检查密码是否达到最低强度要求
     *
     * @param password    密码
     * @param minStrength 最低强度要求 (1-3)
     * @return 是否满足要求
     */
    public static boolean meetsStrengthRequirement(String password, int minStrength) {
        return evaluateStrength(password) >= minStrength;
    }

    /**
     * 获取详细的密码强度分析报告
     *
     * @param password 密码
     * @return 分析报告
     */
    public static StrengthAnalysis analyzePassword(String password) {
        int strength = evaluateStrength(password);
        String description = getStrengthDescription(strength);

        // 分析具体特征
        int length = StrUtil.isBlank(password) ? 0 : password.length();
        boolean hasDigits = containsCharacterType(password, DIGITS);
        boolean hasLowercase = containsCharacterType(password, LOWERCASE_LETTERS);
        boolean hasUppercase = containsCharacterType(password, UPPERCASE_LETTERS);
        boolean hasSymbols = containsCharacterType(password, SYMBOLS);

        return new StrengthAnalysis(strength, description, length,
                hasDigits, hasLowercase, hasUppercase, hasSymbols);
    }

    /**
     * 检查是否包含特定字符类型
     */
    private static boolean containsCharacterType(String password, String characterSet) {
        if (StrUtil.isBlank(password)) {
            return false;
        }
        for (char c : password.toCharArray()) {
            if (characterSet.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 密码强度分析结果类
     */
    public static class StrengthAnalysis {
        private final int strengthLevel;
        private final String strengthDescription;
        private final int length;
        private final boolean hasDigits;
        private final boolean hasLowercase;
        private final boolean hasUppercase;
        private final boolean hasSymbols;

        public StrengthAnalysis(int strengthLevel, String strengthDescription, int length,
                                boolean hasDigits, boolean hasLowercase,
                                boolean hasUppercase, boolean hasSymbols) {
            this.strengthLevel = strengthLevel;
            this.strengthDescription = strengthDescription;
            this.length = length;
            this.hasDigits = hasDigits;
            this.hasLowercase = hasLowercase;
            this.hasUppercase = hasUppercase;
            this.hasSymbols = hasSymbols;
        }

        // Getters
        public int getStrengthLevel() {
            return strengthLevel;
        }

        public String getStrengthDescription() {
            return strengthDescription;
        }

        public int getLength() {
            return length;
        }

        public boolean isHasDigits() {
            return hasDigits;
        }

        public boolean isHasLowercase() {
            return hasLowercase;
        }

        public boolean isHasUppercase() {
            return hasUppercase;
        }

        public boolean isHasSymbols() {
            return hasSymbols;
        }

        @Override
        public String toString() {
            return String.format("密码强度分析: 等级%d(%s), 长度:%d, 数字:%s, 小写字母:%s, 大写字母:%s, 符号:%s",
                    strengthLevel, strengthDescription, length,
                    hasDigits ? "是" : "否", hasLowercase ? "是" : "否",
                    hasUppercase ? "是" : "否", hasSymbols ? "是" : "否");
        }
    }
}