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

    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />

</head>
<body data-gr-c-s-loaded="true">
<%@include file="navigationBar.jsp"%>

<div class="container">
    <div class="row justify-content-center">
        <aside class="col-md-6">
            <div class="card">
                <header class="card-header">
                    <a href="/user/signUp" class="float-right btn btn-outline-primary mt-1">Sign up</a>
                    <h4 class="card-title mt-2">Log in</h4>
                </header>
                <article class="card-body">
                    <c:url value="/user/logIn" var="loginUrl" />
                    <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
                        <div class="form-group">
                            <label for="email">Your email</label>
                            <input id="email" name="email" class="form-control" placeholder="Email" type="email"/>
                        </div> <!-- form-group// -->
                        <div class="form-group">
                            <a class="float-right" href="#">Forgot?</a>
                            <label for="password">Your password</label>
                            <input id="password" name="password" class="form-control" placeholder="******" type="password"/>
                        </div> <!-- form-group// -->
                        <div class="form-group">
                            <div class="checkbox">
                                <label for="rememberme">Remember me </label>
                                <input id="rememberme" name="rememberme" type="checkbox"/>
                            </div> <!-- checkbox .// -->
                        </div> <!-- form-group// -->
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block"> Login  </button>
                        </div> <!-- form-group// -->
                    </form>
                </article>
            </div> <!-- card.// -->

        </aside> <!-- col.// -->
    </div> <!-- row.// -->

</div>
</body>
</html>
