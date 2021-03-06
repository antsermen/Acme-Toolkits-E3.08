<%--
- menu.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Provider,acme.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>

			
			<acme:menu-suboption code="05980389k: Carnero Vergel, Manuel" action="https://www.google.es/"/>
			<acme:menu-suboption code="29617049H: Santos Perez, Pablo" action="https://tetris.com/play-tetris"/>
			<acme:menu-suboption code="53916052G: Serrano Mena, Antonio Roberto" action="http://listen.hatnote.com/"/>
			<acme:menu-suboption code="80232022H: Arias Exposito, Jose Ramon" action="https://buscaminas.eu/"/>
			<acme:menu-suboption code="44068800H: Moreno Perez, Juan Carlos" action="http://www.netflix.com/"/>
			<acme:menu-suboption code="75917728H: Sabugueiro Troya, David" action="http://www.twitter.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.chirp.list" action="/any/chirp/list"/>
			<acme:menu-suboption code="master.menu.anonymous.toolkit.list" action="/any/toolkit/list"/>
			<acme:menu-suboption code="master.menu.anonymous.item.tool.list" action="/any/item/tools"/>
			<acme:menu-suboption code="master.menu.anonymous.item.component.list" action="/any/item/components"/>
			<acme:menu-suboption code="master.menu.anonymous.userAccount.list" action="/any/user-account/list"/>
		
      
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated">
			<acme:menu-suboption code="master.menu.authenticated.chirp.list" action="/any/chirp/list"/>
			<acme:menu-suboption code="master.menu.authenticated.toolkit.list" action="/any/toolkit/list"/>
			<acme:menu-suboption code="master.menu.authenticated.item.tool.list" action="/any/item/tools"/>
			<acme:menu-suboption code="master.menu.authenticated.item.component.list" action="/any/item/components"/>
			<acme:menu-suboption code="master.menu.authenticated.userAccount.list" action="/any/user-account/list"/>
			<acme:menu-suboption code="master.menu.authenticated.announcement.list" action="/authenticated/announcement/list-recent"/>
			<acme:menu-suboption code="master.menu.administrator.system-configuration" action="/authenticated/system-configuration/show"/>
			
		</acme:menu-option>

		<acme:menu-option code="master.menu.inventor" access="hasRole('Inventor')">
      <acme:menu-suboption code="master.menu.inventor.mine-tools.list" action="/inventor/item/my-tools"/>	
			<acme:menu-suboption code="master.menu.inventor.mine-components.list" action="/inventor/item/my-components"/>	
			<acme:menu-suboption code="master.menu.inventor.mine-patronages.list" action="/inventor/patronage/list"/>
			<acme:menu-suboption code="master.menu.inventor.mine-toolkits.list"	action="/inventor/toolkit/list"/>
			<acme:menu-suboption code="master.menu.inventor.patronageReport.list" action="/inventor/patronage-report/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.administrator.dashboard" action="/administrator/administrator-dashboard/show"/>
			<acme:menu-suboption code="master.menu.administrator.system-configuration" action="/administrator/system-configuration/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.announcement.create" action="/administrator/announcement/create"/>	
			<acme:menu-separator/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.patron" access="hasRole('Patron')">
			<acme:menu-suboption code="master.menu.patron.patronage.list" action="/patron/patronage/list"/>
			<acme:menu-suboption code="master.menu.patron.patron.dashboard" action="/patron/patron-dashboard/show"/>
			<acme:menu-suboption code="master.menu.patron.patronage-report" action="/patron/patronage-report/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
			
		</acme:menu-option>

	</acme:menu-left>
	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-inventor" action="/authenticated/inventor/create" access="!hasRole('Inventor')"/>
			<acme:menu-suboption code="master.menu.user-account.inventor" action="/authenticated/inventor/update" access="hasRole('Inventor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-patron" action="/authenticated/patron/create" access="!hasRole('Patron')"/>
			<acme:menu-suboption code="master.menu.user-account.patron" action="/authenticated/patron/update" access="hasRole('Patron')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

