<%@taglib prefix="s" uri="/struts-tags"%>
<table>
	<tr>
		<td><s:form action="UserEdit.action">
			<s:textfield name="user.login" label="Login" />
			<s:textfield name="user.firstName" label="Pierwsze Imie" />
			<s:textfield name="user.middleName" label="Drugie Imie" />
			<s:textfield name="user.lastName" label="Nazwisko" />
			<s:textfield name="user.email" label="Email" />
			<s:textfield name="user.phoneNr" label="Numer telefonu" />
			<s:hidden name="user.passwd"></s:hidden>
			<s:hidden name="user.uniqueId"></s:hidden>
			<s:hidden name="operation" value="save"></s:hidden>
			<s:radio list="#{1:'Student', 2:'Wykladowca', 3:'Administrator'}" name="selectedType" value="currentType" />
			<s:submit value="Zapisz" />
		</s:form></td>
		<td><img src="gfx/system-users.png" /></td>
	</tr>
</table>