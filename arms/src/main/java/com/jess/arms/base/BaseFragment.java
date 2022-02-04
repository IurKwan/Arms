/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.base;

import androidx.annotation.ContentView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle4.android.FragmentEvent;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 因为 Java 只能单继承, 所以如果要用到需要继承特定 @{@link Fragment} 的三方库, 那你就需要自己自定义 @{@link Fragment}
 * 继承于这个特定的 @{@link Fragment}, 然后再按照 {@link BaseFragment} 的格式, 将代码复制过去, 记住一定要实现{@link IFragment}
 *
 * @author guanzhirui
 */
public abstract class BaseFragment<P extends IPresenter> extends SupportFragment implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;

    /**
     * 如果当前页面逻辑简单, Presenter 可以为 null
     */
    @Inject
    protected P mPresenter;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    public BaseFragment() {
        super();
    }

    /**
     * 封装简化ViewBind的使用
     *
     * @param contentLayoutId layout
     */
    @ContentView
    public BaseFragment(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 隐藏键盘
        hideSoftInput();
        //释放资源
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        this.mPresenter = null;
    }

    @Override
    public boolean needStatistics() {
        return true;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (needStatistics() && Platform.DEPENDENCY_UMENG) {
            // 友盟统计
            MobclickAgent.onPageStart(getClass().getSimpleName());
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (needStatistics() && Platform.DEPENDENCY_UMENG) {
            // 友盟统计
            MobclickAgent.onPageEnd(getClass().getSimpleName());
        }
    }
}
