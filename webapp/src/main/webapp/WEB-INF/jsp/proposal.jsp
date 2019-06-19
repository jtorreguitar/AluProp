<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title><spring:message code="label.proposal.proposal"/></title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">
        <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>


        <!-- Bootstrap core css -->
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="<c:url value="/resources/js/urlUtility.js"/>" ></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>
</head>
<body>

<!-- #######################     NAV BAR     ####################### -->

<%@include file="navigationBar.jsp"%>

<div class="card" style="width: 80%;">

    <img class="card-img-top ${proposal.state == 'ACCEPTED'?'':proposal.state == 'PENDING'?'':proposal.state == 'SENT'?'':'grayscale'}" src="<c:url value="/images/${property.mainImage.id}"/>" alt="Card image cap">
    <div class="card-body">
        <h5 class="card-title"><spring:message code="label.proposal.proposal"/>: <a href="<c:url value="/${property.id}"/>">${property.description}</a></h5>
        <p class="card-text">${property.caption}</p>
    </div>
    <div class="card-header">
        <spring:message code="user.interested_users"/>
    </div>
    <ul class="list-group list-group-flush">
        <a href="<c:url value="/user/${creator.id}"/>" class="list-group-item list-group-item-action">
            <div style="display: flex;justify-content: space-between;align-items: center">${creator.name}
                <c:if test="${proposal.state == 'ACCEPTED'}"> | ${creator.email} | ${creator.contactNumber}</c:if>
                <span><img src="<c:url value="/resources/images/star.png"/>" class="flag" alt="${language_en}"></span>
            </div>
        </a>
        <c:if test="${not empty proposalUsers}">
            <c:forEach var="user" items="${proposalUsers}" varStatus="i">
                <a href="<c:url value="/user/${user.id}"/>" class="list-group-item list-group-item-action">
                    <div style="display: flex;justify-content: space-between;align-items: center">${user.name}
                        <c:if test="${proposal.state == 'ACCEPTED'}"> | ${user.email} | ${user.contactNumber}</c:if>
                        <span>
                            <c:choose>
                                <c:when test="${userStates[i.index] == 0 }">
                                    <img src="<c:url value="/resources/images/clock.png"/>" class="flag" alt="${language_en}">
                                </c:when>
                                <c:when test="${userStates[i.index] == 1}">
                                    <img src="<c:url value="/resources/images/check.png"/>" class="flag" alt="${language_en}">
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value="/resources/images/cross.png"/>" class="flag" alt="${language_en}">
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </a>
            </c:forEach>
        </c:if>
    </ul>
    <div class="card-body" id="answer">
        <div class="row" style="display:flex;justify-content:center;">
            <c:choose>
                <c:when test="${proposal.state == 'DROPPED'}">
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${currentUser.role == 'ROLE_HOST'}">
                                <spring:message code="proposal.proposal_declined_host"/>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="proposal.proposal_dropped"/>
                                <a href="<c:url value="/"/>"><spring:message code="proposal.findAnotherProperty"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:when test="${proposal.state == 'CANCELED'}">
                    <div class="card-body">
                        <spring:message code="proposal.proposal_canceled"/>
                        <a href="<c:url value="/"/>"><spring:message code="proposal.findAnotherProperty"/></a>
                    </div>
                </c:when>
                <c:when test="${proposal.state == 'DECLINED'}">
                    <div class="card-body">
                        <spring:message code="proposal.proposal_declined"/>
                        <a href="<c:url value="/"/>"><spring:message code="proposal.findAnotherProperty"/></a>
                    </div>
                </c:when>
                <c:when test="${proposal.state == 'ACCEPTED'}">
                    <div class="card-body" style="display: flex;flex-direction: column;align-items: center">
                        <c:choose>
                            <c:when test="${currentUser.role == 'ROLE_HOST'}">
                                <spring:message code="proposal.proposal_accepted_host"/>
                            </c:when>
                            <c:otherwise>
                                <div>
                                    <spring:message code="proposal.proposal_accepted_guest"/>
                                </div>
                                <br/>
                                <div>
                                    <spring:message code="proposal.proposal_accepted_host_info"/>${proposal.property.owner.name} | ${proposal.property.owner.email} | ${proposal.property.owner.contactNumber}
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:when test="${proposal.state == 'SENT'}">
                    <div class="card-body">
                        <spring:message code="proposal.proposal_sent"/>
                    </div>
                </c:when>
                <c:when test="${creator.id == currentUser.id}">
                    <c:url value="/guest/delete/${proposal.id}" var="postPath"/>
                    <form action="${postPath}" method="post">
                        <button type="submit" class="btn btn-secondary"><spring:message code="label.proposal.cancel"/></button>
                    </form>
                </c:when>
                <c:when test="${isInvited == true && !hasReplied}">
                    <div class="col-6">
                        <c:url value="/guest/accept/${proposal.id}" var="postPath"/>
                        <form action="${postPath}" method="post">
                            <button type="submit" class="btn btn-success"><spring:message code="label.proposal.accept"/></button>
                        </form>
                    </div>
                    <div class="col-6">
                        <c:url value="/guest/decline/${proposal.id}" var="postPath"/>
                        <form action="${postPath}" method="post">
                            <button type="submit" class="btn btn-danger"><spring:message code="label.proposal.decline"/></button>
                        </form>
                    </div>
                </c:when>
                <c:when test="${isInvited == true && hasReplied}">
                    <spring:message code="label.proposal.already_replied"/>
                </c:when>
                <c:otherwise>
                   <div style="display: flex;flex-direction: column">
                       <div style="margin-bottom:20px;align-self: center;"><spring:message code="label.proposal.your_prpoperty"/></div>
                       <c:if test="${proposal.property.availability == 'RENTED'}"><div class="formError" style="margin-bottom:20px"><spring:message code="proposal.rented_host"/></div></c:if>
                       <div style="display: flex;flex-direction: row;justify-content: space-around">
                           <c:choose>
                               <c:when test="${proposal.property.availability == 'RENTED'}">
                                   <c:url value="/host/decline/${proposal.id}" var="postPath"/>
                                   <form action="${postPath}" method="post" style="margin-block-end:0;">
                                       <button type="submit" class="btn btn-danger" disabled><spring:message code="label.proposal.decline"/></button>
                                   </form>
                                   <c:url value="/host/accept/${proposal.id}" var="postPath"/>
                                   <form action="${postPath}" method="post" style="margin-block-end:0;">
                                       <button type="submit" class="btn btn-success" disabled><spring:message code="label.proposal.accept"/></button>
                                   </form>
                               </c:when>
                               <c:otherwise>
                                   <c:url value="/host/decline/${proposal.id}" var="postPath"/>
                                   <form action="${postPath}" method="post" style="margin-block-end:0;">
                                       <button type="submit" class="btn btn-danger"><spring:message code="label.proposal.decline"/></button>
                                   </form>
                                   <c:url value="/host/accept/${proposal.id}" var="postPath"/>
                                   <form action="${postPath}" method="post" style="margin-block-end:0;">
                                       <button type="submit" class="btn btn-success"><spring:message code="label.proposal.accept"/></button>
                                   </form>
                               </c:otherwise>
                           </c:choose>

                       </div>
                   </div>
                </c:otherwise>
            </c:choose>

            <%--<div>--%>
                <%--<button type="button" class="btn btn-secondary"><spring:message code="label.proposal.cancel"/></button>--%>
            <%--</div>--%>
        </div>
    </div>
</div>


</body>
</html>
