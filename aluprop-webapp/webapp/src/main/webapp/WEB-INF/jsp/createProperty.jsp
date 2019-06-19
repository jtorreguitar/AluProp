<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <title><spring:message code="host.publish_property"/></title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/navbar-fixed/">

    <link rel='icon' type='image/png' href="<c:url value="/resources/images/favicon.png"/>"/>

    <!-- Bootstrap core css -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
    <script src="<c:url value="/resources/js/urlUtility.js"/>" ></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</head>
<body data-gr-c-s-loaded="true">
<%@include file="navigationBar.jsp"%>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <header class="card-header">
                    <h4 class="card-title mt-2"><spring:message code="host.publish_property"/></h4>
                </header>
                <article class="card-body">
                    <c:url value="/host/create" var="postPath"/>
                    <form:form modelAttribute="propertyCreationForm" action="${postPath}" method="post" enctype="multipart/form-data">
                        <label><spring:message code="forms.image_upload_instructions"/></label>
                        <c:choose>
                            <c:when test="${imagesAlreadyUploaded == null}">
                                <spring:message code="placeholder.choose_file" var="chooseFilePlaceholder"/>
                                <div class="form-group">
                                    <spring:message code="forms.upload_picture" arguments="1"/>
                                    <input id="uploadFile1" placeholder="${chooseFilePlaceholder}"/>
                                    <div class="fileUpload btn btn-primary"><span><spring:message code="forms.upload"/></span>
                                        <input id="uploadBtn1" type="file" class="upload" name="file"/>
                                    </div></br>
                                    <spring:message code="forms.upload_picture" arguments="2"/>
                                    <input id="uploadFile2" placeholder="${chooseFilePlaceholder}" />
                                    <div class="fileUpload btn btn-primary"><span><spring:message code="forms.upload"/></span>
                                        <input id="uploadBtn2" type="file" class="upload" name="file"/>
                                    </div></br>
                                    <spring:message code="forms.upload_picture" arguments="3"/>
                                    <input id="uploadFile3" placeholder="${chooseFilePlaceholder}"/>
                                    <div class="fileUpload btn btn-primary"><span><spring:message code="forms.upload"/></span>
                                        <input id="uploadBtn3" type="file" class="upload" name="file"/>
                                    </div></br>
                                    <spring:message code="forms.upload_picture" arguments="4"/>
                                    <input id="uploadFile4" placeholder="${chooseFilePlaceholder}"/>
                                    <div class="fileUpload btn btn-primary"><span><spring:message code="forms.upload"/></span>
                                        <input id="uploadBtn4" type="file" class="upload" name="file"/>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="system.successfully_uploaded"/>
                            </c:otherwise>
                        </c:choose>
                        <form:errors path="imageIds" cssClass="formError" element="p"/>
                        <c:if test="${noImages == true}">
                            <span class="formError"><spring:message code="system.upload_error"/></span>
                        </c:if>

                        <div class="form-group">
                            <form:label path="description"><spring:message code="forms.name"/> </form:label>
                            <form:input path="description" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="description" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="caption"> <spring:message code="forms.description"/> </form:label>
                            <spring:message code="placeholder.include_details" var="includeDetailsPlaceholder"/>
                            <form:textarea path="caption" type="text" class="form-control"  style="resize:both;" placeholder="${includeDetailsPlaceholder}"></form:textarea>
                            <form:errors path="caption" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="propertyType"><spring:message code="forms.property_type"/></form:label>
                            <form:select path="propertyType" id="select-property-type" name="propertyTypes">
                                <form:option value="-1"><spring:message code="forms.choose"/></form:option>
                                <c:forEach var="type" items="${propertyTypes}">
                                    <form:option value="${type.id}"><spring:message code="${type.name}"/></form:option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="propertyType" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="neighbourhoodId"><spring:message code="forms.neighborhood"/></form:label>
                            <form:select path="neighbourhoodId" id="select-neighbourhood" name="neighbourhoods">
                                <form:option value="-1"><spring:message code="forms.choose"/></form:option>
                                <c:forEach var="neighbourhood" items="${neighbourhoods}">
                                    <form:option value="${neighbourhood.id}">${neighbourhood.name}</form:option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="neighbourhoodId" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="privacyLevel"><spring:message code="forms.privacy"/></form:label>
                            <form:select path="privacyLevel" id="select-privacy-level" name="provacyLevels">
                                <form:option value="-1"><spring:message code="forms.choose"/></form:option>
                                <c:forEach var="level" items="${privacyLevels}">
                                    <form:option value="${level.id}"><spring:message code="${level.name}"/></form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <form:label path="capacity"><spring:message code="forms.capacity"/></form:label>
                            <form:input path="capacity" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="capacity" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="price"><spring:message code="forms.rent_per_month"/></form:label>
                            <form:input path="price" type="text" class="form-control" placeholder=""></form:input>
                            <form:errors path="price" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="ruleIds"><spring:message code="forms.rules"/></form:label>
                                <c:forEach var="rule" items="${rules}">
                                    <label class="checkbox"><form:checkbox path="ruleIds" value="${rule.id}"/>${" "}<spring:message code="${rule.name}"/></label>
                                </c:forEach>
                            <form:errors path="ruleIds" cssClass="formError" element="p"/>
                        </div>
                        <div class="form-group">
                            <form:label path="serviceIds"><spring:message code="forms.services"/></form:label>
                                <c:forEach var="service" items="${services}">
                                    <label class="checkbox"><form:checkbox path="serviceIds" value="${service.id}"/>${" "}<spring:message code="${service.name}"/></label>
                                </c:forEach>
                            <form:errors path="serviceIds" cssClass="formError" element="p"/>
                        </div>

                        <div class="gone">
                            <c:if test="${imagesAlreadyUploaded != null}">
                                <c:forEach var="image" items="${imagesAlreadyUploaded}">
                                    <form:checkbox path="imageIds" value="${image}"/>$
                                </c:forEach>
                                <form:errors path="serviceIds" cssClass="formError" element="p"/>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <button id="btn-publish" type="submit" class="btn btn-primary btn-block"><spring:message code="host.publish_property_button"/></button>
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
