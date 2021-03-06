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

package springfox.documentation.swagger.dto

class AllowableListValuesSpec extends InternalJsonSerializationSpec {

  def "should pass coverage"() {
    expect:
      new springfox.documentation.service.AllowableListValues(['a'], 'string').with {
        getValues()
        getValueType()
      }
  }

  def "when list values is empty, the enum value is ignored" () {
    expect:
      writePretty(new AllowableListValues([], "List")) == """{ }"""
  }

  def "when list values is not empty, the enum value is rendered" () {
    expect:
      writePretty(new AllowableListValues(['ONE', 'TWO'], "List")) == """{
  "enum" : [ "ONE", "TWO" ]
}"""
  }
}
