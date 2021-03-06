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

package springfox.documentation.swagger.readers.operation

import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import springfox.documentation.spring.web.mixins.RequestMappingSupport
import springfox.documentation.spring.web.plugins.DocumentationContextSpec
import springfox.documentation.builders.OperationBuilder
import springfox.documentation.spi.service.contexts.OperationContext

import static com.google.common.collect.Sets.*

@Mixin([RequestMappingSupport])
class SwaggerMediaTypeReaderSpec extends DocumentationContextSpec {
  def "handler method should override spring media types"() {
    RequestMappingInfo requestMappingInfo =
            requestMappingInfo('/somePath',
                    [
                            'consumesRequestCondition': consumesRequestCondition(['application/json'] as String[]),
                            'producesRequestCondition': producesRequestCondition(['application/json'] as String[])
                    ]
            )
    OperationContext operationContext = new OperationContext(new OperationBuilder(),
            RequestMethod.GET, handlerMethod, 0, requestMappingInfo,
            context(), "")
    when:
      new SwaggerMediaTypeReader().apply(operationContext)
      def operation = operationContext.operationBuilder().build()

    then:
      operation.consumes == expectedConsumes
      operation.produces == expectedProduces

    where:
      expectedConsumes                                  | expectedProduces                                  | handlerMethod
      newHashSet('application/xml')                     | newHashSet()                                      | dummyHandlerMethod('methodWithXmlConsumes')
      newHashSet()                                      | newHashSet('application/xml')                     |  dummyHandlerMethod('methodWithXmlProduces')
      newHashSet('application/xml')                     | newHashSet('application/json')                    | dummyHandlerMethod ('methodWithMediaTypeAndFile', MultipartFile)
      newHashSet('application/xml')                     | newHashSet('application/xml')                     | dummyHandlerMethod  ('methodWithBothXmlMediaTypes')
      newHashSet('application/xml', 'application/json') | newHashSet('application/xml', 'application/json') | dummyHandlerMethod('methodWithMultipleMediaTypes')

  }
}
