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

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 因为 Java 只能单继承, 所以如果要用到需要继承特定 {@link Activity} 的三方库, 那你就需要自己自定义 {@link Activity}
 * 继承于这个特定的 {@link Activity}, 然后再按照 {@link BaseActivity} 的格式, 将代码复制过去, 记住一定要实现{@link IActivity}
 *
 * @author guanzhirui
 */
public abstract class BaseActivity<P extends IPresenter> extends SupportActivity implements IActivity, ActivityLifecycleable {
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    @Inject
    protected P mPresenter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;
    }

    /**
     * 这个 {@link Activity} 是否会使用 {@link Fragment}, 框架会根据这个属性判断是否注册 {@link FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回 {@code false}, 那意味着这个 {@link Activity} 不需要绑定 {@link Fragment}, 那你再在这个 {@link Activity} 中绑定继承于 {@link BaseFragment} 的 {@link Fragment} 将不起任何作用
     *
     * @return 返回 {@code true} (默认为 {@code true}), 则需要使用 {@link Fragment}
     */
    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public boolean needStatistics() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needStatistics() && Platform.DEPENDENCY_UMENG) {
            // 友盟统计
            MobclickAgent.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (needStatistics() && Platform.DEPENDENCY_UMENG) {
            // 友盟统计
            MobclickAgent.onPause(this);
        }
    }

    @Override
    public boolean forceFontScale() {
        return true;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //fontScale不为1，需要强制设置为1
        if (forceFontScale() && newConfig.fontScale != 1) {
            getResources();
        }
    }

    @Override
    public Resources getResources() {
        if (!forceFontScale()) {
            return super.getResources();
        }
        Resources resources = super.getResources();
        //fontScale不为1，需要强制设置为1
        if (resources.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            //设置成默认值，即fontScale为1
            newConfig.setToDefaults();
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }

}
