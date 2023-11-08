package tech.qdhxy.erp.common.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.SneakyThrows;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PinyinUtil {
    public static void main(String[] args) {
        String aa = "所得税费用";
        String bbb = getCaptions(aa);
        System.out.println(bbb);

    }
    @SneakyThrows
    public static String getCaptions(String chinese) {
        if(StringUtils.isNotBlank(chinese)) {
            String pinyinStr = PinyinHelper.toHanYuPinyinString(chinese, new HanyuPinyinOutputFormat(), "", false);
            if(StringUtils.isNotBlank(pinyinStr)) {
                return Stream.of(pinyinStr.split("\\d")).map(x -> x.charAt(0) + "").collect(Collectors.joining(""));
            }
        }
        return chinese;
    }
}
