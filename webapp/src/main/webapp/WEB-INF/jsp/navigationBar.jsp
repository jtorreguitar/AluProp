<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<head>--%>
    <%--<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">--%>
    <%--<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>--%>
    <%--<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>--%>
<%--</head>--%>

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="<c:url value="/"/>">AluProp</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-collapse collapse" id="navbarCollapse">

        <ul class="navbar-nav mr-auto">
        </ul>
        <ul class="navbar-nav" style="float: right">
                <li>
                    <div class="input-group" id="adv-search">
                        <c:url value="/search/" var="postPath"/>
                        <form:form modelAttribute="filteredSearchForm"  action="${postPath}" method="post" style="display:flex;margin-bottom: 0px;">
                            <spring:message code="label.search" var="searchPlaceholder"/>
                            <form:input path="description" type="text" class="form-control" placeholder="${searchPlaceholder}" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px;"/>
                            <form:errors path="description" cssClass="formError" element="p"/>
                            <div class="input-group-btn">
                                <div class="btn-group" role="group">
                                    <div class="dropdown dropdown-lg" style="display:flex;">
                                            <button type="button" id="selection-btn" style="height:inherit;background-color: white;" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false"></button>
                                        <div class="dropdown-menu dropdown-menu-right" role="menu">
                                                <div class="form-group">
                                                    <form:label path="propertyType"><spring:message code="forms.property_type"/></form:label>
                                                    <form:select path="propertyType" class="form-control">
                                                        <form:option value="-1" selected="true"><spring:message code="forms.choose"/></form:option>
                                                        <form:option value="0"><spring:message code="forms.house"/></form:option>
                                                        <form:option value="1"><spring:message code="forms.apartment"/></form:option>
                                                        <form:option value="2"><spring:message code="forms.loft"/></form:option>
                                                    </form:select>
                                                    <form:errors path="propertyType" cssClass="formError" element="p"/>
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="neighbourhoodId"><spring:message code="forms.neighborhood"/></form:label>
                                                    <form:select path="neighbourhoodId" id="select-neighbourhood" name="neighbourhoods" class="form-control">
                                                        <form:option value="-1"><spring:message code="forms.choose"/></form:option>
                                                        <c:forEach var="neighbourhood" items="${neighbourhoods}">
                                                            <form:option value="${neighbourhood.id}">${neighbourhood.name}</form:option>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:errors path="neighbourhoodId" cssClass="formError" element="p"/>
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="privacyLevel"><spring:message code="forms.privacy"/></form:label>
                                                    <form:select path="privacyLevel" class="form-control">
                                                        <form:option value="-1" selected="true"><spring:message code="forms.choose"/></form:option>
                                                        <form:option value="0"><spring:message code="forms.privacy.individual"/></form:option>
                                                        <form:option value="1"><spring:message code="forms.privacy.shared"/></form:option>
                                                    </form:select>
                                                    <form:errors path="privacyLevel" cssClass="formError" element="p"/>
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="capacity"><spring:message code="forms.capacity"/></form:label>
                                                    <form:input path="capacity" type="text" class="form-control" placeholder=""></form:input>
                                                    <form:errors path="capacity" cssClass="formError" element="p"/>
                                                </div>
                                                <spring:message code="forms.rent_per_month"/>
                                                <div class="form-row">
                                                    <div class="form-group col-md-6">
                                                            <%--<label>Min</label>--%>
                                                        <form:label path="minPrice"><spring:message code="forms.rent_per_month_min"/></form:label>
                                                        <form:input path="minPrice" type="number" class="form-control" placeholder="Min"></form:input>
                                                        <form:errors path="minPrice" cssClass="formError" element="p"/>
                                                    </div>
                                                    <div class="form-group col-md-6 text-right">
                                                            <%--<label>Max</label>--%>
                                                        <form:label path="maxPrice"><spring:message code="forms.rent_per_month_max"/></form:label>
                                                        <form:input path="maxPrice" type="number" class="form-control" placeholder="Max"></form:input>
                                                        <form:errors path="maxPrice" cssClass="formError" element="p"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="ruleIds"><spring:message code="forms.rules"/></form:label>
                                                    <c:forEach var="rule" items="${rules}">
                                                        <label class="checkbox"><form:checkbox path="ruleIds" value="${rule.id}"/>${" "}${rule.name}</label>
                                                    </c:forEach>
                                                    <form:errors path="ruleIds" cssClass="formError" element="p"/>
                                                </div>
                                                <div class="form-group">
                                                    <form:label path="serviceIds"><spring:message code="forms.services"/></form:label>
                                                    <c:forEach var="service" items="${services}">
                                                        <label class="checkbox"><form:checkbox path="serviceIds" value="${service.id}"/>${" "}${service.name}</label>
                                                    </c:forEach>
                                                    <form:errors path="serviceIds" cssClass="formError" element="p"/>
                                                </div>
                                                <%--<button type="submit" class="btn btn-primary">Search</button>--%>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary">${searchPlaceholder}</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </li>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <li><a class="nav-link mr-1 bold ${requestScope['javax.servlet.forward.request_uri'] == '/user/signUp' ? 'active':''}" style="padding-right:0px;" href="<c:url value="/user/signUp"/>"><spring:message code="label.signup" /></a></li>
                <li><span class="nav-link mr-1" style="padding-left:0px;padding-right:0px;"><spring:message code="label.nav.or" /></span></li>
                <li><a class="nav-link mr-1 bold ${requestScope['javax.servlet.forward.request_uri'] == '/user/logIn' ? 'active':''}" style="padding-left:0px;" href="<c:url value="/user/logIn"/>"><spring:message code="label.login" /></a></li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><a class="nav-link mr-1 bold active" href="<c:url value="/user/profile"/>"><spring:message code="user.greeting" arguments="${currentUser.name}"/> </a></li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li><div><a class="nav-link mr-1" href="<c:url value="/user/logOut"/>"> <spring:message code="user.logout"/> </a></div></li>
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

        <script>
            $('.dropdown-menu').click(function(e) {
                e.stopPropagation();
            });
        </script>
    </div>
</nav>