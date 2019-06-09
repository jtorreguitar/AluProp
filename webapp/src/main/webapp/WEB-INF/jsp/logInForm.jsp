    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>Log In</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">
    <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>


    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body data-gr-c-s-loaded="true">
<%@include file="navigationBar.jsp"%>

<div class="container">
    <div class="row justify-content-center">
        <aside class="col-md-6">
            <div class="card">
                <c:url value="/user/signUp" var="signUp"/>
                <header class="card-header">
                    <a href="${signUp}" class="float-right btn btn-outline-primary mt-1"><spring:message code="label.signup"/></a>
                    <h4 class="card-title mt-2"><spring:message code="label.login"/></h4>
                </header>
                <article class="card-body">
                    <c:url value="/user/logIn" var="loginUrl" />
                    <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
                        <div class="form-group">
                            <label for="email"> <spring:message code="forms.email"/></label>
                            <input id="email" name="email" class="form-control" placeholder="Email" type="email"/>
                        </div> <!-- form-group// -->
                        <div class="form-group">
                            <%--<a class="float-right" href="#"><spring:message code="label.forgot_password"/></a>--%>
                            <label for="password"><spring:message code="label.password"/></label>
                            <input id="password" name="password" class="form-control" placeholder="******" type="password"/>
                        </div> <!-- form-group// -->
                        <div class="form-group">
                            <div class="checkbox">
                                <label for="rememberme"><input id="rememberme" name="rememberme" type="checkbox"/><spring:message code="label.remember_me"/></label>
                            </div> <!-- checkbox .// -->
                        </div> <!-- form-group// -->
                        <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                            <span class="formError"> <spring:message code="system.invalid_user_or_pass"/></span>
                        </c:if>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block"> <spring:message code="label.login"/> </button>
                        </div> <!-- form-group// -->
                    </form>
                </article>
            </div> <!-- card.// -->

        </aside> <!-- col.// -->
    </div> <!-- row.// -->

</div>
</body>
</html>
