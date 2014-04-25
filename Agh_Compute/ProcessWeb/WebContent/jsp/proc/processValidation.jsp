<%@taglib prefix="s" uri="/struts-tags"%>
<div class="validate-process-panel">
	<div class="title-panel">
		<s:text name="process.validation"/>
	</div>
	<div class="content-panel">
		<div style="position: relative; float: left; left:80px;">
			<img src="/procc/gfx/spellcheck.png"/>
		</div>
		<div style="position: relative; float: left; left: 80px;">
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
