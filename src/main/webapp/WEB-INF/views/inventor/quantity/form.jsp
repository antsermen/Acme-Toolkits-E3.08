<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
		
		<jstl:if test="${acme:anyOf(command, 'show, update, delete')}">
		
			<acme:input-integer code="inventor.quantity.form.label.itemsNumber" path="itemsNumber" readonly="${isTool}"/>
			<acme:input-textbox code="inventor.item.form.label.itemType" path="item.itemType" readonly="true"/>
			<acme:input-textbox code="inventor.item.form.label.name" path="item.name" readonly="true"/>
			<acme:input-textbox code="inventor.quantity.item.form.label.code" path="item.code" readonly="true"/>
			<acme:input-money code="inventor.quantity.item.list.label.retail-price" path="item.retailPrice" readonly="true" />					
		</jstl:if>
				
		<jstl:if test="${toolkitPublished == false}">
			<jstl:if test="${!isTool}">		
				<acme:submit code="inventor.quantity.form.button.update"
					action="/inventor/quantity/update" />
			</jstl:if>
				<acme:submit code="inventor.quantity.form.button.delete"
					action="/inventor/quantity/delete" />
		</jstl:if>
		
		<jstl:choose>
		<jstl:when test="${command == 'create'}">		
			<acme:input-integer code="inventor.quantity.form.label.number" path="itemsNumber"/> 
       		<acme:input-select code="inventor.quantity.form.label.item" path="itemId">
            <jstl:forEach items="${items}" var="item">
                <acme:input-option code="${item.getName()}" value="${item.getId()}" selected="${item.getId() == itemId }"/>
            </jstl:forEach>
        	</acme:input-select>
			<acme:submit code="inventor.quantity.form.button.create" action="/inventor/quantity/create?toolkitId=${toolkitId}"/>			
		</jstl:when>
		</jstl:choose>	
</acme:form>