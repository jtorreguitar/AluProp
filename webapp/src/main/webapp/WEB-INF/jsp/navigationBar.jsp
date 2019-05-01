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
            <li><a class="nav-link mr-1 bold active" href="/user/signUp">Sign up</a></li>
            <li><span class="nav-link mr-1">or</span></li>
            <li><a class="nav-link mr-1 bold" href="/user/logIn">Log in</a></li>
        </ul>
    </div>
</nav>