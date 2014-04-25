<%@taglib prefix="s" uri="/struts-tags"%>
<div class="failed-validation-panel">

	<div class="title-panel">
		<s:text name="process.validationfailed"/>
	</div>

	<div class="content-panel">
		<div style="position:inherit; top:39%; left:12%">
			<img src="/procc/gfx/apps.png"/>
		</div>
		<div style="position: inherit; top: 40%; left: 28%;">
			<s:text name="process.validationfailed"></s:text>
			<s:form action="ValidateProcess">
				<s:select name="schemaId" listValue="title" listKey="uniqueId" list="schemas" />
				<s:hidden name="operation" value="validate"/>
				<s:hidden name="computationPackageId"></s:hidden>
				<s:hidden name="computationConfigId"></s:hidden>
				<s:submit value="Waliduj"/>
			</s:form>
		</div>
	</div>
</div>