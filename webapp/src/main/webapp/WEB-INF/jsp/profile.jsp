<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@	taglib	prefix="spring"	uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <title><spring:message code="label.profile"/></title>

        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">
        <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>


        <!-- Bootstrap core css -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css" />
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    </head>
    <body data-gr-c-s-loaded="true">

        <%@include file="navigationBar.jsp"%>

        <h1 class="display-4">${profileUser.name} ${profileUser.lastName}</h1>
        <c:if test="${profileUser.role == 'ROLE_GUEST'}">
            <div>
                <h5 class="text-muted"><img src="<c:url value="/resources/images/studies.svg"/>" class="studies" alt="English">${profileUser.university.name}</h5>
            </div>
        </c:if>
        <h6 class="text-muted"><img src="<c:url value="/resources/images/birthday-date.svg"/>" class="birthday-date" alt="English"> ${profileUser.birthDate.year} - ${profileUser.birthDate.month+1} - ${profileUser.birthDate.date}</h6>
        <div>
        <br>
        </div>
        <div class="profile-data">
            <div class="card">
                <div class="card-header"><spring:message code="label.profile.bio"/></div>
                <div class="card-body">${profileUser.bio}</div>
            </div>
            <br>
            <div class="row">
                <c:if test="${profileUser.email == currentUser.email && profileUser.role == 'ROLE_GUEST'}">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                <spring:message code="label.profile.interests"/>
                            </div>
                            <div class="list-group">
                                <c:choose>
                                    <c:when test="${not empty interests.pageList}">
                                        <c:forEach var="interest" items="${interests.pageList}">
                                            <a href="<c:url value="/${interest.id}"/>" class="list-group-item list-group-item-action">${interest.description}</a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="card-body">
                                            <spring:message code="label.profile.noInterests" />
                                            <a href="<c:url value="/"/>"><spring:message code="label.profile.findAProperty"/></a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${not empty interests}">
                                <div class="card-footer" style="display:flex;justify-content: center" style="display: flex;justify-content: center">
                                    <c:choose>
                                        <c:when test="${not empty interests.pageList}">
                                            <nav aria-label="Page navigation">
                                                <ul class="pagination" style="margin-bottom: 0">
                                                    <c:choose>
                                                        <c:when test="${interests.firstPage}">
                                                            <li class="page-item disabled">
                                                                <a class="page-link" aria-disabled="true"> < </a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="page-item">
                                                                <a class="page-link" href="?interestPage=${interests.page-1}&${requestScope['javax.servlet.forward.query_string']}"><</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:forEach begin="1" end="${interests.pageCount}" step="1"  varStatus="i">
                                                        <c:choose>
                                                            <c:when test="${(interests.page + 1) == i.index}">
                                                                <li class="page-item disabled">
                                                                    <a class="page-link" aria-disabled="true">${i.index}</a>
                                                                </li>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li class="page-item">
                                                                    <a class="page-link" href="?interestPage=${i.index - 1}&${requestScope['javax.servlet.forward.query_string']}">${i.index}</a>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                    <c:choose>
                                                        <c:when test="${interests.lastPage}">
                                                            <li class="page-item disabled">
                                                                <a class="page-link"  aria-disabled="true"> > </a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="page-item">
                                                                <a class="page-link" href="?interestPage=${interests.page+1}&${requestScope['javax.servlet.forward.query_string']}"> > </a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ul>
                                            </nav>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                <spring:message code="label.profile.proposals"/>
                            </div>
                            <div class="list-group">
                                <c:choose>
                                    <c:when test="${not empty proposals.pageList}">
                                        <c:forEach var="proposal" items="${proposals.pageList}" varStatus="i">
                                            <a href="<c:url value="/proposal/${proposal.id}"/>" class="list-group-item list-group-item-action">
                                                <div style="display: flex;justify-content: space-between">${proposal.property.description}
                                                    <c:if test="${proposal.creator.id == currentUser.id}"><span><img class="my-span" src="<c:url value="/resources/images/star.png"/>"/></span></c:if>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="card-body"><spring:message code="label.profile.no_proposals" /></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${not empty proposals}">
                                <div class="card-footer" style="display:flex;justify-content: center">
                                    <c:choose>
                                        <c:when test="${not empty proposals.pageList}">
                                            <nav aria-label="Page navigation">
                                                <ul class="pagination" style="margin-bottom: 0">
                                                    <c:choose>
                                                        <c:when test="${proposals.firstPage}">
                                                            <li class="page-item disabled">
                                                                <a class="page-link" aria-disabled="true"> < </a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="page-item">
                                                                <a class="page-link" href="?proposalPage=${proposals.page-1}&${requestScope['javax.servlet.forward.query_string']}"><</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:forEach begin="1" end="${proposals.pageCount}" step="1"  varStatus="i">
                                                        <c:choose>
                                                            <c:when test="${(proposals.page + 1) == i.index}">
                                                                <li class="page-item disabled">
                                                                    <a class="page-link" aria-disabled="true">${i.index}</a>
                                                                </li>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li class="page-item">
                                                                    <a class="page-link" href="?proposalPage=${i.index - 1}&${requestScope['javax.servlet.forward.query_string']}">${i.index}</a>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                    <c:choose>
                                                        <c:when test="${proposals.lastPage}">
                                                            <li class="page-item disabled">
                                                                <a class="page-link"  aria-disabled="true"> > </a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="page-item">
                                                                <a class="page-link" href="?proposalPage=${proposals.page+1}&${requestScope['javax.servlet.forward.query_string']}"> > </a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ul>
                                            </nav>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </c:if>

                <c:if test="${profileUser.email == currentUser.email && profileUser.role == 'ROLE_HOST'}">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                <spring:message code="label.profile.properties"/>
                            </div>
                            <div class="list-group">
                                <c:choose>
                                    <c:when test="${not empty properties.pageList}">
                                            <c:forEach var="property" items="${properties.pageList}">
                                                <a href="<c:url value="/${property.id}"/>" class="list-group-item list-group-item-action" style="display: flex;justify-content: space-between">${property.description}
                                                    <c:choose>
                                                        <c:when test="${property.availability == 'AVAILABLE'}">
                                                            <span class="badge badge-success my-badge"> <spring:message code="property.active"/> </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-secondary my-badge"> <spring:message code="property.inactive"/> </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a>
                                            </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="card-body"><spring:message code="label.profile.no_properties" /></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${not empty properties}">
                                <div class="card-footer" style="display:flex;justify-content: center">
                                    <c:choose>
                                        <c:when test="${not empty properties.pageList}">
                                            <nav aria-label="Page navigation">
                                                <ul class="pagination" style="margin-bottom: 0" style="margin-bottom:0;">
                                                    <c:choose>
                                                        <c:when test="${properties.firstPage}">
                                                            <li class="page-item disabled">
                                                                <a class="page-link" aria-disabled="true"> < </a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="page-item">
                                                                <a class="page-link" href="?propertyPage=${properties.page-1}&${requestScope['javax.servlet.forward.query_string']}"><</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:forEach begin="1" end="${properties.pageCount}" step="1"  varStatus="i">
                                                        <c:choose>
                                                            <c:when test="${(properties.page + 1) == i.index}">
                                                                <li class="page-item disabled">
                                                                    <a class="page-link" aria-disabled="true">${i.index}</a>
                                                                </li>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li class="page-item">
                                                                    <a class="page-link" href="?propertyPage=${i.index - 1}&${requestScope['javax.servlet.forward.query_string']}">${i.index}</a>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                    <c:choose>
                                                        <c:when test="${properties.lastPage}">
                                                            <li class="page-item disabled">
                                                                <a class="page-link"  aria-disabled="true"> > </a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="page-item">
                                                                <a class="page-link" href="?propertyPage=${properties.page+1}&${requestScope['javax.servlet.forward.query_string']}"> > </a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ul>
                                            </nav>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </c:if>
                        </div>

                    </div>

                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                <spring:message code="label.profile.proposals"/>
                            </div>
                            <div class="list-group">
                                <c:choose>
                                    <c:when test="${not empty hostProposals.pageList}">
                                        <c:forEach var="proposal" items="${hostProposals.pageList}" varStatus="i">
                                            <a href="<c:url value="/proposal/${proposal.id}"/>" class="list-group-item list-group-item-action">
                                                <div style="display: flex;justify-content: space-between">${proposal.property.description} - <spring:message code="proposal_profile_created_by"/> ${proposal.creator.name}
                                                    <c:if test="${proposal.users.size() > 0}"><spring:message code="proposal_profile_including"/>
                                                        <c:forEach var="user" items="${proposal.users}" varStatus="i">
                                                            ${user.name}
                                                        </c:forEach>
                                                    </c:if>
                                                    <span>
                                                        <c:choose>
                                                            <c:when test="${proposal.state == 'SENT' }">
                                                                <img src="<c:url value="/resources/images/clock.png"/>" class="my-span" alt="${language_en}">
                                                            </c:when>
                                                            <c:when test="${proposal.state == 'ACCEPTED' }">
                                                                <img src="<c:url value="/resources/images/check.png"/>" class="my-span" alt="${language_en}">
                                                            </c:when>
                                                            <c:when test="${proposal.state == 'DECLINED' }">
                                                                <img src="<c:url value="/resources/images/cross.png"/>" class="my-span" alt="${language_en}">
                                                            </c:when>
                                                            <c:when test="${proposal.state == 'DROPPED' }">
                                                                <img src="<c:url value="/resources/images/disabled.png"/>" class="my-span" alt="${language_en}">
                                                            </c:when>
                                                        </c:choose>
                                                    </span>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="card-body"><spring:message code="label.profile.no_proposals_host" /></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${not empty hostProposals}">
                                <div class="card-footer" style="display:flex;justify-content: center">
                                    <c:choose>
                                        <c:when test="${not empty hostProposals.pageList}">
                                            <nav aria-label="Page navigation">
                                                <ul class="pagination" style="margin-bottom: 0">
                                                    <c:choose>
                                                        <c:when test="${hostProposals.firstPage}">
                                                            <li class="page-item disabled">
                                                                <a class="page-link" aria-disabled="true"> < </a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="page-item">
                                                                <a class="page-link" href="?hostProposalPage=${hostProposals.page-1}&${requestScope['javax.servlet.forward.query_string']}"><</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:forEach begin="1" end="${hostProposals.pageCount}" step="1"  varStatus="i">
                                                        <c:choose>
                                                            <c:when test="${(hostProposals.page + 1) == i.index}">
                                                                <li class="page-item disabled">
                                                                    <a class="page-link" aria-disabled="true">${i.index}</a>
                                                                </li>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <li class="page-item">
                                                                    <a class="page-link" href="?hostProposalPage=${i.index - 1}&${requestScope['javax.servlet.forward.query_string']}">${i.index}</a>
                                                                </li>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                    <c:choose>
                                                        <c:when test="${hostProposals.lastPage}">
                                                            <li class="page-item disabled">
                                                                <a class="page-link"  aria-disabled="true"> > </a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li class="page-item">
                                                                <a class="page-link" href="?hostProposalPage=${hostProposals.page+1}&${requestScope['javax.servlet.forward.query_string']}"> > </a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ul>
                                            </nav>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </c:if>
                        </div>
                    </div>

                </c:if>
            </div>
        </div>

    </body>
</html>
