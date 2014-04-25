<%@taglib prefix="s" uri="/struts-tags"%>



<div class="group-edit-main">
<div class="title-panel"><s:text name="group.edit" /></div>
<div class="content-panel"><s:form action="GroupEdit.action">
	<s:textfield name="group.name" key="groups.group.name" />
	<s:hidden name="operation" value="save"></s:hidden>
	<s:checkbox name="rights" fieldValue="user_admin"
		key="group.right.user_admin" />
	<s:checkbox name="rights" fieldValue="group_admin"
		key="group.right.group_admin" />
	<s:checkbox name="rights" fieldValue="process_admin"
		key="group.right.process_admin" />
	<s:submit value="Dodaj" />
</s:form></div>
</div>
