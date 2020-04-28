package com.example.testkotlindemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView<T> {
    public T presenter;

    /**
     * 是否可见状态
     */
    private boolean isVisible;
    /**
     * 标志位，View已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;

    protected Context context;
    protected LayoutInflater inflater;

    private Unbinder unbinder;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        isFirstLoad = true;
        View view = initView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initData();
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void setPresenter(T presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void showDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).showDialog();
        }
    }

    @Override
    public void dismissDialog() {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).dismissDialog();
        }
    }
}
