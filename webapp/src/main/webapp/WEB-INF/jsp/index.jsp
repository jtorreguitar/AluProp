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

    <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>
    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</head>
<body data-gr-c-s-loaded="true">

    <!-- #######################     NAV BAR     ####################### -->

    <%@include file="navigationBar.jsp"%>

    <!-- ####################### CARDS CONTAINING PROPERTIES  #######################-->
    <c:if test="${isSearch == true}">
        <H1><spring:message code="label.search_results"/></H1>
    </c:if>
    <div class="row" style="margin-top: 30px;margin-bottom: 20px;width: 85%;margin-left: 16px">
        <div class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <spring:message code="order_by"/>
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="?orderBy=NEWEST&${requestScope['javax.servlet.forward.query_string']}"><spring:message code="order_by_most_recent"/></a>
                <a class="dropdown-item" href="?orderBy=CAPACITY_DESC&${requestScope['javax.servlet.forward.query_string']}"><spring:message code="order_by_capacity_desc"/></a>
                <a class="dropdown-item" href="?orderBy=CAPACITY&${requestScope['javax.servlet.forward.query_string']}"><spring:message code="order_by_capacity_ascen"/></a>
                <a class="dropdown-item" href="?orderBy=PRICE_DESC&${requestScope['javax.servlet.forward.query_string']}"><spring:message code="order_by_price_desc"/></a>
                <a class="dropdown-item" href="?orderBy=PRICE&${requestScope['javax.servlet.forward.query_string']}"><spring:message code="order_by_price_ascen"/></a>
                <a class="dropdown-item" href="?orderBy=BUDGET_DESC&${requestScope['javax.servlet.forward.query_string']}"><spring:message code="order_by_budget_desc"/></a>
                <a class="dropdown-item" href="?orderBy=BUDGET&${requestScope['javax.servlet.forward.query_string']}"><spring:message code="order_by_budget_ascen"/></a>
            </div>
        </div>
    </div>
    <div class="elem-container">
        <c:choose>
            <c:when test="${not empty properties}">
                <c:forEach var="property" items="${properties}">
                    <div class="card property-elem">
                        <img class="card-img-top" src="<c:url value="/images/${property.mainImage.id}"/>" alt="Card image">
                        <div class="card-body" style="display: flex;flex-direction: column; justify-content: space-between">
                            <div>
                                <h4 class="card-title">${property.description}</h4>
                                <h6 class="card-text">${property.neighbourhood.name}</h6>
                                <p class="card-text" style="margin-bottom: 12px;">${property.caption}</p>
                            </div>
                            <div style="display: flex;flex-direction: row;justify-content: space-between; align-items: end">
                                <a href="${pageContext.request.contextPath}/${property.id}" class="btn btn-primary stretched-link"><spring:message code="label.properties.seeMore" /></a>
                                <h5>$${property.price}</h5>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${isSearch == true}">
                        <h1><spring:message code="label.search.no_properties" /></h1>
                    </c:when>
                    <c:otherwise>
                        <h1><spring:message code="label.properties.noProperties" /></h1>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="row" style="margin-top: 30px;margin-bottom: 20px">
        <c:set var="maxItems" value ="12" />
        <c:set var="total" value="${(totalElements%maxItems)>0? totalPages+1:totalPages}" />
        <c:set var="current" value="${currentPage+1}" />
        <c:choose>
            <c:when test="${not empty properties}">
                <nav aria-label="Page navigation">
                <ul class="pagination">
                    <c:choose>
                        <c:when test="${current == 1}">
                            <li class="page-item disabled">
                                <a class="page-link" aria-disabled="true"> < </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link" href="?pageNumber=${current - 2}&${requestScope['javax.servlet.forward.query_string']}"> < </a></li>
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
                            <c:choose>
                                <c:when test="${pageIt == current}">
                                    <li class="page-item disabled">
                                    <a class="page-link" aria-disabled="true">
                                            ${pageIt}
                                    </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                    <a class="page-link" href="?pageNumber=${pageIt-1}&${requestScope['javax.servlet.forward.query_string']}">
                                            ${pageIt}
                                    </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${current == total}">
                            <li class="page-item disabled">
                            <a class="page-link"  aria-disabled="true"> > </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="?pageNumber=${current}&${requestScope['javax.servlet.forward.query_string']}"> > </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                </nav>
            </c:when>
        </c:choose>
    </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
