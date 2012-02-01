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
<link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css"/>
<style>
	.laundry .Washer span {
		background: url(<c:url value="/images/washer-24x24.png"/>) left center no-repeat; 
	}

	.laundry .Dryer span {
		background: url(<c:url value="/images/dryer-24x24.png"/>) left center no-repeat; 
	}
	
	@media only screen and (-webkit-min-device-pixel-ratio: 2) {
		/* laundry portlet */
		.laundry .washers span {
			background: url(<c:url value="/images/washer-48x48.png"/>) left center no-repeat; 
			background-size: 24px 24px;
		}

		.laundry .dryers span {
			background: url(<c:url value="/images/dryer-48x48.png"/>) left center no-repeat; 
			background-size: 24px 24px;
		}
	}
</style>
<div class="portlet laundry">
    <div data-role="content" class="portlet-content">
        <ul data-role="listview">
            <c:forEach items="${ laundromats }" var="laundromat">
                <li data-role="list-divider">
                	<div class="ui-grid-a">
						<div class="ui-block-a"><h3>${ laundromat.name }</h3></div>
						<div class="ui-block-b">
                            <a href="${ locationUrls[laundromat.locationCode] }" data-role="button" 
                                    data-iconpos="notext" data-icon="map" data-theme="b">
                                <spring:message code="map"/>
                            </a>
                        </div>
					</div>
                </li>
                <c:forEach items="${ laundromat.machineTypes }" var="type">
                    <li class="machine-type <spring:message code="${ type.key }"/> no-ui-li-count">
                        <spring:message var="machineTypeName" code="${ type.messageKey }"/>
                        <span class="${ type.available == type.total ? "full" : "" }">
                            <spring:message code="proportion.machines.available" arguments="${ type.available },${ type.total },${ machineTypeName }"/>
                        </span>
                    </li>
                </c:forEach>
            </c:forEach>
        </ul>
    </div>
</div>
