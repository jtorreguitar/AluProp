<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                        <img class="card-img-top" src="http://localhost:8080/images/${property.mainImageId}" alt="Card image">
                        <div class="card-body">
                            <h4 class="card-title">${property.description}</h4>
                            <p class="card-text">${property.caption}</p>
                            <p class="card-text bold">$${property.price}</p>
                            <p class="card-text">${property.neighbourhood}</p>
                            <a href="/${property.id}" class="btn btn-primary stretched-link"><spring:message code="label.properties.seeMore" /></a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h1><spring:message code="label.properties.noProperties" /></h1>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="row">
        <c:set var="total" value="${(totalElements%maxItems)>0? totalPages+1:totalPages}" />
        <c:set var="current" value="${currentPage+1}" />
        <c:choose>
            <c:when test="${not empty properties}">
                <ul class="pagination center">
                    <c:choose>
                        <c:when test="${current == 1}">
                            <li class="disabled page">
                                <a class="disabled">
                                    <i class="material-icons disabled"><</i>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="waves-effect page">
                                <a href="<c:url value="${changePageUrl}"> <c:param name="pageNumber" value="${current - 2}"/> </c:url>">
                                    <i class="material-icons"><</i>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                    <c:set var="just_one"
                           value="${(current - 4) <= 0 || total <= 10}"/>
                    <c:set var="no_more_prev"
                           value="${(current + 5) > total}"/>
                    <c:set var="the_first_ones"
                           value="${(current + 5) < 10}"/>
                    <c:set var="no_more_next"
                           value="${total < 10 || (current + 5) >= total}"/>
                    <c:forEach var="pageIt"
                               begin="${just_one ? 1 : no_more_prev ? total - 9 : current - 4}"
                               end="${no_more_next ? total : the_first_ones ? 10 : current + 5}">
                        <li id="page" class=${pageIt == current ? "active" : "waves-effect"}>
                            <c:choose>
                                <c:when test="${pageIt == current}">
                                    <a>
                                            ${pageIt}
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value="${changePageUrl}"> <c:param name="pageNumber" value="${pageIt-1}"/> </c:url>">
                                            ${pageIt}
                                    </a>
                                </c:otherwise>
                            </c:choose>

                        </li>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${current == total}">
                            <li class="disabled page">
                                <a class="disabled">
                                    <i class="material-icons disabled">></i>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="waves-effect page">
                                <a href="<c:url value="${changePageUrl}"> <c:param name="pageNumber" value="${current}"/> </c:url>">
                                    <i class="material-icons">></i>
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </c:when>
        </c:choose>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>
</html>
