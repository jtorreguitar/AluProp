<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

<%--<%@include file="navigationBar.jsp"%>--%>

<div class="card" style="width: 80%;">
    <div class="card-header">
        <spring:message code="user.interested_users"/>
    </div>
    <img class="card-img-top" src="..." alt="Card image cap">
    <div class="card-body">
        <h5 class="card-title">Property name</h5>
        <p class="card-text">Property description</p>
    </div>
    <ul class="list-group list-group-flush">
        <c:choose>
            <c:when test="${not empty proposalUsers}">
                <c:forEach var="user" items="${proposalUsers}">
                    <li class="list-group-item">${user.name}</li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li class="list-group-item"><spring:message code="property.no_users_interested"/></li>

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


</body>
</html>
