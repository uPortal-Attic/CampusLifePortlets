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
<c:set var="n"><portlet:namespace/></c:set>

<div id="${n}container" class="portlet">

    <div data-role="header" class="titlebar portlet-titlebar">
        <c:choose>
            <c:when test="${ hasMultipleLocations }">
                <a class="menu-back-link" href="<portlet:renderURL/>" data-role="button" data-icon="back" data-inline="true">Back</a>
                <h2 class="title">${ diningHall.name }</h2>
            </c:when>
            <c:otherwise>
                <h2 class="title">
                    <portlet:renderURL var="prevUrl">
                        <portlet:param name="action" value="diningHall"/>
                        <portlet:param name="diningHall" value="${ diningHall.key }"/>
                        <portlet:param name="date" value="${ prev }"/>
                    </portlet:renderURL>
                    <a data-role="button" data-icon="arrow-l" data-iconpos="notext" data-inline="true" href="${ prevUrl }">&lt;</a>
                    ${ displayDate }
                    <portlet:renderURL var="nextUrl">
                        <portlet:param name="action" value="diningHall"/>
                        <portlet:param name="diningHall" value="${ diningHall.key }"/>
                        <portlet:param name="date" value="${ next }"/>
                    </portlet:renderURL>
                    <a data-role="button" data-icon="arrow-r" data-iconpos="notext" data-inline="true" href="${ nextUrl }">&lt;</a>
                </h2>
            </c:otherwise>
        </c:choose>
        <c:if test="${ not empty locationUrl }">
            <a href="${ locationUrl }" data-role="button" data-iconpos="notext" data-icon="map" class="ui-btn-right">
                <spring:message code="map"/>
            </a>
        </c:if>
        
    </div>

    <div data-role="content" class="portlet-content">
    
        <ul data-role="listview">
            <c:if test="${ fn:length(diningHall.meals) == 0 }">
                <p><spring:message code="no.meals"/></p>
            </c:if>
    
            <c:forEach items="${ diningHall.meals }" var="meal" varStatus="status">
                <portlet:renderURL var="mealUrl">
                    <portlet:param name="action" value="meal"/>
                    <portlet:param name="diningHall" value="${ diningHall.key }"/>
                    <portlet:param name="mealName" value="${ meal.name }"/>
                    <portlet:param name="date" value="${ date }"/>
                </portlet:renderURL>
                <li><a href="${ mealUrl }">${ meal.name }</a></li>
            </c:forEach>
        </ul>
    
    </div>
</div>
