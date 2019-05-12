<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title>Create a Property</title>

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
                    <c:url value="/host/create/uploadPictures" var="postPath"/>
                    <c:url value="${postPath}" var="postPath"/>
                    <form:form modelAttribute="propertyCreationForm" action="${postPath}" method="post" enctype="multipart/form-data">
                        <label>Upload up to 4 pictures of the property:</label>
                        <c:choose>
                            <c:when test="${imagesUploaded == null}">
                                <div class="form-group">
                                    Picture 1 to upload:
                                    <input id="uploadFile1" placeholder="Choose File" disabled="disabled" />
                                    <div class="fileUpload btn btn-primary"><span>Upload</span>
                                        <input id="uploadBtn1" type="file" class="upload" name="file"/>
                                    </div>
                                    Picture 2 to upload:
                                    <input id="uploadFile2" placeholder="Choose File" disabled="disabled" />
                                    <div class="fileUpload btn btn-primary"><span>Upload</span>
                                        <input id="uploadBtn2" type="file" class="upload" name="file"/>
                                    </div>
                                    Picture 3 to upload:
                                    <input id="uploadFile3" placeholder="Choose File" disabled="disabled" />
                                    <div class="fileUpload btn btn-primary"><span>Upload</span>
                                        <input id="uploadBtn3" type="file" class="upload" name="file"/>
                                    </div>
                                    Picture 4 to upload:
                                    <input id="uploadFile4" placeholder="Choose File" disabled="disabled" />
                                    <div class="fileUpload btn btn-primary"><span>Upload</span>
                                        <input id="uploadBtn4" type="file" class="upload" name="file"/>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                ${imagesUploaded} pictures were successfully uploaded!
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="imageIds" cssClass="formError" element="p"/>
                        <c:if test="${noImages == true}">
                            <span class="formError">Please upload at least 1 picture of the property.</span>
                        </c:if>

                        <div class="form-group">
                            <form:label path="description">Name</form:label>
                            <form:input path="description" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="description" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="caption">Description</form:label>
                            <form:textarea path="caption" type="text" class="form-control"  style="resize:both;" placeholder="Include more details of the property..."></form:textarea>
                            <form:errors path="caption" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="propertyType">Property type:</form:label>
                            <form:select path="propertyType">
                                <option value="-1">Please choose</option>
                                <option value="0">House</option>
                                <option value="1">Apartment</option>
                                <option value="2">Loft</option>
                            </form:select>
                            <form:errors path="propertyType" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="neighbourhoodId">Neighborhood:</form:label>
                            <form:select path="neighbourhoodId" id="select-neighbourhood" name="neighbourhoods">
                                <form:option value="-1">Please choose</form:option>
                                <c:forEach var="neighbourhood" items="${neighbourhoods}">
                                    <form:option value="${neighbourhood.id}">${neighbourhood.name}</form:option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="neighbourhoodId" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="privacyLevel">Privacy:</form:label>
                            <form:select path="privacyLevel">
                                <option value="-1">Please choose</option>
                                <option value="0">Individual</option>
                                <option value="1">Shared</option>
                            </form:select>
                            <form:errors path="privacyLevel" cssClass="formError" element="p"/>
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
<script>
    document.getElementById("uploadBtn1").onchange = function () {
        document.getElementById("uploadFile1").value = this.value.split("\\fakepath\\")[1];
    };
    document.getElementById("uploadBtn2").onchange = function () {
        document.getElementById("uploadFile2").value = this.value.split("\\fakepath\\")[1];
    };
    document.getElementById("uploadBtn3").onchange = function () {
        document.getElementById("uploadFile3").value = this.value.split("\\fakepath\\")[1];
    };
    document.getElementById("uploadBtn4").onchange = function () {
        document.getElementById("uploadFile4").value = this.value.split("\\fakepath\\")[1];
    };
</script>

</body>
</html>
