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
package com.jess.arms.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * {@link Fragment} 代理类,用于框架内部在每个 {@link Fragment} 的对应生命周期中插入需要的逻辑
 * @author guanzhirui
 */
public interface FragmentDelegate {

    String FRAGMENT_DELEGATE = "FRAGMENT_DELEGATE";

    /**
     * onAttach
     *
     * @param context 上下文
     */
    void onAttach(@NonNull Context context);

    /**
     * onCreate
     *
     * @param savedInstanceState 保存实例状态
     */
    void onCreate(@Nullable Bundle savedInstanceState);

    /**
     * onCreateView
     *
     * @param view               视图
     * @param savedInstanceState savedInstanceState
     */
    void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState);

    /**
     * onActivityCreate
     *
     * @param savedInstanceState savedInstanceState
     */
    void onActivityCreate(@Nullable Bundle savedInstanceState);

    /**
     * onStart
     */
    void onStart();

    /**
     * onResume
     */
    void onResume();

    /**
     * onPause
     */
    void onPause();

    /**
     * onStop
     */
    void onStop();

    /**
     * onSaveInstanceState
     *
     * @param outState 国家
     */
    void onSaveInstanceState(@NonNull Bundle outState);

    /**
     * onDestroyView
     */
    void onDestroyView();

    /**
     * onDestroy
     */
    void onDestroy();

    /**
     * onDetach
     */
    void onDetach();

    /**
     * isAdded
     * Return true if the fragment is currently added to its activity.
     *
     * @return boolean
     */
    boolean isAdded();

}
