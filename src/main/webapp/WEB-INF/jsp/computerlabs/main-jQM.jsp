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
	.computer-lab .PC span {
		background: url(<c:url value="/images/windows-24x24.png"/>) left center no-repeat; 
	}

	.computer-lab .Mac span {
		background: url(<c:url value="/images/apple-24x24.png"/>) left center no-repeat; 
	}
	
	@media only screen and (-webkit-min-device-pixel-ratio: 2) {
		/* computer lab portlet */
		.computer-lab .Windows span {
			background: url(<c:url value="/images/windows-48x48.png"/>) left center no-repeat; 
			background-size: 24px 24px;
		}

		.computer-lab .OSX span {
			background: url(<c:url value="/images/apple-48x48.png"/>) left center no-repeat; 
			background-size: 24px 24px;
		}
	}
</style>
<div class="portlet computer-lab">
    <div data-role="content" class="portlet-content">
        <ul data-role="listview">
            <c:forEach items="${ labs }" var="lab">
                <li data-role="list-divider">
                	<div class="ui-grid-a">
						<div class="ui-block-a"><h3>${ lab.name }</h3></div>
						<div class="ui-block-b">
                            <a href="${ locationUrls[lab.locationCode] }" data-role="button" 
                                    data-iconpos="notext" data-icon="map">
                                <spring:message code="map"/>
                            </a>
                        </div>
					</div>
                </li>
                <c:forEach items="${ lab.computerTypes }" var="type">
                    <li class="computer-type <spring:message code="${ type.key }"/> no-ui-li-count">
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
