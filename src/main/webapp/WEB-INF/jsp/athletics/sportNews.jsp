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
        <portlet:renderURL var="scoresUrl">
            <portlet:param name="action" value="sportScores"/>
            <portlet:param name="sport" value="${ sport.name }"/>
        </portlet:renderURL>
          <li><a class="button" href="${ scoresUrl }">
            <spring:message code="scores"/>
          </a></li>
        </ul>
      </div>
  </div> <!-- end: portlet-titlebar -->
  
  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">

            <c:forEach items="${ sport.newsItem }" var="item">
                <h3>
                    ${ item.date }: <a href="${ item.storyUrl }">
                        ${ item.title }
                    </a>
                </h3>
            </c:forEach>

    </div>
</div>
