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
package com.netflix.iep.userservice;

import com.fasterxml.jackson.core.type.TypeReference;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Service based on an enpdoint that returns a simple JSON list of emails. For
 * example the whitepages mailing list endpoint.
 */
@Singleton
final class SimpleUserService extends AbstractUserService {

  private static final TypeReference<Set<String>> SET_TYPE = new TypeReference<Set<String>>() {};

  @Inject
  SimpleUserService(Context context) {
    super(context, "simple");
  }

  @Override protected Set<String> parseResponse(byte[] data) throws IOException {
    Set<String> vs = context.objectMapper().readValue(data, SET_TYPE);
    return Collections.unmodifiableSet(new TreeSet<>(vs));
  }
}
