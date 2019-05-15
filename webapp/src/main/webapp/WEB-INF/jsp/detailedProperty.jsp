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
        <title>Property</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

        <!-- Bootstrap core css -->
        <link href="resources/css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

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
                    <c:forEach var="image" items="${property.images}">
                        <div class="carousel-item active">
                            <img src="http://${pageContext.request.localName}:${pageContext.request.serverPort}/images/${image.id}" class="d-block w-100 carousel-image">
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
                <h1> Oops, there are no images for this property </h1>
            </c:otherwise>
        </c:choose>
        <br>
        <div class="flex-container">
            <div>
                <H2>${property.description}</H2>
                <H6>${property.propertyType.toString()} in ${property.neighbourhood.name}</H6>
                <H8>${property.capacity} huespedes | ${property.privacyLevel?"Shared":"Individual"}</H8>
            </div>
            <div class="interest-column text-right">
                <H4 class="price">$${property.price}</H4><br/>
                <form action="${property.id}/interest" method="POST">
                    <c:choose>
                        <c:when test="${userInterested == true}">
                            <input type="submit" value="Ya no me Interesa" style="color:white;background-color:red;border-color:red" class="btn stretched-link"/>
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Me Interesa" class="btn btn-primary stretched-link"/>
                        </c:otherwise>
                    </c:choose>

                </form><br/>
                <c:if test="${param.noLogin == true}">
                    <p class="formError">You must be logged in to show interest.</p>
                </c:if>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        ${property.caption}
                    </div>
                </div>
                <br/>
                <c:choose>
                    <c:when test="${userInterested == true}">
                        <div class="card">
                            <div class="card-header">
                                Usuarios interesados
                            </div>
                            <ul class="list-group list-group-flush">
                                <c:choose>
                                    <c:when test="${not empty interestedUsers and interestedUsers.size() > 1}">
                                        <c:forEach var="user" items="${interestedUsers}">
                                            <li class="list-group-item"><input type="checkbox"/>  ${user.name}</li>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="list-group-item">No other users have shown interest in this property!</li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </div>

                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-3">
                <div class="card">
                    <div class="card-header">
                        Reglas
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:choose>
                            <c:when test="${not empty property.rules}">
                                <c:forEach var="rule" items="${property.rules}">
                                    <li class="list-group-item">${rule.name}</li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <li class="list-group-item">This property has no special rules! </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
            <div class="col-md-3">
            <div class="card">
                <div class="card-header">
                    Servicios
                </div>
                <ul class="list-group list-group-flush">
                    <c:choose>
                        <c:when test="${not empty property.services}">
                            <c:forEach var="service" items="${property.services}">
                                <li class="list-group-item">${service.name}</li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <li class="list-group-item"> This property has no special services! </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
            </div>
        </div>


    </div>


    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>
</html>
