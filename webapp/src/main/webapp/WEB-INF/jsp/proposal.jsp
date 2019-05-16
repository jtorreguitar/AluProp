<html>
<head>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title>Property</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

        <!-- Bootstrap core css -->
        <link href="resources/css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>
</head>
<body>

<!-- #######################     NAV BAR     ####################### -->

<%@include file="navigationBar.jsp"%>
<div class="card">
    <div class="card-header">
        Usuarios interesados
    </div>
    <ul class="list-group list-group-flush">
        <c:choose>
            <c:when test="${not empty proposalUsers}">
                <c:forEach var="user" items="${proposalUsers}">
                    <li class="list-group-item">${user.name}</li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li class="list-group-item">No other users have shown interest in this property!</li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>

</body>
</html>
