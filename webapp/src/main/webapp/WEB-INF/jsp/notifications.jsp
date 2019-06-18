<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title><spring:message code="label.proposal.proposal"/></title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">
        <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>

        <!-- Bootstrap core css -->
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="<c:url value="/resources/js/urlUtility.js"/>" ></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>
</head>
<body>

<!-- #######################     NAV BAR     ####################### -->

<%@include file="navigationBar.jsp"%>

<div class="card" style="width: 80%;">
    <div class="card-header">
        <h5 class="card-title"><spring:message code="notifications.notifications"/>:</h5>
    </div>
    <div class="list-group">
        <c:choose>
            <c:when test="${not empty notifications}">
                <c:forEach var="notification" items="${notifications}" varStatus="i">
                        <a href="<c:url value="${notification.link}?notificationId=${notification.id}"/>" class="list-group-item list-group-item-action ${notification.state == 'UNREAD'?'unread':''}">
                            <div class="notification">
                                <div class="notification-subject"><spring:message code="${notification.subjectCode}"/></div>
                                <div class="notification-text"><spring:message code="${notification.textCode}"/></div>
                            </div>
                        </a>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="list-group-item"><spring:message code="notifications.noAllNotifications"/></div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>