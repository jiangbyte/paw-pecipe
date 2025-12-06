package io.jiangbyte.app.biz.cart.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.cart.dto.CardReq;
import io.jiangbyte.app.biz.cart.entity.BizCart;
import io.jiangbyte.app.biz.cart.dto.BizCartDto;
import io.jiangbyte.app.biz.cart.dto.BizCartPageQuery;

import java.util.List;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-12-03
 * @description 购物车表 服务类
 */
public interface BizCartService extends IService<BizCart> {
    Page<BizCart> page(BizCartPageQuery req);

    void add(BizCartDto req);

    void edit(BizCartDto req);

    void delete(List<String> ids);

    BizCart detail(String id);

    List<BizCart> latest(int n);

    List<BizCart> topN(int n);

    /**
     * 加入购物车（自动合并）
     */
    void addToCart(CardReq req);

    /**
     * 更新购物车项数量
     */
    void updateCartItemQuantity(String cartId, Integer newQuantity);

    /**
     * 移除购物车项
     */
    void removeCartItem(String cartId);

    /**
     * 选中/取消选中某项
     */
    void selectCartItem(String cartId, boolean selected);

    /**
     * 获取用户选中的购物车项（用于结算）
     */
    List<BizCart> getSelectedCartItemsForCheckout();

}