package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.OrderSale;
import com.kindustry.erp.model.OrderSaleLine;
import com.kindustry.erp.service.OrderSaleService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.util.JsonUtil;
import com.kindustry.util.PageUtil;

@Namespace("/orderSale")
@Action("orderSaleAction")
public class OrderSaleAction extends BaseAction<OrderSale> {
  private static final long serialVersionUID = -8602771960595933874L;

  @Autowired
  private OrderSaleService orderSaleService;

  /**
   * 查询客户订单明细
   */
  public void findOrderSaleLineList() {
    GridModel gridModel = new GridModel();
    gridModel.setRows(orderSaleService.findOrderSaleLineList(super.sample.getOrderSaleId()));
    gridModel.setTotal(null);
    outputJson(gridModel);
  }

  /**
   * 查询客户订单
   */
  public void findOrderSaleList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(getSearchName(), Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(orderSaleService.findOrderSaleList(map, pageUtil));
    gridModel.setTotal(orderSaleService.getCount(map, pageUtil));
    outputJson(gridModel);
  }

  /**
   * 删除客户订单
   * 
   * @return
   */
  public void delOrderSale() {
    outputJson(getMessage(orderSaleService.delOrderSale(super.sample.getOrderSaleId())));
  }

  /**
   * 持久化客户订单
   */
  public void persistenceOrderSale() {
    Map<String, List<OrderSaleLine>> map = new HashMap<String, List<OrderSaleLine>>();
    if (inserted != null && !"".equals(inserted)) {
      map.put("addList", JsonUtil.parseList(inserted, OrderSaleLine.class));
    }
    if (updated != null && !"".equals(updated)) {
      map.put("updList", JsonUtil.parseList(updated, OrderSaleLine.class));
    }
    if (deleted != null && !"".equals(deleted)) {
      map.put("delList", JsonUtil.parseList(deleted, OrderSaleLine.class));
    }
    outputJson(getMessage(orderSaleService.persistenceOrderSale(super.sample, map)), Constants.TEXT_TYPE_PLAIN);
  }

}
