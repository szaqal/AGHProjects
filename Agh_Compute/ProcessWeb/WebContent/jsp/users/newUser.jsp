<%@taglib prefix="s" uri="/struts-tags"%>


<div class="edit-user-panel">
	<div class="title-panel">
		<s:text name="users.data"/>
	</div>
	<div class="content-panel">
		<s:form action="UserEdit.action">
			<s:textfield name="user.email" key="form.email"/>
			<s:password name="user.password" key="form.passwd" />
			<s:textfield name="user.firstName" key="form.user.fname"/>
			<s:textfield name="user.lastName" key="form.user.lname"/>
			<s:hidden name="operation" value="save"></s:hidden>
			<s:submit value="Dodaj"/>
		</s:form>
	</div>
</div>



