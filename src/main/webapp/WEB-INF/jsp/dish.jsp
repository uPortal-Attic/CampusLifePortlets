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
        <portlet:renderURL var="backUrl"><portlet:param name="action" value="diningHall"/><portlet:param name="diningHall" value="${ diningHallKey }"/></portlet:renderURL>
        <a class="menu-back-link" href="${ backUrl }" data-role="button" data-icon="back" data-inline="true">Back</a>
        <h2 class="title">Dish Details</h2>
    </div>

    <div data-role="content" class="portlet-content">

        <h2>${ dish.name }</h2>
        
        <c:forEach items="${ dish.code }" var="code"><p><img src="${dishCodeImages[code]}"/> <spring:message code="dish.code.${ code }.name"/></p></c:forEach>

        <div data-role="collapsible" data-collapsed="true">
            <h3>Nutrition</h3>
            <table>
                <c:forEach items="${ dish.nutritionItem }" var="item">
                    <tr><td style="padding-right: 5px;">${ item.name }</td><td>${ item.value }</td></tr>
                </c:forEach>
            </table>
        </div>
        
        <div data-role="collapsible" data-collapsed="true">
            <h3>Ingredients</h3>
            <p>${ dish.ingredients }</p>
        </div>
    
    </div>
</div>
