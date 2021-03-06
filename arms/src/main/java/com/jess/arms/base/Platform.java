/*
 * Copyright 2018 JessYan
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

/**
 * @author guanzhirui
 * @date 2022/06/26
 */
public class Platform {
    public static final boolean DEPENDENCY_GLIDE;
    public static final boolean DEPENDENCY_UMENG;

    static {
        DEPENDENCY_GLIDE = findClassByClassName("com.bumptech.glide.Glide");
        DEPENDENCY_UMENG = findClassByClassName("com.umeng.analytics.MobclickAgent");
    }

    private static boolean findClassByClassName(String className) {
        boolean hasDependency;
        try {
            Class.forName(className);
            hasDependency = true;
        } catch (ClassNotFoundException e) {
            hasDependency = false;
        }
        return hasDependency;
    }
}
