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
        <portlet:renderURL var="backUrl">
            <portlet:param name="action" value="diningHall"/>
            <portlet:param name="diningHall" value="${ diningHallKey }"/>
        </portlet:renderURL>
        <a href="<portlet:renderURL/>">
            <spring:message code="dining.halls"/>
        </a> &gt;
        <a href="${ backUrl }">${ diningHall.name }</a> &gt;
      </div>
      <h2 class="title" role="heading">${ meal.name }</h2>
  </div> <!-- end: portlet-titlebar -->
  
  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">

      <c:forEach items="${ categories }" var="category" varStatus="status">
         <div class="portlet-section" role="region">
            <div class="titlebar">
                <h3 class="title" role="heading">${ category.name }</h3>
            </div>
            <div class="content">
            
              <ul>
                <c:forEach items="${ category.dish }" var="dish">
                    <portlet:renderURL var="dishUrl">
                        <portlet:param name="action" value="dish"/>
                        <portlet:param name="diningHall" value="${ diningHallKey }"/>
                        <portlet:param name="mealName" value="${ meal.name }"/>
                        <portlet:param name="dishName" value="${ dish.name }"/>
                    </portlet:renderURL>
                    <li><a href="${ dishUrl }" style="min-height: 0px; padding-left: 15px">
                        ${ dish.name }
                        <c:forEach items="${ dish.code }" var="code">
                            <img src="${dishCodeImages[code]}" style="float: none; position: relative; margin-right: 10px"/>
                        </c:forEach>
                    </a></li>
                </c:forEach>
              </ul>
            </div>
         </div>
      </c:forEach>
    
    </div>
</div>
