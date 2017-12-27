package com.zhao.zhaolibrary.View.RefreshView;

/**
 * @author zhaofawu
 * @date 2017/11/24 0024.
 */
public interface OnRefreshAndLoadMoreListener {
    /**
     * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。注意此方法是在主线程中调用的， 需要另开线程来进行耗时操作。
     */
    void onRefresh();

    /**
     * 加载更多时会去回调此方法，在方法内编写具体的加载更多逻辑。注意此方法是在主线程中调用的， 需要另开线程来进行耗时操作。
     */
    void onLoadMore();
}
