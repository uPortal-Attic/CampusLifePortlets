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

<div class="fl-widget portlet" role="section">

  <!-- Portlet Titlebar -->
  <div class="fl-widget-titlebar titlebar portlet-titlebar" role="sectionhead">
      <h2 class="title" role="heading">
        <portlet:renderURL var="prevUrl">
            <portlet:param name="date" value="${ prev }"/>
        </portlet:renderURL>
        <a href="${ prevUrl }">&lt;</a>
        Menu for ${ displayDate }
        <portlet:renderURL var="nextUrl">
            <portlet:param name="date" value="${ next }"/>
        </portlet:renderURL>
        <a href="${ nextUrl }">&gt;</a>
      </h2>
  </div> <!-- end: portlet-titlebar -->
  
  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">
        <c:if test="${ fn:length(diningHalls) == 0 }">
            <p><spring:message code="no.meals"/></p>
        </c:if>

        <ul data-role="listview">
            <c:forEach items="${ diningHalls }" var="diningHall">
                <li><a href="<portlet:renderURL><portlet:param name="action" value="diningHall"/><portlet:param name="diningHall" value="${ diningHall.key }"/></portlet:renderURL>">${ diningHall.name }</a></li>
            </c:forEach>
        </ul>
    
    </div>
</div>