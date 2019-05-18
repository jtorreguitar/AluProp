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

        <h1 class="display-4">Name Surname</h1>
        <h5 class="text-muted">University</h5>
        <h6 class="text-muted">dd/mm/yyyy</h6>
        <div>
        <br>
        </div>
        <div class="profile-data">
            <div class="card">
                <div class="card-header">Bio</div>
                <div class="card-body"> Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vehicula, massa nec condimentum imperdiet, massa est convallis dolor, et mollis nisl magna eu massa. Suspendisse et elit non magna tempus dignissim. Praesent justo purus, consectetur a eleifend eu, cursus eu diam. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sed placerat tortor, vel tempor lectus. Integer vel dui sit amet dui molestie commodo vel ac dolor. Aenean quis fringilla est. Pellentesque ac pellentesque ipsum. Duis at consequat libero. Nunc pharetra vehicula tortor, nec sodales elit semper quis. Cras venenatis magna ac dignissim maximus. Nulla condimentum vestibulum lacus, ut tincidunt. </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            Interests
                        </div>
                        <div class="list-group">
                            <a href="#" class="list-group-item list-group-item-action">Dapibus ac facilisis in</a>
                            <a href="#" class="list-group-item list-group-item-action">Morbi leo risus</a>
                            <a href="#" class="list-group-item list-group-item-action">Porta ac consectetur ac</a>
                            <a href="#" class="list-group-item list-group-item-action disabled">Vestibulum at eros</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-header">
                            Proposals
                        </div>
                        <div class="list-group">
                            <a href="#" class="list-group-item list-group-item-action proposal-list"><div><span class="title">Lel this is a proposal</span></div><div class="proposal-badge"><span class="badge badge-secondary">Pending</span></div></a>
                            <a href="#" class="list-group-item list-group-item-action proposal-list"><div><span class="title">This house is really cool</span></div><div class="proposal-badge"><span class="badge badge-success">Sent</span></div></a>
                            <a href="#" class="list-group-item list-group-item-action proposal-list"><div><span class="title">Loft as cool as the office</span></div><div class="proposal-badge"><span class="badge badge-secondary">Pending</span></div></a>
                            <a href="#" class="list-group-item list-group-item-action proposal-list"><div><span class="title">Dopest property ever</span></div><div class="proposal-badge"><span class="badge badge-success">Sent</span></div></a>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>