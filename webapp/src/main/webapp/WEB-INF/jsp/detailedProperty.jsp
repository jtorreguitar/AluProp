<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title><spring:message code="label.property"/></title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">
        <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>


        <!-- Bootstrap core css -->
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>
</head>
<body>

    <!-- #######################     NAV BAR     ####################### -->

    <%@include file="navigationBar.jsp"%>


    <!-- #######################     PROPERTY     ####################### -->

    <div class="container">
        <c:choose>
            <c:when test="${not empty property.images}">
            <div id="myCarousel" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <c:forEach var="image" items="${property.images}" varStatus="loop">
                        <li data-target="#myCarousel" data-slide-to="${loop.index}"></li>
                    </c:forEach>

                </ol>
                <div class="carousel-inner">
                    <c:forEach var="image" items="${property.images}" varStatus="i">
                        <div class="carousel-item ${i.index == 0?"active":""}">
                            <c:url value="/images/" var="imageUrl"/>
                            <img src="${imageUrl}/${image.id}" class="d-block w-100 carousel-image">
                        </div>
                    </c:forEach>
                </div>
                <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>
            </c:when>
            <c:otherwise>
                <h1> <spring:message code="system.property.no_images_error"/> </h1>
            </c:otherwise>
        </c:choose>
        <br>
        <div class="my-elem-container">
            <div class="row">
                <div class="col-md-8">
                    <div class="card acqua">
                        <div class="card-body">
                            <div>
                                <spring:message code="forms.privacy.shared" var="privacy_shared"/>
                                <spring:message code="forms.privacy.individual" var="privacy_individual"/>
                                <H2>${property.description}</H2>
                                <H6>
                                    <c:choose>
                                        <c:when test="${property.propertyType == 'HOUSE'}">
                                            <spring:message code="forms.house"/>
                                        </c:when>
                                        <c:when test="${property.propertyType == 'APARTMENT'}">
                                            <spring:message code="forms.apartment"/>
                                        </c:when>
                                        <c:when test="${property.propertyType == 'LOFT'}">
                                            <spring:message code="forms.loft"/>
                                        </c:when>
                                    </c:choose>
                                    <spring:message code="label.properties.in"/> ${property.neighbourhood.name}</H6>
                                <H8>${property.capacity} <spring:message code="label.guests"/> | ${property.privacyLevel?privacy_shared:privacy_individual}</H8>
                            </div>
                            <br>
                            <div class="card">
                                <div class="card-header"><spring:message code="property.description"/></div>
                                <div class="card-body">
                                   ${property.caption}
                                </div>
                            </div>
                            <br>
                            <div class="card">
                                <div class="card-header">
                                    <spring:message code="property.rules"/>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <c:choose>
                                        <c:when test="${not empty property.rules}">
                                            <c:forEach var="rule" items="${property.rules}">
                                                <li class="list-group-item">${rule.name}</li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="list-group-item"> <spring:message code="property.no_special_rules"/> </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>
                            <br>
                            <div class="card">
                                <div class="card-header">
                                    <spring:message code="property.services"/>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <c:choose>
                                        <c:when test="${not empty property.services}">
                                            <c:forEach var="service" items="${property.services}">
                                                <li class="list-group-item">${service.name}</li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="list-group-item"> <spring:message code="property.no_special_services"/> </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card acqua">
                        <div class="card-body">
                            <H4 class="price">$${property.price} <spring:message code="property.per_month"/></H4><br>
                            <spring:message code="user.interested" var="interested"/>
                            <spring:message code="user.not_interested" var="not_interested"/>
                                <c:choose>
                                    <c:when test="${currentUser.role == 'ROLE_HOST' && currentUser.id == property.owner.id}">
                                        <div class="flex-container" style="display: flex;flex-direction: column;">
                                            <c:choose>
                                                <c:when test="${property.availability == 'AVAILABLE'}">
                                                    <c:url value="/host/changeStatus/" var="postPath"/>
                                                    <form class="my-form" action="${postPath}${property.id}" method="post">
                                                        <button type="submit" class="btn btn-secondary" style="width: -moz-available;margin-bottom: 12px;"><spring:message code="label.properties.pause"/></button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:url value="/host/changeStatus/" var="postPath"/>
                                                    <form class="my-form" action="${postPath}${property.id}" method="post">
                                                        <button type="submit" class="btn btn-success" style="width: -moz-available;margin-bottom: 12px;"><spring:message code="label.properties.activate"/></button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:url value="/host/delete/" var="postPath"/>
                                            <form class="my-form" action="${postPath}${property.id}" method="post">
                                                <button type="submit" class="btn btn-danger" style="width: -moz-available;"><spring:message code="label.properties.delete"/></button>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:when test="${currentUser.role == 'ROLE_HOST'}">
                                    </c:when>
                                    <c:when test="${currentUser.role == 'ROLE_GUEST' && userInterested == true}">
                                        <div class="flex-container" style="justify-content: space-around">
                                            <c:choose>
                                                <c:when test="${userInterested == true || currentUser.role == 'ROLE_HOST'}">
                                                    <c:url value="/proposal/create/${property.id}" var="postPath"/>
                                                    <form:form modelAttribute="proposalForm" action="${postPath}" style="width: -moz-available;" method="post">
                                                        <div class="card">
                                                            <div class="card-header" style="display: flex;justify-content: space-between;">
                                                                <spring:message code="user.interested_users"/>
                                                            </div>
                                                            <ul class="list-group list-group-flush">
                                                                <c:choose>
                                                                    <c:when test="${not empty interestedUsers and interestedUsers.size() > 1}">
                                                                        <c:forEach var="user" items="${interestedUsers}">
                                                                            <c:if test="${user.id != currentUser.id}">
                                                                                <li class="list-group-item">
                                                                                <c:choose>
                                                                                    <c:when test="${currentUser.role != 'ROLE_HOST'}">
                                                                                        <div style="display: flex;flex-direction: row;justify-content: space-between">
                                                                                                <div>
                                                                                                    ${user.name}
                                                                                                    <p><em><small>${user.university.name} - ${user.age} - ${user.gender.toString().toLowerCase()}</small></em></p>
                                                                                                </div>
                                                                                                <div style="display: flex;align-items: center;">
                                                                                                    <a href="<c:url value="/user/${user.id}"/>"><button type="button" class="btn btn-link"><spring:message code="label.profile"/></button></a>
                                                                                                </div>
                                                                                        </div>
                                                                                </li>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <a href="<c:url value="/user/${user.id}"/>"><label class="checkbox">${user.name}</label></a></li>
                                                                                    </c:otherwise>
                                                                                </c:choose>

                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <li class="list-group-item"><spring:message code="property.no_users_interested"/></li>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </ul>
                                                        </div>
                                                        <br>
                                                        <div class="flex-container" style="justify-content: space-around">
                                                            <spring:message code="user.create_proposal" var="createProposal"/>
                                                            <c:if test="${currentUser.role != 'ROLE_HOST'}">
                                                                <input value="${createProposal}" style="cursor:pointer;width: -moz-available;" class="btn btn-primary stretched-link confirm-proposal" data-toggle="modal" data-target="#exampleModalCenter"/>
                                                                <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                                                        <div class="modal-content" style="padding: 0.5rem;margin-left:-5rem;width:200rem">
                                                                            <div class="modal-header" style="flex-direction: column">
                                                                                <div style="display: flex;justify-content: space-between;width:100%;padding-bottom: 5px;">
                                                                                    <h5 class="modal-title" id="exampleModalCenterTitle"><spring:message code="user.create_proposal"/></h5>
                                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                        <span aria-hidden="true">&times;</span>
                                                                                    </button>
                                                                                </div>
                                                                                <spring:message code="proposal.createExplanation" arguments="${property.capacity-1}"/>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <c:choose>
                                                                                    <c:when test="${not empty interestedUsers and interestedUsers.size() > 1}">
                                                                                        <c:forEach var="user" items="${interestedUsers}">
                                                                                            <c:if test="${user.id != currentUser.id}">
                                                                                                <label class="checkbox" style="align-self: center;margin-bottom: 0px;display: flex;align-content: center;width:100%;">
                                                                                                    <div class="list-group-item" style="width: 100%;display: flex;flex-direction: row;justify-content: space-between">
                                                                                                        <div>
                                                                                                            <form:checkbox class="proposal-checkbox" path="invitedUsersIds" style="margin-right: 6px;" value="${user.id}"/> ${user.name}
                                                                                                            <p style="margin-bottom: 0px;margin-left: 26px;"><em><small>${user.university.name} - ${user.age} - ${user.gender.toString().toLowerCase()}</small></em></p>
                                                                                                        </div>
                                                                                                        <div class="d" style="display: flex;align-items: center;">
                                                                                                            <a href="<c:url value="/user/${user.id}"/>"><button type="button" class="btn btn-link"><spring:message code="label.profile"/></button></a>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </label>
                                                                                            </c:if>
                                                                                        </c:forEach>
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <li class="list-group-item"><spring:message code="property.no_users_interested"/></li>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <div>
                                                                                    <span id="selected-guests" style="display: none;">
                                                                                        <spring:message code="proposal.youHaveSelected"/>
                                                                                        <span style="font-weight:bold;" id="selected-users"></span>
                                                                                        <spring:message code="proposal.eachGuestWillBePaying"/>
                                                                                    </span>
                                                                                    <span id="selected-no-guests">
                                                                                        <spring:message code="proposal.youWillBePaying"/>
                                                                                    </span>
                                                                                    <span style="font-weight: bold;" id="price-per-person">${property.price}</span>
                                                                                    <span style="font-weight: bold;"><spring:message code="proposal.month"/></span>
                                                                                </div>
                                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="label.close"/></button>
                                                                                <button type="submit" class="btn btn-primary" type="submit" value="${createProposal}"><spring:message code="proposal.create"/></button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${maxPeople != null}">
                                                                <span class="formError"><spring:message code="forms.proposal.max" arguments="${maxPeople}"/></span>
                                                            </c:if>
                                                        </div>
                                                    </form:form>
                                                </c:when>
                                                <c:otherwise>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:url value="/${property.id}/deInterest/" var="postPath"/>
                                            <form class="my-form" action="${postPath}" method="POST" style="width: 100%;">
                                                <input type="submit" value="${not_interested}" style="width: 100%;color:white;background-color:red;border-color:red" class="btn btn-primary stretched-link"/>
                                            </form>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/${property.id}/guest/interest/" var="postPath"/>
                                        <form class="my-form" action="${postPath}" method="POST">
                                            <input type="submit" value="${interested}" style="width: 100%" class="btn btn-primary stretched-link"/>
                                        </form>
                                    </c:otherwise>
                            </c:choose>
                            <c:if test="${param.noLogin == true}">
                                <p class="formError"><spring:message code="system.must_be_logged_in_interest"/></p>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        //var n = $(":checkbox:checked");//$("input:checkbox:checked").length;
        $(document).on("click", ".confirm-proposal", function () {
            var count = 4;
            $(".modal-body #lel").val( count );
        });
        var totalPrice =<c:out value="${property.price}"/>;
        var checkedBoxes = 0;
        $('.proposal-checkbox').change(function() {
            if ($(this).is(':checked')){
                checkedBoxes = checkedBoxes + 1;
            } else{
                checkedBoxes = checkedBoxes - 1;
            }
            $('#selected-users').html(checkedBoxes);
            $('#price-per-person').html((totalPrice/(1+checkedBoxes)).toFixed(2));
            if (checkedBoxes === 0){
                $('#selected-no-guests').show();
                $('#selected-guests').css('display', 'none');
            } else {
                $('#selected-no-guests').css('display', 'none');
                $('#selected-guests').show();
            }

        });
    </script>
</body>
</html>
