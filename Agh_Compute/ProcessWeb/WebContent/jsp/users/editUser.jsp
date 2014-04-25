<%@taglib prefix="s" uri="/struts-tags"%>
<s:form action="UserEdit.action" theme="simple">
<div class="list-panel">
	<div class="title-panel">
		<s:text name="users.data"/>
	</div>
	<div class="content-panel">
		<table>
			<tr>
				<td><s:text name="form.email"/></td>
				<td><s:textfield name="user.email" key="form.email"/></td>
			</tr>
			<tr>
				<td><s:text name="form.passwd"/></td>
				<td><s:password name="user.password" key="form.passwd" /></td>
			</tr>
			<tr>
				<td><s:text name="form.user.fname"/></td>
				<td><s:textfield name="user.firstName" /></td>
			</tr>
			<tr>
				<td><s:text name="form.user.lname"/></td>
				<td><s:textfield name="user.lastName" key="form.user.lname"/></td>
			</tr>
			<tr>
				<td>
					<s:hidden name="user.uniqueId"></s:hidden>
				</td>
				<td><s:submit name="operation" value="Zapisz"/></td>
			</tr>
		</table>
	</div>
</div>

<div class="details-panel">
	<div class="title-panel">
		<s:text name="users.groups"/>
	</div>
	<div class="content-panel">
		<table>
			<s:iterator value="usersGroup" var="userGrp">
				<tr>
					<td><s:property value="name" /></td>
					
					<s:url action="UserEdit" id="deleteGroup">
						<s:param name="group" value="%{uniqueId}"/>
						<s:param name="id" value="user.uniqueId"/>
						<s:param name="operation">deleteGroup</s:param>
					</s:url>
					<td><s:a href="%{deleteGroup}">Usun</s:a></td>
				</tr>
			</s:iterator>
			<tr>
				<td><s:select label="grupa" name="group" listValue="name" 
					listKey="uniqueId" list="allGroups" theme="simple"/></td>
				<td><s:submit name="operation" value="Dodaj do grupy" /></td>	
			</tr>
		</table>
	</div>
</div>
</s:form>