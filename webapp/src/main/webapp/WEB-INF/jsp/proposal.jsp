<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title><spring:message code="label.property"/></title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

        <!-- Bootstrap core css -->
        <link href="/resources/css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>
</head>
<body>

<!-- #######################     NAV BAR     ####################### -->

<%@include file="navigationBar.jsp"%>

<div class="card" style="width: 80%;">

    <img class="card-img-top" src="<c:url value="/images/${property.getMainImageId()}"/>" alt="Card image cap">
    <div class="card-body">
        <h5 class="card-title"><spring:message code="label.proposal.proposal"/>: ${property.description}</h5>
        <p class="card-text">${property.caption}</p>
    </div>
    <div class="card-header">
        <spring:message code="user.interested_users"/>
    </div>
    <ul class="list-group list-group-flush">
        <c:choose>
            <c:when test="${not empty proposal.users}">
                <c:forEach var="user" items="${proposal.users}" varStatus="i">
                    <li class="list-group-item"><div style="display: flex;justify-content: space-between">${user.name}<span>
                        <c:choose>
                            <c:when test=" ${proposal.invitedUserStates[i.index] == 0 }">
                                <img src="<c:url value="/resources/images/clock.png"/>"class="flag" alt="${language_en}">
                            </c:when>
                            <c:when test=" ${proposal.invitedUserStates[i.index] == 1}">
                                <img src="<c:url value="/resources/images/tick.png"/>" class="flag" alt="${language_en}">
                            </c:when>

                            <%--<c:when test=" ${proposal.invitedUserStates[i.index] == 2}">--%>
                                <%--<img src="<c:url value="/resources/images/cross.png"/>"/>--%>
                            <%--</c:when>--%>
                            <c:otherwise>
                            <img src="<c:url value="/resources/images/cross.png"/>" class="flag" alt="${language_en}">
                            </c:otherwise>
                        </c:choose>
                    ${proposal.invitedUserStates[i.index]}

                    </span></div></li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li class="list-group-item"><spring:message code="property.no_users_interested"/></li>
            </c:otherwise>
        </c:choose>
    </ul>
    <div class="card-body" id="answer">
        <div class="row">
            <div class="col-6">
                <button type="button" class="btn btn-success"><spring:message code="label.proposal.accept"/></button>
            </div>
            <div class="col-6">
                <button type="button" class="btn btn-danger"><spring:message code="label.proposal.decline"/></button>
            </div>
            <%--<div>--%>
                <%--<button type="button" class="btn btn-secondary"><spring:message code="label.proposal.cancel"/></button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>


</body>
</html>
