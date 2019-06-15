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
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
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

    <img class="card-img-top" src="<c:url value="/images/${property.mainImage.id}"/>" alt="Card image cap">
    <div class="card-body">
        <h5 class="card-title"><spring:message code="label.proposal.proposal"/>: ${property.description}</h5>
        <p class="card-text">${property.caption}</p>
    </div>
    <div class="card-header">
        <spring:message code="user.interested_users"/>
    </div>
    <ul class="list-group list-group-flush">
        <c:choose>
            <c:when test="${not empty proposalUsers}">
                <a href="<c:url value="/user/${creator.id}"/>" class="list-group-item list-group-item-action">
                    <div style="display: flex;justify-content: space-between">${creator.name}
                        <span><img src="<c:url value="/resources/images/star.png"/>" class="flag" alt="${language_en}"></span>
                    </div>
                </a>
                <c:forEach var="user" items="${proposalUsers}" varStatus="i">
                    <a href="<c:url value="/user/${user.id}"/>" class="list-group-item list-group-item-action">
                        <div style="display: flex;justify-content: space-between">${user.name}
                            <span>
                                <c:choose>
                                    <c:when test="${userStates[i.index] == 0 }">
                                        <img src="<c:url value="/resources/images/clock.png"/>" class="my-span" alt="${language_en}">
                                    </c:when>
                                    <c:when test="${userStates[i.index] == 1}">
                                        <img src="<c:url value="/resources/images/check.png"/>" class="my-span" alt="${language_en}">
                                    </c:when>
                                    <c:otherwise>
                                        <p> ${userStates[i.index]}</p>
                                        <img src="<c:url value="/resources/images/cross.png"/>" class="my-span" alt="${language_en}">
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </a>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li class="list-group-item"><spring:message code="proposal.no_users_in_proposal"/></li>
            </c:otherwise>
        </c:choose>
    </ul>
    <div class="card-body" id="answer">
        <div class="row">
            <c:choose>
                <c:when test="${creator.id == currentUser.id}">
                    <c:url value="/proposal/delete/${proposal.id}" var="postPath"/>
                    <form action="${postPath}" method="post">
                        <button type="submit" class="btn btn-secondary"><spring:message code="label.proposal.cancel"/></button>
                    </form>
                </c:when>
                <c:when test="${isInvited == true && !hasReplied}">
                    <div class="col-6">
                        <c:url value="/proposal/user/accept/${proposal.id}" var="postPath"/>
                        <form action="${postPath}" method="post">
                            <button type="submit" class="btn btn-success"><spring:message code="label.proposal.accept"/></button>
                        </form>
                    </div>
                    <div class="col-6">
                        <c:url value="/proposal/user/decline/${proposal.id}" var="postPath"/>
                        <form action="${postPath}" method="post">
                            <button type="submit" class="btn btn-danger"><spring:message code="label.proposal.decline"/></button>
                        </form>
                    </div>
                </c:when>
                <c:when test="${isInvited == true && hasReplied}">
                    <spring:message code="label.proposal.already_replied"/>
                </c:when>
                <c:otherwise>
                    <spring:message code="label.proposal.your_prpoperty"/>
                    <div class="col-6">
                        <c:url value="/proposal/accept/${proposal.id}" var="postPath"/>
                        <form action="${postPath}" method="post">
                            <button type="submit" class="btn btn-success"><spring:message code="label.proposal.accept"/></button>
                        </form>
                    </div>
                    <div class="col-6">
                        <c:url value="/proposal/decline/${proposal.id}" var="postPath"/>
                        <form action="${postPath}" method="post">
                            <button type="submit" class="btn btn-danger"><spring:message code="label.proposal.decline"/></button>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>

            <%--<div>--%>
                <%--<button type="button" class="btn btn-secondary"><spring:message code="label.proposal.cancel"/></button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>


</body>
</html>
