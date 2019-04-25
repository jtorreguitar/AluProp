<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>Log In</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

    <!-- Bootstrap core css -->
    <link href="resources/css/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body data-gr-c-s-loaded="true">
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
            <li><a class="nav-link mr-1 bold" href="/signup">Sign up</a></li>
            <li><span class="nav-link mr-1">or</span></li>
            <li><a class="nav-link mr-1 bold active" href="#">Log in</a></li>
        </ul>
        <%--<form class="form-inline mt-2 mt-md-0 search-bar-form">--%>
        <%--<input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">--%>
        <%--<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>--%>
        <%--</form>--%>

    </div>
</nav>

<div class="container">
    <div class="row justify-content-center">
        <aside class="col-md-6">
            <div class="card">
                <header class="card-header">
                    <a href="/signup" class="float-right btn btn-outline-primary mt-1">Sign up</a>
                    <h4 class="card-title mt-2">Log in</h4>
                </header>
                <article class="card-body">

                    <form>
                        <div class="form-group">
                            <label>Your email</label>
                            <input name="" class="form-control" placeholder="Email" type="email">
                        </div> <!-- form-group// -->
                        <div class="form-group">
                            <a class="float-right" href="#">Forgot?</a>
                            <label>Your password</label>
                            <input class="form-control" placeholder="******" type="password">
                        </div> <!-- form-group// -->
                        <div class="form-group">
                            <div class="checkbox">
                                <label> <input type="checkbox"> Save password </label>
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
