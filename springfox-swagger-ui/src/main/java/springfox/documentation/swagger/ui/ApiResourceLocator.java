/*
 *
 *  Copyright 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package springfox.documentation.swagger.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiResourceLocator {

  private final DocumentationCache documentationCache;

  @Autowired
  public ApiResourceLocator(DocumentationCache documentationCache) {
    this.documentationCache = documentationCache;
  }

  public List<SwaggerApi> resources() {
    List<SwaggerApi> swaggerApis = new ArrayList<SwaggerApi>();
    for (Documentation documentation : documentationCache.all()) {
      SwaggerApi swaggerApi = new SwaggerApi();
      swaggerApi.setUri(documentation.getBasePath());
      swaggerApi.setTitle(documentation.getGroupName());
      swaggerApis.add(swaggerApi);
    }
    return swaggerApis;
  }
}
