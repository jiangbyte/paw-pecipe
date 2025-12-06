package io.jiangbyte.app.base.dashboard.service;

import io.jiangbyte.app.base.dashboard.dto.PaneItem;

import java.util.List;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 26/11/2025
 * @description TODO
 */
public interface DashboardService {
    List<PaneItem> getDashboardPaneItems();
}
