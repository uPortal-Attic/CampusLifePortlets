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
<rs:aggregatedResources path="/skin.xml"/>
<style>
    .laundry ul li {
        list-style-type: none; 
    }
    .laundry .Washer span {
        background: url(<c:url value="/images/washer-24x24.png"/>) left center no-repeat; 
    }

    .laundry .Dryer span {
        background: url(<c:url value="/images/dryer-24x24.png"/>) left center no-repeat; 
    }
</style>
<!-- Portlet -->
<div class="fl-widget portlet laundry" role="section">

  <!-- Portlet Titlebar -->
  <div class="fl-widget-titlebar titlebar portlet-titlebar" role="sectionhead">
    <h2 class="title" role="heading">
        <spring:message code="laundromats"/>
    </h2>
  </div> <!-- end: portlet-titlebar -->
  
  <!-- Portlet Content -->
  <div class="fl-widget-content content portlet-content" role="main">

      <c:forEach items="${ laundromats }" var="laundromat">
          <div class="portlet-section" role="region">
            <div class="titlebar">
                <h3 class="title" role="heading">${ laundromat.name }</h3>
                <div class="options">
                    <a href="${ locationUrls[laundromat.locationCode] }">
                        <spring:message code="map"/>
                    </a>
                </div>
            </div>
            <div class="content">
                <ul>
                    <c:forEach items="${ laundromat.machineTypes }" var="type">
                        <li class="machine-type <spring:message code="${ type.key }"/>">
                            <spring:message var="machineTypeName" code="${ type.messageKey }"/>
                            <span class="${ type.available == type.total ? "full" : "" }">
                                <spring:message code="proportion.machines.available" arguments="${ type.available },${ type.total },${ machineTypeName }"/>
                            </span>
                        </li>
                    </c:forEach>
                </ul>
            
            </div>
          </div>
      </c:forEach>
        
    </div>
</div>
