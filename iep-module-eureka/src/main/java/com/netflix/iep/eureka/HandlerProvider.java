/*
 * Copyright 2014-2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.iep.eureka;

import com.google.inject.Inject;
import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.iep.service.ServiceManager;

import javax.inject.Provider;

/**
 * Provides an instance of the healthcheck handler for discovery.
 */
class HandlerProvider implements Provider<HealthCheckHandler> {

  private final HealthCheckHandler handler;

  @Inject
  HandlerProvider(Provider<ServiceManager> mgr) {
    this.handler = new EurekaHandler(mgr);
  }

  @Override public HealthCheckHandler get() {
    return handler;
  }
}
