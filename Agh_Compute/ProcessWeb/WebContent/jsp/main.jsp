<%@taglib prefix="s" uri="/struts-tags"%>

<div class="logindata-panel">
	<div class="title-panel">
		<s:text name="main.panel.logon"/>
	</div>
	<div class="content-panel">
		<div style="position: relative; float: left; left:80px;">
			<img src="/procc/gfx/amsn4.png"/>
		</div>
		<div style="position: relative; float: left; left: 80px;">
		<s:text name="common.logged.user"/>
		<b><s:property value="loggedUser"/></b><br>
		<s:text name="last.user.login"/>
		<b><s:date name="loginData.loginDate" format="yyyy-MM-dd HH:mm" /></b><br/>
		<s:text name="last.user.remoteaddr"/>
		<b><s:property value="loginData.loginFrom"/></b>
		</div>
	</div>
</div>

<div class="main-process-panel">
	<div class="title-panel">
	 	<s:text name="common.prcesses"/>
	</div>
	<div class="content-panel">
		<span style="position: relative; FLOAT: left;">
			<img src="/procc/gfx/kded.png"/>
		</span>
		<s:url action="ComputationList" id="complist" />
		<s:url action="PackagesList" id="packlist" />
		<s:url action="ComputationConfigList" id="confList"/>
		<s:a href="%{complist}"><s:text name="process.list"/></s:a><br/>
		<s:a href="%{packlist}"><s:text name="process.packages.list"/></s:a><br/>
		<s:a href="%{confList}"><s:text name="computation.config.list"/></s:a><br/>
	</div>
</div>

<div class="main-admin-panel">
	<div class="title-panel">
	 	<s:text name="main.panel.admin"/>
	</div>
	<div class="content-panel">
		<span style="position: relative; FLOAT: left;">
			<img src="/procc/gfx/settings.png"/>
		</span>
		<s:url action="UsersList" id="userslist" />
		<s:url action="GroupsList" id="groupslist"/>
		<s:url action="ValidationList" id="validationList" />
		<s:url action="TransformList" id="transformList" />
		<s:a href="%{userslist}"><s:text name="common.users"/></s:a><br/>
		<s:a href="%{groupslist}"><s:text name="common.groups"/></s:a><br/>
		<s:a href="%{validationList}"><s:text name="validation.list"/></s:a><br/>
		<s:a href="%{transformList}"><s:text name="transform.list"/></s:a><br/>
		
	</div>
</div>

<div class="computations-panel">
	<div class="title-panel">
	 	<s:text name="computations.label"/>
	</div>
	<div class="content-panel">
		<span style="position: relative; FLOAT: left;">
			<img src="/procc/gfx/kformula.png"/>
		</span>
		<s:url action="PerformedComputationsList" id="performedComputationsList"></s:url>
		<s:a href="%{performedComputationsList}"><s:text name="computations.performed"/></s:a>
		<s:url action="ComputationResultList" id="computationResultList"></s:url><br/>
		<s:a href="%{computationResultList}"><s:text name="computatios.results"/></s:a>
	</div>
</div>


<div class="nodes-panel">
	<div class="title-panel">
	 	<s:text name="nodes.label"/>
	</div>
	<div class="content-panel">
		<span style="position: relative; FLOAT: left;">
			<img src="/procc/gfx/nodes.png"/>
		</span>
		<s:url action="NodesList" id="nodesList"></s:url>
		<s:a href="%{nodesList}"><s:text name="nodes.list"/></s:a><br/>
	</div>
</div>


