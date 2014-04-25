<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#registerFormTable").show("fast");
	});
</script>
<table id="registerFormTable" style="display:none">
	<tr>
		<td><s:form action="RegisterUser.action">
			<s:textfield name="user.login" label="Login" />
			<s:password name="user.passwd" label="Haslo" />
			<s:textfield name="user.firstName" label="Pierwsze Imie" />
			<s:textfield name="user.middleName" label="Drugie Imie" />
			<s:textfield name="user.lastName" label="Nazwisko" />
			<s:textfield name="user.email" label="Email" />
			<s:textfield name="user.phoneNr" label="Numer telefonu" />
			<s:hidden name="operation" value="save"></s:hidden>
			<s:submit value="Rejestruj" />
		</s:form></td>
		<td><img src="gfx/system-users.png" /></td>
	</tr>
</table>