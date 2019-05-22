<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title><spring:message code="label.profile"/></title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

        <!-- Bootstrap core css -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css" />
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

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
                                        <a href="<c:url value="/${interest.id}"/>" class="list-group-item list-group-item-action">${interest.description}</a>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                <div class="card-body"><spring:message code="label.profile.noInterests" /></div>
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
                                            <a href="<c:url value="/proposal/${proposal.id}"/>" class="list-group-item list-group-item-action">${proposalPropertyNames[i.index]}</a>
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
        </div>

    </body>
</html>