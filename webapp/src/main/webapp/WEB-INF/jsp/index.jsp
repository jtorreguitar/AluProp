<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>AluProp</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />

</head>
<body data-gr-c-s-loaded="true">

    <!-- #######################     NAV BAR     ####################### -->


    <%@include file="navigationBar.jsp"%>



    <!-- ####################### CARDS CONTAINING PROPERTIES  #######################-->
    <div class="elem-container">
        <c:choose>
            <c:when test="${not empty properties}">
                <c:forEach var="property" items="${properties}">
                    <div class="card property-elem">
                        <img class="card-img-top" src="${property.image}" alt="Card image">
                        <div class="card-body">
                            <h4 class="card-title">${property.description}</h4>
                            <p class="card-text">${property.caption}</p>
                            <a href="/${property.id}" class="btn btn-primary stretched-link">Ver m&aacute;s</a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h1> Oops, there are no properties loaded </h1>
            </c:otherwise>
        </c:choose>

    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>
</html>
