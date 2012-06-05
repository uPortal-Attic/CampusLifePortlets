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

    <portlet:renderURL var="backUrl">
        <portlet:param name="action" value="diningHall"/>
        <portlet:param name="diningHall" value="${ diningHallKey }"/>
        <portlet:param name="date" value="${ date }"/>
    </portlet:renderURL>
    <div data-role="header" class="titlebar portlet-titlebar">
        <a class="menu-back-link" href="${ backUrl }" data-role="button" data-icon="back" data-inline="true">Back</a>
        <h2 class="title">${ meal.name }</h2>
    </div>

    <div data-role="content" class="portlet-content">

        <ul data-role="listview">
            <c:forEach items="${ categories }" var="category" varStatus="status">
                <li data-role="list-divider">${ category.name }</li>
                <c:forEach items="${ category.dishes }" var="dish">
                    <li>
                        <c:choose>
                            <c:when test="${ showDishDetails }">
                                <portlet:renderURL var="dishUrl">
                                    <portlet:param name="action" value="dish"/>
                                    <portlet:param name="diningHall" value="${ diningHallKey }"/>
                                    <portlet:param name="mealName" value="${ meal.name }"/>
                                    <portlet:param name="dishName" value="${ dish.name }"/>
                                    <portlet:param name="date" value="${ date }"/>
                                </portlet:renderURL>
                                <a href="${ dishUrl }" style="min-height: 0px; padding-left: 15px">
                                    ${ dish.name }
                                    <c:forEach items="${ dish.code }" var="code">
                                        <img src="${dishCodeImages[code]}" style="float: none; position: relative; margin-right: 10px"/>
                                    </c:forEach>
                                </a>
                            </c:when>
                            <c:otherwise>
                                ${ dish.name }
                                <c:forEach items="${ dish.code }" var="code">
                                    <img src="${dishCodeImages[code]}" style="float: none; position: relative; margin-right: 10px"/>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </c:forEach>
        </ul>
    
    </div>
</div>
