/*
 *   Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License").
 *   You may not use this file except in compliance with the License.
 *   A copy of the License is located at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file. This file is distributed
 *   on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *   express or implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 */

package com.amazon.opendistroforelasticsearch.sql.plugin;

import org.elasticsearch.common.settings.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static org.elasticsearch.common.settings.Setting.Property.Dynamic;
import static org.elasticsearch.common.settings.Setting.Property.NodeScope;

/**
 * SQL plugin settings
 */
public class SqlSettings {

    /**
     * Get plugin settings stored in cluster setting. Why not use ES slow log settings consistently?
     * 1) It's per-index setting.
     * 2) It has separate setting for Query and Fetch phase which are all ES internal concepts.
     */
    public static final String SQL_ENABLED = "opendistro.sql.enabled";
    public static final String QUERY_SLOWLOG = "opendistro.sql.query.slowlog";
    public static final String QUERY_ANALYSIS_ENABLED = "opendistro.sql.query.analysis.enabled";
    public static final String METRICS_ROLLING_WINDOW = "opendistro.sql.metrics.rollingwindow";
    public static final String METRICS_ROLLING_INTERVAL = "opendistro.sql.metrics.rollinginterval";

    private final Map<String, Setting<?>> settings;

    public SqlSettings() {
        Map<String, Setting<?>> settings = new HashMap<>();
        settings.put(SQL_ENABLED, Setting.boolSetting(SQL_ENABLED, true, NodeScope, Dynamic));
        settings.put(QUERY_SLOWLOG, Setting.intSetting(QUERY_SLOWLOG, 2, NodeScope, Dynamic));
        settings.put(QUERY_ANALYSIS_ENABLED, Setting.boolSetting(QUERY_ANALYSIS_ENABLED, true, NodeScope, Dynamic));
        settings.put(METRICS_ROLLING_WINDOW, Setting.longSetting(METRICS_ROLLING_WINDOW, 3600L, 2L,
                NodeScope, Dynamic));
        settings.put(METRICS_ROLLING_INTERVAL, Setting.longSetting(METRICS_ROLLING_INTERVAL, 60L, 1L,
                NodeScope, Dynamic));

        this.settings = unmodifiableMap(settings);
    }

    public SqlSettings(Map<String, Setting<?>> settings) {
        this.settings = unmodifiableMap(settings);
    }

    public Setting<?> getSetting(String key) {
        if (settings.containsKey(key)) {
            return settings.get(key);
        }
        throw new IllegalArgumentException("Cannot find setting by key [" + key + "]");
    }

    public List<Setting<?>> getSettings() {
        return new ArrayList<>(settings.values());
    }
}
