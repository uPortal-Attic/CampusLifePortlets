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
        <a class="menu-back-link" href="javascript:;" data-role="button" data-icon="back" data-inline="true">Back</a>
        <h2 class="title">${ diningHall.name }</h2>
    </div>

    <div data-role="content" class="portlet-content">

        <ul data-role="listview" class="meals">
            <c:forEach items="${ diningHall.meal }" var="meal" varStatus="status">
                <li><a href="javascript:;">${ meal.name }</a></li>
            </c:forEach>
        </ul>

        <c:forEach items="${ diningHall.meal }" var="meal" varStatus="status">
            <ul id="${n}meal_${status.index}" style="display:none" data-role="listview" class="meal">
                <c:forEach items="${ meal.foodCategory }" var="category" varStatus="status">
                    <li data-role="list-divider">${ category.name }</li>
                    <c:forEach items="${ category.dish }" var="dish">
                        <li style=""><a href="" style="min-height: 0px; padding-left: 15px">
                            ${ dish.name } 
                            <c:forEach items="${ dish.code }" var="code"><img src="${dishCodeImages[code]}" style="float: none; position: relative"/></c:forEach>
                        </a></li>
                    </c:forEach>
                </c:forEach>
            </ul>
        </c:forEach>
    
    </div>
</div>

<script type="text/javascript">
up.jQuery(function () {
    var $ = up.jQuery;
    $(document).ready(function () {
        $(".menu-back-link").click(function () { 
            if ($(".meals").css("display") !== 'none') {
                window.location = "<portlet:renderURL/>";
            } else {
                $(".meal").hide();
                $(".meals").show();
                return false;
            }
        });
        $(".meals li a").click(function () {
            var index = $(this).index(".meals li a");
            $(".meals").hide();
            $("#${n}meal_" + index).show();
        });
    });
});
</script>