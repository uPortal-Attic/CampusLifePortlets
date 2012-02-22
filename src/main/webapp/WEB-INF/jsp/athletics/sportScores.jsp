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
      <div class="breadcrumb">
          <a class="menu-back-link" href="<portlet:renderURL/>">
            <spring:message code="sports"/>
          </a> &gt;
      </div>
      <h2 class="title" role="heading">${ sport.name }</h2>
      <div class="toolbar">
        <ul>
        <portlet:renderURL var="newsUrl">
            <portlet:param name="action" value="sportNews"/>
            <portlet:param name="sport" value="${ sport.name }"/>
        </portlet:renderURL>
          <li><a class="button" href="${ newsUrl }">
            <spring:message code="news"/>
          </a></li>
        </ul>
      </div>
  </div> <!-- end: portlet-titlebar -->
  
  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">

        <c:forEach items="${ sport.competition }" var="competition">
            <h3>${ competition.name }</h3>
            <p>
                ${ competition.date } @ ${ competition.location }
                <br/>${ not empty competition.result ? competition.result : competition.time }
                <c:if test="${ not empty competition.url }">
                    <br/><a href="${ competition.url }"><spring:message code="summary"/></a>
                </c:if>
            </p>
        </c:forEach>
    </div>
</div>