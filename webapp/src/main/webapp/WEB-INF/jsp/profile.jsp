<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title>Profile</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

        <!-- Bootstrap core css -->
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>

    <body data-gr-c-s-loaded="true">

        <%@include file="navigationBar.jsp"%>

        <h1 class="display-4">${user.name} ${user.lastName}</h1>
        <div>
            <h5 class="text-muted"><img src="<c:url value="/resources/images/studies.svg"/>" class="studies" alt="English">${user.university.name}</h5>
        </div>
        <h6 class="text-muted"><img src="<c:url value="/resources/images/birthday-date.svg"/>" class="birthday-date" alt="English">${user.birthDate.toString()}</h6>
        <div>
        <br>
        </div>
        <div class="profile-data">
            <div class="card">
                <div class="card-header"><spring:message code="label.profile.bio"/></div>
                <div class="card-body">${user.bio}</div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <spring:message code="label.profile.interests"/>
                        </div>
                        <div class="list-group">
                            <c:choose>
                                <c:when test="${not empty interests}">
                                    <c:forEach var="interest" items="${interests}">
                                        <a href="/${interest.id}" class="list-group-item list-group-item-action">${interest.description}</a>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <h1><spring:message code="label.profile.noInterests" /></h1>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            <spring:message code="label.profile.proposals"/>
                        </div>
                        <div class="list-group">
                            <c:choose>
                                <c:when test="${not empty proposals}">
                                    <div class="card-body">
                                        <c:forEach var="proposal" items="${proposals}" varStatus="i">
                                            <a href="/proposal/${proposal.id}" class="list-group-item list-group-item-action">${proposalPropertyNames[i.index]}</a>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="card-body"><spring:message code="label.profile.no_proposals" /></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>