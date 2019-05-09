<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>Sign Up</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />

</head>
<body data-gr-c-s-loaded="true">
<%@include file="navigationBar.jsp"%>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <header class="card-header">
                    <h4 class="card-title mt-2">Create a new property</h4>
                </header>
                <article class="card-body">
                    <c:choose>
                        <c:when test="${filesUploaded == null}">
                            <div class="form-group">
                                <label>Upload up to 4 pictures of the property:</label>
                                <form id="images" method="POST" action="/host/create/uploadPictures" enctype="multipart/form-data">
                                    Picture 1 to upload: <input type="file" class="btn btn-primary btn-block" name="file"><br />
                                    Picture 2 to upload: <input type="file" class="btn btn-block" name="file"><br />
                                    Picture 3 to upload: <input type="file" class="btn btn-block" name="file"><br />
                                    Picture 4 to upload: <input type="file" class="btn btn-block" name="file"><br />
                                    <button type="submit" value="Upload"> Upload pictures!</button>
                                </form>
                            </div>
                        </c:when>
                        <c:otherwise>
                            ${filesUploaded} pictures uploaded!
                        </c:otherwise>
                    </c:choose>
                    <c:url value="/host/create" var="postPath"/>
                    <form:form modelAttribute="propertyCreationForm" action="${postPath}" method="post">
                        <div class="form-group">
                            <form:label path="caption">Caption</form:label>
                            <form:input path="caption" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="caption" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="description">Description</form:label>
                            <form:textarea path="description" type="text" class="form-control"  style="resize:both;" placeholder="Include more details of the property..."></form:textarea>
                            <form:errors path="description" cssClass="formError" element="p"/>
                        </div>
                        <div class="col form-group">
                            <form:label path="propertyType" class="form-check form-check-inline">Property type:</form:label>
                            <form:select path="propertyType">
                                <form:option value="0">House</form:option>
                                <form:option value="1">Apartment</form:option>
                                <form:option value="2">Loft</form:option>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <form:label path="neighbourhoodId">Neighborhood:</form:label>
                            <form:select path="neighbourhoodId" id="select-neighbourhood" name="neighbourhoods">
                                <option value="" selected>Please choose</option>
                                <c:forEach var="neighbourhood" items="${neighbourhoods}">
                                    <form:option value="${neighbourhood.id}">${neighbourhood.name}</form:option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="neighbourhoodId" cssClass="formError" element="p"/>
                        </div>
                        <div class="col form-group">
                            <form:label path="privacyLevel" class="form-check form-check-inline">Privacy:</form:label>
                            <form:select path="privacyLevel">
                                <form:option value="false">Individual</form:option>
                                <form:option value="true">Shared</form:option>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <form:label path="capacity">Capacity:</form:label>
                            <form:input path="capacity" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="capacity" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="price">Rent per month:</form:label>
                            <form:input path="price" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="price" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="ruleIds">Rules:</form:label>
                                <c:forEach var="rule" items="${rules}">
                                    <form:checkbox path="ruleIds" value="${rule.id}"/>${rule.name}
                                </c:forEach>
                            <form:errors path="ruleIds" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="serviceIds">Services:</form:label>
                            <c:forEach var="service" items="${services}">
                                <form:checkbox path="serviceIds" value="${service.id}"/>${service.name}
                            </c:forEach>
                            <form:errors path="serviceIds" cssClass="formError" element="p"/>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block">Create Property</button>
                        </div> <!-- form-group// -->
                    </form:form>
                </article> <!-- card-body end .// -->
            </div> <!-- card.// -->
        </div> <!-- col.//-->

    </div> <!-- row.//-->


</div>
<!--container end.//-->

</body>
</html>
