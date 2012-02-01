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

<div class="fl-widget portlet" role="section">

  <!-- Portlet Titlebar -->
  <div class="fl-widget-titlebar titlebar portlet-titlebar" role="sectionhead">
      <div class="breadcrumb">
        <portlet:renderURL var="diningHallUrl">
            <portlet:param name="action" value="diningHall"/>
            <portlet:param name="diningHall" value="${ diningHallKey }"/>
        </portlet:renderURL>
        <portlet:renderURL var="mealUrl">
            <portlet:param name="action" value="meal"/>
            <portlet:param name="diningHall" value="${ diningHallKey }"/>
            <portlet:param name="mealName" value="${ mealName }"/>
        </portlet:renderURL>
        <a href="<portlet:renderURL/>">
            <spring:message code="dining.halls"/>
        </a> &gt;
        <a href="${ diningHallUrl }">${ diningHall.name }</a> &gt;
        <a href="${ mealUrl }">${ mealName }</a>
      </div>
      <h2 class="title" role="heading">${ dish.name }</h2>
  </div> <!-- end: portlet-titlebar -->
  
  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">

    <div class="portlet-content">
        
        <c:forEach items="${ dish.code }" var="code"><p><img src="${dishCodeImages[code]}"/> <spring:message code="dish.code.${ code }.name"/></p></c:forEach>

        <div data-role="collapsible" data-collapsed="true">
            <h3><spring:message code="nutrition"/></h3>
            <table>
                <c:forEach items="${ dish.nutritionItem }" var="item">
                    <tr><td style="padding-right: 5px;">${ item.name }</td><td>${ item.value }</td></tr>
                </c:forEach>
            </table>
        </div>
        
        <div data-role="collapsible" data-collapsed="true">
            <h3><spring:message code="ingredients"/></h3>
            <p>${ dish.ingredients }</p>
        </div>
    
    </div>
  </div>
</div>
