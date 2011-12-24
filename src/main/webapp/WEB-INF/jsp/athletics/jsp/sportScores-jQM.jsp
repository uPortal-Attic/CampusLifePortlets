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
<style type="text/css">
    .athletic-portlet-scores h3,
    .athletic-portlet-scores p {
        font-size: 12px;
    }
    .athletic-portlet-scores ul li.ui-btn {
        background: none !important;
    }
    .athletic-portlet-scores ul li {
        border-bottom: 1px solid #c9c9c9 !important;
    }
    .athletic-portlet-scores ul li a {
        color: #444 !important;
    }
</style>

<div class="portlet">
    <div data-role="header" class="titlebar portlet-titlebar">
        <a href="<portlet:renderURL/>" data-role="button" data-icon="back" data-inline="true">All Sports</a>
        <h2>${ sport.name }</h2>
        <portlet:renderURL var="newsUrl">
            <portlet:param name="action" value="sportNews"/>
            <portlet:param name="sport" value="${ sport.name }"/>
        </portlet:renderURL>
        <a data-role="button" href="${ newsUrl }">News</a>
    </div>
    
    <div data-role="content" class="portlet-content athletic-portlet-scores">
        <ul data-role="listview">
            <c:forEach items="${ sport.competition }" var="competition">
                <li>
                    <c:choose>
                        <c:when test="${ not empty competition.url }">
                            <a href="${ competition.url }">
                                <h3>${ competition.name }</h3>
                                <p>${ competition.date } @ ${ competition.location }</p>
                                <p>${ not empty competition.result ? competition.result : competition.time }</p>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <h3>${ competition.name }</h3>
                            <p>${ competition.date } @ ${ competition.location }</p>
                            <p>${ not empty competition.result ? competition.result : competition.time }</p>
                        </c:otherwise>
                    </c:choose>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>