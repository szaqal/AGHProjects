<%@taglib prefix="s" uri="/struts-tags" %>
<!-- Student -->
<a href='<s:url value="LoginUser.action"/>'>Logowanie</a><br/>
<s:if test="%{#session.sessionData.lecturer || #session.sessionData.student }">
	<a href='<s:url value="MyProjects.action"/>'>Moje projekty</a>
	<a href='<s:url value="Messages.action"/>'>Wiadomosci</a>
	<a href='<s:url value="PublicDocuments.action"/>'>Dokumenty publiczne</a>
</s:if>

<hr/>
<!--Wykladowca -->
<s:if test="%{#session.sessionData.admin}">
	<a href='<s:url value="UsersList.action"/>'>Uzytkownicy</a><br/>
	<a href='<s:url value="Configuration.action"/>'>Konfiguracja</a><br/>
</s:if>

<s:if test="#session.sessionData.userLabel != ''">
	<a href='<s:url value="Logout.action"/>'>Wyloguj</a><br/>
</s:if>
