<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title>Profile</title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

        <!-- Bootstrap core css -->
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>

    <body data-gr-c-s-loaded="true">

        <%@include file="navigationBar.jsp"%>

        <div>
            <h1 class="profile-name"> IGNACIO VIDAURRETA </h1>
        </div>
        <div class="profile-data">
            <div class="card">
                <div class="card-header"> BIO: </div>
                <div class="card-body"> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vehicula, massa nec condimentum imperdiet, massa est convallis dolor, et mollis nisl magna eu massa. Suspendisse et elit non magna tempus dignissim. Praesent justo purus, consectetur a eleifend eu, cursus eu diam. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed placerat tortor, vel tempor lectus. Integer vel dui sit amet dui molestie commodo vel ac dolor. Aenean quis fringilla est. Pellentesque ac pellentesque ipsum. Duis at consequat libero. Nunc pharetra vehicula tortor, nec sodales elit semper quis. Cras venenatis magna ac dignissim maximus. Nulla condimentum vestibulum lacus, ut tincidunt. </div>
            </div>
            <div class="card">
                <div class="card-header"> Interested in: </div>
                <div class="card-body">
                    <ul>
                        <li> THIS HOUSE WHICH IS AWESOME </li>
                        <li> THIS OTHER HOUSE THAT IS AMAZING </li>
                        <li> DON'T EVEN GET ME STARTED WITH THIS HOUSE </li>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>