package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Log;
import com.kindustry.erp.service.LogsService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.util.PageUtil;

@Namespace("/logs")
@Action(value = "logsAction")
public class LogsAction extends BaseAction<Log> {
  private static final long serialVersionUID = 4149928264423089262L;

  @Autowired
  private LogsService logsService;

  /**
   * 查询所有日志
   */
  public String findLogsAllList() {
    Map<String, Object> params = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      params.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(logsService.findLogsAllList(params, pageUtil));
    gridModel.setTotal(logsService.getCount(params, pageUtil));
    outputJson(gridModel);
    return null;
  }

  /**
   * 删除日志
   */
  public String delLogs() {
    outputJson(getMessage(logsService.delLogs(super.sample.getSid())));
    return null;
  }

  /**
   * 持久化日志弹窗
   */
  public String persistenceLogs() {
    outputJson(getMessage(logsService.persistenceLogs(super.sample)), Constants.TEXT_TYPE_PLAIN);
    return null;
  }

}
