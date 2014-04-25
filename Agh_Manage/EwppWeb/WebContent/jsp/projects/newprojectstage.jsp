<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function(){ 
	$(function() {
		$("#datePicker").datepicker();
	});
	
});

</script>

<s:form action="ProjectStageEdit">
	<s:textfield name="stageTitle" label="Tytul" cssStyle="width:300px"/>
	<s:textarea name="stageDesc" label="Opis" rows="20" cols="40" />
	<s:hidden name="projectId"/>
	<s:hidden name="operation" value="save"/>
	<s:submit value="Utwórz" />
</s:form>

