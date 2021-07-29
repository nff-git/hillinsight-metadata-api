package hillinsight.metadata.api.utils.convention;

import com.alibaba.fastjson.JSONObject;
import hillinsight.metadata.api.models.vo.ThirdFieldExcelimportVo;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName PageTransferUtils
 * @Description 正则
 * @Author nff
 * @Date 2021/5/27
 * @Version 2.0
 */
public class RegularUtils {


    /**
     * 正则校验(包括0，小数保留两位的数字)
     *
     * @return {@link boolean}
     */
    public static boolean isResult(String str) {
        //正数的正则表达式(包括0，小数保留两位)：^((0{1}\.\d{1,2})|([1-9]\d*\.{1}\d{1,2})|([1-9]+\d*)|0)$
        Pattern pattern = Pattern.compile("^((0{1}\\.\\d{1,2})|([1-9]\\d*\\.{1}\\d{1,2})|([1-9]+\\d*)|0)$");
        Matcher matcher = pattern.matcher((CharSequence) str);
        return matcher.matches();
    }

    /**
     * 正则校验（包括0的数字）
     *
     * @return {@link boolean}
     */
    public static boolean isInteger(String str) {
        //正整数的正则表达式(包括0)：^[+]{0,1}(\d+)$
        Pattern pattern = Pattern.compile("^[+]{0,1}(\\d+)$");
        Matcher matcher = pattern.matcher((CharSequence) str);
        return matcher.matches();
    }

    /**
     * 判断数字是否在某个范围内
     * @param num 数字
     * @param startRange 开始范围
     * @param endRange 结束范围
     */
    public static boolean numberIsInTheRange(int num, int startRange, int endRange) {
    // 判断是否在范围内
        if (num >= startRange && num <= endRange) {
            return true;
        }
        return false;
    }





}
