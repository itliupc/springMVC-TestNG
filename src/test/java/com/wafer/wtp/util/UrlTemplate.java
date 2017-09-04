package com.wafer.wtp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

public class UrlTemplate {

  private static final Pattern pattern = Pattern.compile("\\{(.*?)\\}");
  private static Matcher matcher;

  /**
   * 格式化字符串 字符串中使用{key}表示占位符
   * 
   * @param sourStr 需要匹配的字符串
   * @param param 参数集
   * @return
   */
  public static Map<String, String> format(String urlStr, String param) {
    Map<String, String> result = new HashMap<String, String>();
    result.put("url", urlStr);
    result.put("param", param);

    String tagerStr = urlStr;
    if (param == null || param.isEmpty()) {
      return result;
    }
    try {
      JSONObject paramObject = JSONObject.fromObject(param);
      matcher = pattern.matcher(tagerStr);
      while (matcher.find()) {
        String key = matcher.group();
        String keyclone = key.substring(1, key.length() - 1).trim();
        if (paramObject.containsKey(keyclone)) {
          Object value = paramObject.get(keyclone);
          tagerStr = tagerStr.replace(key, value.toString());
          paramObject.remove(keyclone);
        }
      }
      result.put("url", tagerStr);
      result.put("param", paramObject.isEmpty() ? "" : paramObject.toString());
    } catch (Exception e) {
      return result;
    }

    return result;
  }
}
