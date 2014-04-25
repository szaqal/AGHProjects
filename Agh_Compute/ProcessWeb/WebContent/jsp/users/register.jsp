<%@taglib prefix="s" uri="/struts-tags"%>
register-panel

<div class="register-panel">
	<div class="title-panel">
		<s:text name="registration.register"/>
	</div>
	<div class="content-panel">
		<div style="position: relative; float: left; left:20px;">
			<img src="/procc/gfx/kuser.png">
		</div>
		<div style="position: relative; float: left; left: 36px;">
		<s:form action="RegisterUser.action">
			<s:textfield name="user.email" key="form.email"/>
			<s:password name="user.password" key="form.passwd" />
			<s:textfield name="user.firstName" key="form.user.fname"/>
			<s:textfield name="user.lastName" key="form.user.lname"/>
			<s:hidden name="operation" value="save"></s:hidden>
			<s:submit value="Rejestracja"/>
		</s:form>
		</div>
	</div>
</div>
