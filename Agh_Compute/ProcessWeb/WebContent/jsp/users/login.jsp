<%@taglib prefix="s" uri="/struts-tags"%>

<div class="login-panel">
	<div class="title-panel">
		<s:text name="common.login"/>
	</div>
	<div class="content-panel">
		<div style="position: relative; float: left; left:80px;">
			<img src="/procc/gfx/lock.png">
		</div>
		<div style="position: relative; float: left; left: 80px;">
			<s:form action="LoginUser.action">
					<s:textfield name="email" key="form.email" id="txtEmail"/>
					<s:password name="passwd" key="form.passwd" />
					<s:hidden name="operation" value="form.login.perform"/>
					<s:submit key="form.login.perform"/>
			</s:form>
	
			<s:url action="RegisterUser" id="register" />
	
			<s:a href="%{register}">Rejestracja</s:a>
		</div>
	</div>
</div>
