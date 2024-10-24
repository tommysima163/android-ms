package com.tommy.androidms.other;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewsFormatter {

    private static int defaultFontSize = 17;
    private static List<String> textImgArr = new ArrayList<>();
    private static List<Integer> keywordsIndexArr = new ArrayList<>();
    private String mySelfHtml = "";

    public String convertOriginalPasteHtml(String originalHtml) {

//        System.out.println("convertOriginalPasteHtml originalHtml: " + originalHtml);
        textImgArr.clear();
        keywordsIndexArr.clear();
        mySelfHtml = "";

        // 移除干扰项
        String convertedHtml = removeUseless(originalHtml);

        // 理清文字和图片的关系
        addTextImg2ArrNew(convertedHtml);
        int arrLength2 = textImgArr.size();
        System.out.println("textImgArr length: " + arrLength2);
//        System.out.println("textImgArr: " + textImgArr);


        // 匹配Key Text
        Pattern regexText = Pattern.compile(">([^<]+)<");
        Matcher matchesText = regexText.matcher(convertedHtml);

        while (matchesText.find()) {
            // 匹配Key Text
            String targetText = matchesText.group(1);
            boolean flag = false;

            if (targetText.trim().isEmpty()) {
                continue;
            }

            // 正则匹配并处理字符串
            String regex = String.format("(<[^>/]+>){0,}(%s)(</[^>/]+>){0,}", Pattern.quote(targetText));
            Pattern regex2 = Pattern.compile(regex);
            Matcher matchesText2 = regex2.matcher(convertedHtml);


            while (matchesText2.find()) {
                int  matchIndex = matchesText2.start();
                //匹配包含 前后开始 结束标签的字符串 如  <p><span>文字</span></p>
                if (flag) break;

                if (keywordsIndexArr.contains(matchesText2.start())) {
//                    System.out.println("已包含 match.index: " + matchesText2.start());
                    continue;
                }
//                System.out.println("未包含 match.index: " + matchesText2.start());
                String orignal = matchesText2.group(0);
                String middle = matchesText2.group(2);
                String xxStr = middle;
                if (orignal.contains("</strong>")) {
                    xxStr = "<strong>" + xxStr + "</strong>";
                }

                String spanStyle = spanColorStyleFit(orignal);
                xxStr = "<span style=\"" + spanStyle + "\">" + xxStr + "</span>";

                if (orignal.contains("<p")) {
                    xxStr = "<p style=\"text-align: left; line-height: 1.75; margin-top: 20px; margin-bottom: 20px; font-size: 17px;\">" + xxStr;
                }

                if (orignal.contains("</p>")) {
                    xxStr = xxStr + "</p>";
                }

                // 在需要的位置加入img
                xxStr = addImgTagIfNeeded(xxStr, targetText,matchIndex);

                mySelfHtml += xxStr;
                flag = true;

                keywordsIndexArr.add(matchIndex);
            }

        }

//        System.out.println("keywordsIndexArr: " + keywordsIndexArr);
        System.out.println("mySelfHtml: " + mySelfHtml);
        return mySelfHtml;
    }

    private String addImgTagIfNeeded(String xxStr, String targetText,Integer matchIndex) {

        if (keywordsIndexArr.indexOf(matchIndex) > -1){
            System.out.println("该targetText已用过："+targetText);
            return "xxStr";
        }

        int index = textImgArr.indexOf(targetText);
//        System.out.println("addImgTagIfNeeded index: " + index);
        int startIndex = index;

        while (textImgArr.size() - 1 > index) {
            String imgStr = textImgArr.get(index + 1);
//            System.out.println("imgStr 000 :"+imgStr);
            if (imgStr.contains("<img")) {
//                System.out.println("imgStr XXX :"+imgStr);
                Pattern regexImg = Pattern.compile("<img\\s([^>]*)");
                Matcher matchesImg = regexImg.matcher(imgStr);

                while (matchesImg.find()) {
                    String s2 = imgStyleConvert(matchesImg.group(1));
                    imgStr = imgStr.replace(matchesImg.group(1), s2);
                }

//                System.out.println("接下来的元素是img  添加: " + imgStr);
                xxStr = xxStr + imgStr;
                index++;
            } else {
                if (index > startIndex) {
                    textImgArr.subList(startIndex + 1, index).clear();
                }

                return xxStr;
            }
        }

        return xxStr;
    }


    public void myMethod() {
        // 目标 HTML 字符串
        String html = "<p>0123<img src=\"https://www.ciit.edu.cn/_upload/article/images/3d/8c/20b5eef543aaa9984ba129c52678/ab86f245-e048-43b7-a7b6-1563d3e44103.jpg\" >123" +
                "<img src=\"https://www.ciit.edu.cn/_upload/article/images/3d/8c/20b5eef543aaa9984ba129c52678/32649e04-16e3-4587-a2f1-459f9205f4bf.jpg\">" +
                "</p><p><strong><span>345</span></strong><strong><span>就业前景</span></strong></p>";

        // 正则表达式：匹配 > 和 < 之间的内容，或 <img> 标签
        String combinedRegex = ">([^<]+)<|(<img\\s+[^>]*>)";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(combinedRegex);
        Matcher matcher = pattern.matcher(html);

        // 保存匹配结果的列表
        List<String> results = new ArrayList<>();

        // 查找所有匹配项
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                // 匹配 > 和 < 之间的内容
                results.add(matcher.group(1).trim()); // 去除多余空格
            } else if (matcher.group(2) != null) {
                // 匹配 <img> 标签
                results.add(matcher.group(2));
            }
        }

        // 输出结果
        for (String result : results) {
            System.out.println(result);
        }

    }

    // 记录原Html中img和文字的位置关系
    public void addTextImg2ArrNew(String originalHtml) {
//        System.out.println("addImg2ArrNew originalHtml: " + originalHtml);

        // 匹配Key Text 非贪婪模式 .*?
//        Pattern regexText = Pattern.compile(">([^<]+)<|(<img[^>]*>)");

        // <p>0123<img  这种情况走第一个可能 >([^<]+)(<img[^>]+>
        Pattern regexText = Pattern.compile(">([^<]+)(<img[^>]+>)|>([^<]+)<|(<img[^>]+>)");

//        Pattern regexText = Pattern.compile(">([^<]+)<|(<img[^>]+>)");
//        Pattern regexText = Pattern.compile(">([^<]+)<");
//        Pattern regexText = Pattern.compile("(<img[^>]*>)");

        Matcher matchesText = regexText.matcher(originalHtml);

        // 遍历匹配的结果
        while (matchesText.find()) {
            if (matchesText.groupCount() > 0 && matchesText.group(1) != null) {
                textImgArr.add(matchesText.group(1));
//                System.out.println("img group1: " + matchesText.group(1));
            }

            if (matchesText.groupCount() > 1 && matchesText.group(2) != null) {
                textImgArr.add(matchesText.group(2));
//                System.out.println("img group2: " + matchesText.group(2));
            }

            if (matchesText.groupCount() > 2 && matchesText.group(3) != null) {
                textImgArr.add(matchesText.group(3));
//                System.out.println("img group3: " + matchesText.group(3));
            }

            if (matchesText.groupCount() > 3 && matchesText.group(4) != null) {
                textImgArr.add(matchesText.group(4));
//                System.out.println("img group4: " + matchesText.group(4));
            }

        }

//        System.out.println("addImg2Arr textImgArr: " + textImgArr.size());
    }

    // 获取span的颜色和背景色
    public String spanColorStyleFit(String spanStyleStr) {
        String convertedStyle = "";

        // 匹配 span 标签下的 style 属性
        Pattern regex = Pattern.compile("<span[^>]*style=\"([^\"]*)\"");
        Matcher matches = regex.matcher(spanStyleStr);

        while (matches.find()) {
            convertedStyle = spanStyleConvert(matches.group(1));
        }

        if (convertedStyle.isEmpty()) {
            convertedStyle = "font-size: 17px;";
        }

//        System.out.println("spanStyleStr converted: " + convertedStyle);
        return convertedStyle;
    }

    public static String headConvert(String head) {
        // 没有 span，需要转换
        if (head != null && !head.contains("<span")) {
            // 有 p 标签，但没有 span，那么在 p 标签后面加 span
            if (head.contains("<p")) {
                Pattern pattern = Pattern.compile("(<p[^>]*>)");
                Matcher matcher = pattern.matcher(head);
                while (matcher.find()) {
                    head = head.replaceAll(matcher.group(1), matcher.group(1) + "<span>");
                }
            }
            // 没有 p 标签，也没有 span，那么在最外层加 span
            else {
                head = "<span>" + head;
            }
        }
        return head;
    }

    public static String footConvert(String foot) {
        // 没有 span，需要转换
        if (foot != null && !foot.contains("</span>")) {
            // 有 p 标签，但没有 span，那么在 p 标签后面加 span
            if (foot.contains("</p>")) {
                foot = foot.replaceAll("</p>", "</span></p>");
            }
            // 没有 p 标签，也没有 span，那么在最外层加 span
            else {
                foot = foot + "</span>";
            }
        }
        return foot;
    }

    public static String spanStyleConvert(String style) {
        StringBuilder convertedStyle = new StringBuilder();
        String[] array = style.split(";");
        boolean fontFlag = false, colorFlag = false, bgColorFlag = false, fontFamilyFlag = false;

        for (String element : array) {
            if (element.contains("font-size") && !fontFlag) {
                convertedStyle.append("font-size: ").append(defaultFontSize).append("px;");
                fontFlag = true;
            }

            if (element.contains("font-family") && !fontFamilyFlag) {
                convertedStyle.append("font-family: 宋体;");
                fontFamilyFlag = true;
            }

            if (element.contains("background-color") && !bgColorFlag) {
                String bgColor = element.substring(element.indexOf("background-color") + 16);
                convertedStyle.append("background-color").append(bgColor).append(";");
                bgColorFlag = true;
            }

            if (element.contains("color") && !colorFlag) {
                int index = element.indexOf("color");
                if (index < 10) {
                    String color = element.substring(index + 5);
                    convertedStyle.append("color").append(color).append(";");
                    colorFlag = true;
                }
            }
        }

        if (!fontFlag) {
            convertedStyle.append("font-size: ").append(defaultFontSize).append("px;");
        }
        if (!fontFamilyFlag) {
            convertedStyle.append("font-family: 宋体;");
        }

        return convertedStyle.toString();
    }

    public static String imgStyleConvert(String style) {
        StringBuilder convertedStyle = new StringBuilder();
        Pattern pattern = Pattern.compile("src=\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(style);
        while (matcher.find()) {
            convertedStyle.append(" src=\"").append(matcher.group(1).trim()).append("\"");
        }

        // 加上宽高100%
        convertedStyle.append(" style=\"width: 100%;height: 100%;\"");

        return convertedStyle.toString();
    }


    public static String removeUseless(String originalHtml) {
        String convertedHtml = originalHtml;

        // 去掉注释
        convertedHtml = convertedHtml.replaceAll("<!--.*?-->", "");
        // 移除 div 和 article 标签
        convertedHtml = convertedHtml.replaceAll("<div[^>]*>", "").replaceAll("</div>", "");
        convertedHtml = convertedHtml.replaceAll("<article[^>]*>", "").replaceAll("</article>", "");
        // 移除 a 标签
        convertedHtml = convertedHtml.replaceAll("<a[^>]*>", "").replaceAll("</a>", "");
        // h1 转成 p
        convertedHtml = convertedHtml.replaceAll("<h1[^>]*>", "<p><span><strong").replaceAll("</h1>", "</strong></span></p>");
        // 去除无意义的 span
        convertedHtml = convertedHtml.replaceAll("<span>\\s</span>", "");

        return convertedHtml.trim();
    }


}
