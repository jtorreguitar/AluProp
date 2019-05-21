<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="<c:url value="/"/>">AluProp</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-collapse" id="navbarCollapse">

        <ul class="navbar-nav mr-auto">
        </ul>
        <ul class="navbar-nav" style="float: right">
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <li><a class="nav-link mr-1 bold ${requestScope['javax.servlet.forward.request_uri'] == '/user/signUp' ? 'active':''}" href="<c:url value="/user/signUp"/>"><spring:message code="label.signup" /></a></li>
                <li><span class="nav-link mr-1"><spring:message code="label.nav.or" /></span></li>
                <li><a class="nav-link mr-1 bold ${requestScope['javax.servlet.forward.request_uri'] == '/user/logIn' ? 'active':''}" href="<c:url value="/user/logIn"/>"><spring:message code="label.login" /></a></li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a class="nav-link mr-1 bold active" href="<c:url value="/user/profile"/>"><spring:message code="user.greeting" arguments="${currentUser.name}"/> </a></li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a class="nav-link mr-1" href="<c:url value="/user/logOut"/>"> <spring:message code="user.logout"/> </a></li>
            </c:if>
            <c:if test="${userRole == '[ROLE_HOST]'}">
                <li><a class="nav-link mr-1 bold active" href="<c:url value="/host/create"/>"> <spring:message code="host.publish_property"/> </a></li>
            </c:if>
            <spring:message code="label.english" var="language_en"/>
            <spring:message code="label.spanish" var="language_es"/>
            <li>
                <a href="?lang=en"> <img src="<c:url value="/resources/images/uk.svg"/>" class="flag" alt="${language_en}"> </a>
            </li>
            <li>
                <a href="?lang=es"> <img src="<c:url value="/resources/images/spain.svg"/>" class="flag" alt="${language_es}"> </a>
            </li>
        </ul>

    </div>
</nav>