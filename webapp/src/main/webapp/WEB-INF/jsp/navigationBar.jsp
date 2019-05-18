<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="/">AluProp</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-collapse" id="navbarCollapse">

        <ul class="navbar-nav mr-auto">
            <%--<li class="nav-item active">--%>
            <%--<a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>--%>
            <%--</li>--%>
            <%--<li class="nav-item">--%>
            <%--<a class="nav-link" href="#">Link</a>--%>
            <%--</li>--%>
            <%--<li class="nav-item">--%>
            <%--<a class="nav-link disabled" href="#">Disabled</a>--%>
            <%--</li>--%>
        </ul>
        <ul class="navbar-nav" style="float: right">
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <li><a class="nav-link mr-1 bold ${requestScope['javax.servlet.forward.request_uri'] == '/user/signUp' ? 'active':''}" href="/user/signUp"><spring:message code="label.nav.signup" /></a></li>
                <li><span class="nav-link mr-1"><spring:message code="label.nav.or" /></span></li>
                <li><a class="nav-link mr-1 bold ${requestScope['javax.servlet.forward.request_uri'] == '/user/logIn' ? 'active':''}" href="/user/logIn"><spring:message code="label.nav.login" /></a></li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a class="nav-link mr-1 bold active" href="#">Hi, ${pageContext.request.userPrincipal.name}!</a></li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a class="nav-link mr-1" href="/user/logOut">Log Out</a></li>
            </c:if>
            <c:if test="${userRole == '[ROLE_HOST]'}">
                <li><a class="nav-link mr-1 bold active" href="/host/create">Publish a property</a></li>
            </c:if>
            <li>
                <a href="?lang=en"> <img src="/resources/images/uk.svg" class="flag" alt="English"> </a>
            </li>
            <li>
                <a href="?lang=es"> <img src="/resources/images/spain.svg" class="flag" alt="Spanish"> </a>
            </li>
        </ul>

    </div>
</nav>