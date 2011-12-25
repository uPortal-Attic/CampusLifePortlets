<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>

<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<div class="portlet">
    <div data-role="content" class="portlet-content">

        <ul data-role="listview">
            <c:forEach items="${ lab }">
                <li data-role="list-divider">${ lab.name }</li>
                <li>
                    <h3>${ lab.name }</h3>
                    <p>${ lab.available } / ${ lab.total } seats available</p>
                </li>
            </c:forEach>
        </ul>
        
    </div>
</div>