<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>Sign Up</title>

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
                <li class="nav-item"><a href="/logIn" class="nav-link">Log In</a></li>
                <li class="nav-item active"><a href="#" class="nav-link">Sign Up</a></li>
            </ul>
            <%--<form class="form-inline mt-2 mt-md-0 search-bar-form">--%>
            <%--<input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">--%>
            <%--<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>--%>
            <%--</form>--%>

        </div>
    </nav>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <header class="card-header">
                        <a href="/logIn" class="float-right btn btn-outline-primary mt-1">Log in</a>
                        <h4 class="card-title mt-2">Sign up</h4>
                    </header>
                    <article class="card-body">
                        <form>
                            <div class="form-row">
                                <div class="col form-group">
                                    <label>First name </label>
                                    <input type="text" class="form-control" placeholder="">
                                </div> <!-- form-group end.// -->
                                <div class="col form-group">
                                    <label>Last name</label>
                                    <input type="text" class="form-control" placeholder=" ">
                                </div> <!-- form-group end.// -->
                            </div> <!-- form-row end.// -->
                            <div class="form-group">
                                <label>Email address</label>
                                <input type="email" class="form-control" placeholder="">
                                <small class="form-text text-muted">We'll never share your email with anyone else.</small>
                            </div> <!-- form-group end.// -->
                            <div class="form-group">
                                <label class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" value="option1">
                                    <span class="form-check-label"> Male </span>
                                </label>
                                <label class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="gender" value="option2">
                                    <span class="form-check-label"> Female</span>
                                </label>
                            </div> <!-- form-group end.// -->
                            <%--<div class="form-row">--%>
                                <%--<div class="form-group col-md-6">--%>
                                    <%--<label>City</label>--%>
                                    <%--<input type="text" class="form-control">--%>
                                <%--</div> <!-- form-group end.// -->--%>
                                <%--<div class="form-group col-md-6">--%>
                                    <%--<label>Country</label>--%>
                                    <%--<select id="inputState" class="form-control">--%>
                                        <%--<option> Choose...</option>--%>
                                        <%--<option>Uzbekistan</option>--%>
                                        <%--<option>Russia</option>--%>
                                        <%--<option selected="">United States</option>--%>
                                        <%--<option>India</option>--%>
                                        <%--<option>Afganistan</option>--%>
                                    <%--</select>--%>
                                <%--</div> <!-- form-group end.// -->--%>
                            <%--</div> <!-- form-row.// -->--%>
                            <div class="form-group">
                                <label>Password</label>
                                <input class="form-control" type="password">
                                <label>Re-enter password</label>
                                <input class="form-control" type="password">
                            </div> <!-- form-group end.// -->
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary btn-block"> Register  </button>
                            </div> <!-- form-group// -->
                            <small class="text-muted">By clicking the 'Sign Up' button, you confirm that you accept our <br> Terms of use and Privacy Policy.</small>
                        </form>
                    </article> <!-- card-body end .// -->
                    <div class="border-top card-body text-center">Have an account? <a href="">Log In</a></div>
                </div> <!-- card.// -->
            </div> <!-- col.//-->

        </div> <!-- row.//-->


    </div>
    <!--container end.//-->

</body>
</html>
