package com.kindustry.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Log;

public class BaseUtil {
  //
  // public static ShiroUser getCurrendUser() {
  // Subject subject = SecurityUtils.getSubject();
  // return (ShiroUser)subject.getSession().getAttribute(Constants.SHIRO_USER);
  // }

  /**
   * 高级查询hql条件拼接
   */
  public static String getGradeSearchConditionsHQL(String asName, PageUtil pageUtil) {
    String searchAnds = pageUtil.getSearchAnds();
    String searchColumnNames = pageUtil.getSearchColumnNames();
    String searchConditions = pageUtil.getSearchConditions();
    String searchVals = pageUtil.getSearchVals();
    if (searchColumnNames != null && searchColumnNames.length() > 0) {
      StringBuffer sb = new StringBuffer();
      String[] searchColumnNameArray = searchColumnNames.split("\\,");
      String[] searchAndsArray = searchAnds.split("\\,");
      String[] searchConditionsArray = searchConditions.split("\\,");
      String[] searchValsArray = searchVals.split("\\,");
      for (int i = 0; i < searchColumnNameArray.length; i++) {
        if (searchColumnNameArray[i].trim().length() > 0 && searchConditionsArray[i].trim().length() > 0) {
          String temp = searchValsArray[i].trim().replaceAll("\\'", "");
          if (Constants.HQL_LIKE.equals(searchConditionsArray[i].trim())) {
            sb.append(" " + searchAndsArray[i].trim() + " " + asName + Constants.IS_DOT + searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " " + "'%"
              + temp + "%'");

          } else {
            sb.append(" " + searchAndsArray[i].trim() + " " + asName + Constants.IS_DOT + searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " " + "'"
              + temp + "'");
          }
        }
      }
      if (sb.length() > 0) {
        return sb.toString();
      }
    }
    return "";
  }

  /**
   * 获得简单查询条件
   */
  public static String getSearchConditionsHQL(String asName, Map<String, Object> param) {
    StringBuffer sb = new StringBuffer();
    if (param != null && !param.isEmpty()) {
      for (String name : param.keySet()) {
        sb.append(" and " + asName + "." + name + " like :" + name);
      }
    }
    return sb.toString();
  }

  /**
   * 获取操作日志
   */
  public static <T> void getLogs(Session session, T o, String eventName, String er, String name) {
    if (!Constants.LOGS_TB_NAME.equals(o.getClass().getSimpleName())) {
      String ip = getIpAddr();
      String mac = null;
      try {
        mac = IpUtil.getMACAddress(Inet4Address.getLocalHost());
      } catch (Exception e) {
        mac = "xx:xx:xx:xx:xx:xx";
      }
      String[] sdf = getFiledName(o);
      String id = getFieldValueByName(sdf[1], o).toString();
      Log l = new Log();
      // l.setUserId(getCurrendUser().getUserId());
      // l.setName(getCurrendUser().getAccount());
      l.setLogDate(new Date());
      l.setEventName(eventName + o.getClass().getSimpleName());
      l.setEventRecord(er + o.getClass().getName());
      l.setObjectId(id);
      l.setType(2);
      l.setIp(ip);
      l.setMac(mac);
      session.save(l);
    }
  }

  public static Object getFieldValueByName(String fieldName, Object o) {
    String firstLetter = fieldName.substring(0, 1).toUpperCase();
    String getter = "get" + firstLetter + fieldName.substring(1);
    try {
      Method method = o.getClass().getMethod(getter, new Class[] {});
      Object value = method.invoke(o, new Object[] {});
      return value;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("属性不存在");
      return "";
    }
  }

  public static String[] getFiledName(Object o) {
    Field[] fields = o.getClass().getDeclaredFields();
    String[] fieldNames = new String[fields.length];
    for (int i = 0; i < fieldNames.length; i++) {
      fieldNames[i] = fields[i].getName();
    }
    return fieldNames;
  }

  /**
   * 获取客户端ip地址
   */
  public static String getIpAddr() {
    HttpServletRequest request = ServletActionContext.getRequest();
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * 文件拷贝
   */
  public static void copy(File src, String fullSavePath) {
    InputStream in = null;
    OutputStream out = null;
    try {
      File newFile = new File(fullSavePath);
      in = new BufferedInputStream(new FileInputStream(src), Constants.BUFFER_SIZE);
      out = new BufferedOutputStream(new FileOutputStream(newFile), Constants.BUFFER_SIZE);
      byte[] buffer = new byte[Constants.BUFFER_SIZE];
      int len = 0;
      while ((len = in.read(buffer)) > 0) {
        out.write(buffer, 0, len);
      }
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != in) {
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != out) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 备份数据库
   */
  public static String dbBackup() {

    SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
    String fineName = "dbBackup-" + sd.format(new Date());
    String sqlName = fineName + ".sql";
    String pathSql = "attachment" + File.separator + "dbBackUp";
    try {
      File filePathSql = new File(pathSql);
      if (!filePathSql.exists()) {
        filePathSql.mkdir();
      }
      StringBuffer sbs = new StringBuffer();
      ResourceBundle rb = ResourceBundle.getBundle("config");
      sbs.append("mysqldump ");
      sbs.append("-h " + rb.getString("mysqlhost"));
      sbs.append("--user=" + rb.getString("jdbc_username"));
      sbs.append(" --password=" + rb.getString("jdbc_password"));
      sbs.append(" --lock-all-tables=true ");
      sbs.append("--result-file=" + pathSql + File.separator);
      sbs.append(sqlName + " ");
      sbs.append(" --default-character-set=utf8 ");
      sbs.append("erp");
      Runtime runtime = Runtime.getRuntime();
      Process child = runtime.exec(sbs.toString());
      // 读取备份数据并生成临时文件
      InputStream in = child.getInputStream();
      OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pathSql + "/" + sqlName), "utf8");
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf8"));
      String line = reader.readLine();
      while (line != null) {
        writer.write(line + "\n");
        line = reader.readLine();
      }
      writer.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sqlName;
  }

}
