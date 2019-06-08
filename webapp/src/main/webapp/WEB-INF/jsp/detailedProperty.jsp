<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title><spring:message code="label.property"/></title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">
        <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>


        <!-- Bootstrap core css -->
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>
</head>
<body>

    <!-- #######################     NAV BAR     ####################### -->

    <%@include file="navigationBar.jsp"%>


    <!-- #######################     PROPERTY     ####################### -->

    <div class="container">
        <c:choose>
            <c:when test="${not empty property.images}">
            <div id="myCarousel" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <c:forEach var="image" items="${property.images}" varStatus="loop">
                        <li data-target="#myCarousel" data-slide-to="${loop.index}"></li>
                    </c:forEach>

                </ol>
                <div class="carousel-inner">
                    <c:forEach var="image" items="${property.images}" varStatus="i">
                        <div class="carousel-item ${i.index == 0?"active":""}">
                            <c:url value="/images/" var="imageUrl"/>
                            <img src="${imageUrl}/${image.id}" class="d-block w-100 carousel-image">
                        </div>
                    </c:forEach>
                </div>
                <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            </c:when>
            <c:otherwise>
                <h1> <spring:message code="system.property.no_images_error"/> </h1>
            </c:otherwise>
        </c:choose>
        <br>
        <div class="my-elem-container">
            <div class="row">
                <div class="col-md-8">
                    <div class="card acqua">
                        <div class="card-body">
                            <div>
                                <spring:message code="forms.privacy.shared" var="privacy_shared"/>
                                <spring:message code="forms.privacy.individual" var="privacy_individual"/>
                                <H2>${property.description}</H2>
                                <H6>
                                    <c:choose>
                                        <c:when test="${property.propertyType == 'HOUSE'}">
                                            <spring:message code="forms.house"/>
                                        </c:when>
                                        <c:when test="${property.propertyType == 'APARTMENT'}">
                                            <spring:message code="forms.apartment"/>
                                        </c:when>
                                        <c:when test="${property.propertyType == 'LOFT'}">
                                            <spring:message code="forms.loft"/>
                                        </c:when>
                                    </c:choose>
                                    <spring:message code="label.properties.in"/> ${property.neighbourhood.name}</H6>
                                <H8>${property.capacity} <spring:message code="label.guests"/> | ${property.privacyLevel?privacy_shared:privacy_individual}</H8>
                            </div>
                            <br>
                            <div class="card">
                                <div class="card-header"><spring:message code="property.description"/></div>
                                <div class="card-body">
                                    <%--Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec dui tellus, luctus sed lacus a, vehicula rhoncus justo. Vivamus cursus, tellus sed pharetra maximus, nisi enim ornare urna, a consequat felis quam a orci. Donec mauris felis, auctor sed nulla a, sollicitudin hendrerit enim. Fusce quis congue justo. Pellentesque eros ligula, varius vitae interdum vel, congue sed eros. Aliquam et augue condimentum, ultricies lectus id, elementum lacus. Cras in rhoncus elit, faucibus euismod nunc. Duis pellentesque commodo tempus. Nunc hendrerit eros vel nisl viverra porttitor. Nam nec urna ac nulla lobortis consequat eu non arcu. Ut ut condimentum tellus, vel ultrices metus. Quisque sapien velit, venenatis nec purus sit amet, accumsan semper massa. Nulla consectetur suscipit sapien, vel rhoncus nunc suscipit id. Donec luctus orci non lectus ornare imperdiet. Donec et nulla mollis, rutrum nisl non, maximus purus.--%>

                                    <%--Nam efficitur cursus purus, a egestas tellus auctor non. Integer maximus, neque a viverra cursus, lectus ex elementum massa, non auctor justo lectus et neque. Quisque ex arcu, tristique vitae dignissim sit amet, rhoncus a sapien. Sed sed erat dui. Vestibulum metus lacus, tempor eu erat ut, pulvinar vulputate arcu. Vestibulum massa quam, laoreet ut commodo a, sagittis in mi. Pellentesque sem ligula, ullamcorper sit amet consectetur tempor, blandit a diam. Proin euismod pulvinar molestie. Nullam rutrum ipsum ut ipsum varius faucibus nec non ante. Praesent pulvinar quis justo ac viverra. Mauris non quam mattis, lobortis arcu ac, ultrices purus.--%>

                                    <%--Suspendisse convallis faucibus mattis. Nunc vel risus sem. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Etiam non orci sit amet nibh cursus finibus. Phasellus elementum lobortis velit, sit amet suscipit elit consectetur vitae. Donec non faucibus purus. Quisque feugiat tortor ut velit imperdiet iaculis. In pretium ex ante, sit amet aliquet erat vulputate varius. Phasellus efficitur urna iaculis eros luctus, et efficitur justo varius. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras porta, libero id tempor finibus, lectus velit auctor odio, nec cursus erat mauris ultrices sapien. Phasellus suscipit semper ligula eget lobortis. Fusce feugiat, velit vel ultrices feugiat, odio nibh faucibus augue, sit amet vestibulum lorem metus ut sem.--%>
                                ${property.caption}
                                </div>
                            </div>
                            <br>

                        <%--<c:choose>--%>
                                <%--<c:when test="${userInterested == true || userRole == '[ROLE_HOST]'}">--%>
                                    <%--<c:url value="/proposal/create/${property.id}" var="postPath"/>--%>
                                    <%--<form:form modelAttribute="proposalForm" action="${postPath}" method="post">--%>
                                        <%--<div class="card">--%>
                                            <%--<div class="card-header" style="display: flex;justify-content: space-between;">--%>
                                                <%--<spring:message code="user.create_proposal" var="createProposal"/>--%>
                                                <%--<span> <spring:message code="user.interested_users"/>--%>
                                        <%--<c:if test="${userRole != '[ROLE_HOST]' && interestedUsers.size() > 1}">--%>
                                            <%--<input type="submit" value="${createProposal}" class="btn btn-primary stretched-link"/>--%>
                                        <%--</c:if>--%>
                                    <%--</span>--%>
                                                <%--<c:if test="${maxPeople != null}">--%>
                                                    <%--<span class="formError"><spring:message code="forms.proposal.max" arguments="${maxPeople}"/></span>--%>
                                                <%--</c:if>--%>
                                            <%--</div>--%>
                                            <%--<ul class="list-group list-group-flush">--%>
                                                <%--<c:choose>--%>
                                                    <%--<c:when test="${not empty interestedUsers and interestedUsers.size() > 1}">--%>
                                                        <%--<c:forEach var="user" items="${interestedUsers}">--%>
                                                            <%--<c:if test="${user.id != currentUser.id}">--%>
                                                                <%--<li class="list-group-item">--%>
                                                                <%--<c:choose>--%>
                                                                    <%--<c:when test="${userRole != '[ROLE_HOST]'}">--%>
                                                                        <%--<div style="display: flex;justify-content: space-between">--%>
                                                                            <%--<label class="checkbox" style="align-self: center;"><form:checkbox path="invitedUsersIds" value="${user.id}"/> ${user.name}</label>--%>
                                                                            <%--<a href="<c:url value="/user/${user.id}"/>"><button type="button" class="btn btn-info"><spring:message code="label.profile"/></button></a>--%>
                                                                        <%--</div>--%>
                                                                        <%--</li>--%>
                                                                    <%--</c:when>--%>
                                                                    <%--<c:otherwise>--%>
                                                                        <%--<a href="<c:url value="/user/${user.id}"/>"><label class="checkbox">${user.name}</label></a></li>--%>
                                                                    <%--</c:otherwise>--%>
                                                                <%--</c:choose>--%>

                                                            <%--</c:if>--%>
                                                        <%--</c:forEach>--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:otherwise>--%>
                                                        <%--<li class="list-group-item"><spring:message code="property.no_users_interested"/></li>--%>
                                                    <%--</c:otherwise>--%>
                                                <%--</c:choose>--%>
                                            <%--</ul>--%>
                                        <%--</div>--%>
                                    <%--</form:form>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>

                            <div class="card">
                                <div class="card-header">
                                    <spring:message code="property.rules"/>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <c:choose>
                                        <c:when test="${not empty property.rules}">
                                            <c:forEach var="rule" items="${property.rules}">
                                                <li class="list-group-item">${rule.name}</li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="list-group-item"> <spring:message code="property.no_special_rules"/> </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>
                            <br>
                            <div class="card">
                                <div class="card-header">
                                    <spring:message code="property.services"/>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <c:choose>
                                        <c:when test="${not empty property.services}">
                                            <c:forEach var="service" items="${property.services}">
                                                <li class="list-group-item">${service.name}</li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="list-group-item"> <spring:message code="property.no_special_services"/> </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card acqua">
                        <div class="card-body">
                            <H4 class="price">$${property.price}</H4><br/>
                            <spring:message code="user.interested" var="interested"/>
                            <spring:message code="user.not_interested" var="not_interested"/>
                            <c:choose>
                                <c:when test="${userRole == '[ROLE_HOST]' && currentUser.id == property.ownerId}">
                                    <c:url value="/property/delete/" var="postPath"/>
                                    <form action="${postPath}${property.id}" method="post">
                                        <button type="submit" class="btn btn-danger"><spring:message code="label.properties.delete"/></button>
                                    </form>
                                    <c:url value="#" var="postPath"/>
                                    <%--<form action="${postPath}${property.id}" method="post">--%>
                                    <button type="submit" class="btn btn-secondary"><spring:message code="label.properties.pause"/></button>
                                    <%--</form>--%>
                                </c:when>
                                <c:when test="${userRole == '[ROLE_HOST]'}">

                                </c:when>
                                <c:when test="${userRole == '[ROLE_GUEST]' && userInterested == true}">
                                    <c:url value="/${property.id}/deInterest/" var="postPath"/>
                                    <form action="${postPath}" method="POST">
                                        <input type="submit" value="${not_interested}" style="color:white;background-color:red;border-color:red" class="btn btn-primary stretched-link"/>
                                    </form><br/>
                                </c:when>
                                <c:otherwise>
                                    <c:url value="/${property.id}/interest/" var="postPath"/>
                                    <form action="${postPath}" method="POST">
                                        <input type="submit" value="${interested}" class="btn btn-primary stretched-link"/>
                                    </form><br/>
                                </c:otherwise>
                            </c:choose><br/>
                            <c:if test="${param.noLogin == true}">
                                <p class="formError"><spring:message code="system.must_be_logged_in_interest"/></p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
