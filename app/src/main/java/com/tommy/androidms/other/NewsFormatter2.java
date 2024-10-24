package com.tommy.androidms.other;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class NewsFormatter2 {
    //保留原有需要的样式  增加默认样式
    public static String keepAddImgStyle(String imgStr) {
        // 去除 img 标签下 不需要的style
        Pattern pattern = Pattern.compile("<img\\s([^>]*)");
        Matcher matcher = pattern.matcher(imgStr);
        StringBuffer result = new StringBuffer();
        result.append("<img");

        while (matcher.find()) {
            String s2 = imgStyleConvert(matcher.group(1));
            matcher.appendReplacement(result, s2);
        }
        matcher.appendTail(result);

        System.out.println("接下来的元素是img  添加: " + result.toString());

        return result.toString();
    }

    //保留原有需要的样式  增加默认样式
    public static String keepAddStyle2Text(String partKeyText, String wholeText) {
        String xxStr = partKeyText;

        if (wholeText.contains("</strong>") || wholeText.contains("<strong")) {
            xxStr = "<strong>" + xxStr + "</strong>";
        }

        //适配原样式的 color ,bg-color
        String spanStyle = spanColorStyleFit(wholeText);
        xxStr = "<span style=\"" + spanStyle + "\">" + xxStr + "</span>";

        if (wholeText.contains("<p")) {
            xxStr = "<p style=\"text-align: left; line-height: 1.75; margin-top: 20px; margin-bottom: 20px; font-size: 17px;\">" + xxStr;
        }

        if (wholeText.contains("</p>")) {
            xxStr += "</p>";
        }

        return xxStr;
    }


    //去除干扰项
    public static String removeUseless(String originalHtml) {
        String convertedHtml = originalHtml;

        // 去掉注释
        convertedHtml = convertedHtml.replaceAll("<!--.*?-->", "");




        // 移除 div 和 article 标签
        convertedHtml = convertedHtml.replaceAll("<div[^>]*>", "").replaceAll("</div>", "");
        convertedHtml = convertedHtml.replaceAll("<article[^>]*>", "").replaceAll("</article>", "");


        //移除table tr td   <tbody>
        convertedHtml = convertedHtml.replaceAll("<table[^>]*>", "");
        convertedHtml = convertedHtml.replaceAll("</table>", "");

        convertedHtml = convertedHtml.replaceAll("<tr[^>]*>", "");
        convertedHtml = convertedHtml.replaceAll("</tr>", "");

        convertedHtml = convertedHtml.replaceAll("<td[^>]*>", "");
        convertedHtml = convertedHtml.replaceAll("</td>", "");

        // 移除 a 标签
        convertedHtml = convertedHtml.replaceAll("<a[^>]*>", "").replaceAll("</a>", "");
        // h1 转成 p
        convertedHtml = convertedHtml.replaceAll("<h1[^>]*>", "<p><span><strong").replaceAll("</h1>", "</strong></span></p>");
        // 去除无意义的 span
        convertedHtml = convertedHtml.replaceAll("<span>\\s</span>", "");

        return convertedHtml.trim();
    }


    //把爬取的网页源码格式化成支持wangEditor的格式 保留必要原格式
    public static String combinedParse(String originalHtml) {
        String convertedHtml = removeUseless(originalHtml);
        System.out.println("convertedHtml 1: " + convertedHtml);

        //正则表达式与JavaScript类似                     1          2       3           |    4            5 |      6       |   7    8
        Pattern pattern = Pattern.compile("(<[^>/]+>){1,}([^<]+)(</[^>/]+>){1,}|(<[^>/]+>){1,}([^<]+)|(<img[^>]*>)|([^>]+)(</[^>/]+>){1,}");
        Matcher matcher = pattern.matcher(convertedHtml);
        StringBuilder mySelfHtml = new StringBuilder();

        while (matcher.find()) {
            String match0 = matcher.group(0);
//            System.out.println("match[0]: " + match0);

            //处理文字
            int[] arr = {2, 5, 7};
            for (int element : arr) {
                 if(matcher.groupCount() > element-1 &&  matcher.group(element) != null){
                     String matchElement = matcher.group(element);
                     mySelfHtml.append(dealWithText(matchElement, match0, element));
                 }

            }

            //处理图片
            String match6 = matcher.group(6);
            if (matcher.groupCount() > 5 && match6 != null ) {
//                System.out.println("match[6]: " + match6);
                mySelfHtml.append(keepAddImgStyle(match6));
            }
        }

        System.out.println("mySelfHtml 1: " + mySelfHtml.toString());
        return mySelfHtml.toString();
    }

    //处理匹配的文字
    public static String dealWithText(String partStr, String wholeStr, int index) {
        String mySelfHtml = "";
        if (partStr != null && !partStr.trim().isEmpty()) {
//            System.out.println("partStr[" + index + "]: " + partStr);
            mySelfHtml += keepAddStyle2Text(partStr, wholeStr);
        } else {
            mySelfHtml += partStr;
        }
//        System.out.println("dealWithText result :" + mySelfHtml);
        return mySelfHtml;
    }

    //获取span的颜色 背景色
    public static String spanColorStyleFit(String spanStyleStr) {
        String convertedStyle = "";

        Pattern pattern = Pattern.compile("<span[^>]*style=\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(spanStyleStr);

        while (matcher.find()) {
            String match1 = matcher.group(1);
            convertedStyle = spanStyleConvert(match1);
        }

        if (convertedStyle.isEmpty()) {
            convertedStyle = "font-size: 17px;";
        }

        return convertedStyle;
    }

    // span 保留原格式的color bg-color，并添加指定默认格式
    public static String spanStyleConvert(String style) {
        StringBuilder convertedStyle = new StringBuilder();
        String[] array = style.split(";");
        boolean fontFlag = false;
        boolean colorFlag = false;
        boolean bgColorFlag = false;
        boolean fontFamilyFlag = false;

        for (String element : array) {
            if (element.contains("font-size") && !fontFlag) {
                convertedStyle.append("font-size: ").append(17).append("px;");
                fontFlag = true;
            }

            if (element.contains("font-family") && !fontFamilyFlag) {
                convertedStyle.append("font-family: 宋体;");
                fontFamilyFlag = true;
            }

            if (element.contains("background-color") && !bgColorFlag) {
                String str = element.substring(element.indexOf("background-color") + 16);
                convertedStyle.append("background-color").append(str).append(";");
                bgColorFlag = true;
            }

            if (element.contains("color") && !colorFlag) {
                int index = element.indexOf("color");
                if (index < 10) {
                    String str = element.substring(index + 5);
                    convertedStyle.append("color").append(str).append(";");
                    colorFlag = true;
                }
            }
        }

        if (!convertedStyle.toString().contains("font-size")) {
            convertedStyle.append("font-size: ").append(17).append("px;");
        }

        if (!convertedStyle.toString().contains("font-family")) {
            convertedStyle.append("font-family: 宋体;");
        }

        return convertedStyle.toString();
    }

    // img src不变，并添加指定默认格式
    public static String imgStyleConvert(String style) {
        StringBuilder convertedStyle = new StringBuilder();
        System.out.println("img input style:"+style);
        // 正则表达式匹配src属性
        Pattern pattern = Pattern.compile("src=\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(style);

        while (matcher.find()) {
            String srcStr = " src=\"" + matcher.group(1).trim() + "\"";
            convertedStyle.append(srcStr);
        }

        // 加上宽高100%
        convertedStyle.append(" style=\"width: 100%;height: 100%;\"");
        System.out.println("img out style:"+convertedStyle.toString());
        return convertedStyle.toString();
    }

    // 视频格式转换
    public static String videoStyleConvert(String style) {
        return "<video controls=\"true\" width=\"100%\" height=\"100%\"";
    }

    // 段落格式转换
    public static String pStyleConvert(String style) {
        StringBuilder convertedStyle = new StringBuilder();
        String[] array = style.split(";");
        boolean lineHeightFlag = false;
        boolean marginTopFlag = false;
        boolean marginBottomFlag = false;

        for (String element : array) {
            if (element.contains("line-height") && !lineHeightFlag) {
                convertedStyle.append("line-height: 1.75;");
                lineHeightFlag = true;
            }

            if (element.contains("margin-top") && !marginTopFlag) {
                convertedStyle.append("margin-top: 20px;");
                marginTopFlag = true;
            }

            if (element.contains("margin-bottom") && !marginBottomFlag) {
                convertedStyle.append("margin-bottom: 20px;");
                marginBottomFlag = true;
            }
        }

        // 如果缺少相关样式，添加默认值
        if (!lineHeightFlag) {
            convertedStyle.append("line-height: 1.75;");
        }

        if (!marginTopFlag) {
            convertedStyle.append("margin-top: 20px;");
        }

        if (!marginBottomFlag) {
            convertedStyle.append("margin-bottom: 20px;");
        }

        return convertedStyle.toString();
    }

}
