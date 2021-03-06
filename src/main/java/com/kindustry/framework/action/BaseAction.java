package com.kindustry.framework.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kindustry.erp.view.Json;
import com.kindustry.util.JsonUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@ParentPackage("default-package")
@Namespace("/")
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

  private static final long serialVersionUID = 1L;

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  public String searchName;
  public String searchValue;
  public String inserted;
  public String updated;
  public String deleted;
  public Integer page;
  public Integer rows;
  public String searchAnds;
  public String searchColumnNames;
  public String searchConditions;
  public String searchVals;

  protected Class<T> sampleClass;
  protected T sample;

  @SuppressWarnings({"rawtypes", "unchecked"})
  public BaseAction() {
    TypeVariable[] typeVariables = this.getClass().getSuperclass().getTypeParameters();

    if (typeVariables != null && typeVariables.length > 0) {
      try {
        Type[] types = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments();
        sampleClass = (Class<T>)types[0];
        return;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public T getModel() {
    if (sample == null) {
      try {
        sample = (T)sampleClass.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return sample;
  }

  /**
   * ajaxJsonOutput
   * 
   * @param object
   * @author kindustry
   */
  public void outputJson(Object object) {
    this.outputJson(object, "application/json");
  }

  /**
   * 
   * @param object
   * @param type
   * @author kindustry
   */
  public void outputJson(Object object, String type) {
    PrintWriter out = null;
    HttpServletResponse httpServletResponse = ServletActionContext.getResponse();
    httpServletResponse.setContentType(type);
    httpServletResponse.setCharacterEncoding("utf-8");
    String json = null;
    try {
      out = httpServletResponse.getWriter();
      // json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
      json = JsonUtil.toJSONString(object);
    } catch (IOException e) {
      e.printStackTrace();
    }
    out.print(json);
    out.close();
  }

  public Json getMessage(boolean flag) {
    Json json = new Json();
    if (flag) {
      json.setStatus(true);
      json.setMessage("数据更新成功！");
    } else {
      json.setMessage("提交失败了！");
    }
    return json;
  }

  public String getSearchName() {
    return searchName;
  }

  public void setSearchName(String searchName) {
    this.searchName = searchName;
  }

  public String getSearchValue() {
    return searchValue;
  }

  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }

  public String getInserted() {
    return inserted;
  }

  public void setInserted(String inserted) {
    this.inserted = inserted;
  }

  public String getUpdated() {
    return updated;
  }

  public void setUpdated(String updated) {
    this.updated = updated;
  }

  public String getDeleted() {
    return deleted;
  }

  public void setDeleted(String deleted) {
    this.deleted = deleted;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getRows() {
    return rows;
  }

  public void setRows(Integer rows) {
    this.rows = rows;
  }

  public String getSearchAnds() {
    return searchAnds;
  }

  public void setSearchAnds(String searchAnds) {
    this.searchAnds = searchAnds;
  }

  public String getSearchColumnNames() {
    return searchColumnNames;
  }

  public void setSearchColumnNames(String searchColumnNames) {
    this.searchColumnNames = searchColumnNames;
  }

  public String getSearchConditions() {
    return searchConditions;
  }

  public void setSearchConditions(String searchConditions) {
    this.searchConditions = searchConditions;
  }

  public String getSearchVals() {
    return searchVals;
  }

  public void setSearchVals(String searchVals) {
    this.searchVals = searchVals;
  }

}
