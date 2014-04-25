<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function(){ 
	$(function() {
		$("#progressbar").progressbar({
			value: $("#projectProgress").val()
		});
	});
	
}); 

</script>
<table width="100%" border="0">
	<tr>
		<td><b>Tutul:</b></td>
		<td><img src="gfx/projects/help-contents.png"/></td>
		<td><s:property value="project.title"/></td>
	</tr>
	<tr>
		<td><b>Opis:</b></td>
		<td><img src="gfx/projects/help-contents.png"/></td>
		<td ><s:property value="project.description"/></td>
	</tr>
	<tr>
		<td colspan="3" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>
	<tr>
		<td><b>Status projektu: </b></td>
		<td>
			<s:if test="project.currentProjectStatus == 'REQUESTED'">
					<img src="gfx/projects/system-hybernate.png" border="0" title="Nowy"/>
			</s:if>
			<s:elseif test="project.currentProjectStatus == 'ACCEPTED'">
					<img src="gfx/projects/system-switch-user.png" border="0" title="W toku"/>
			</s:elseif>
			<s:elseif test="project.currentProjectStatus == 'FINISHED'">
					<img src="gfx/projects/stop.png" border="0" title="Zakonczony"/>
			</s:elseif>
		</td>
		<td>
			<s:if test="project.currentProjectStatus == 'REQUESTED'">
					Nowy
			</s:if>
		</td>
	</tr>

	<tr>
		<td colspan="3" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>

	<tr>
		<td><b>Uczestnicy projektu:</b></td>
		<td><img src="gfx/im-msn.png"/></td>
		<td>
			<table>
				<s:iterator id="iter" value="members" var="member">
					<tr><td><s:property value="label"/></td></tr>
				</s:iterator>
			</table>
		</td>
	</tr>

	<tr>
		<td colspan="3" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>
	<tr>
		<td><b>Etapy projektu:</b></td>
		<td><img src="gfx/projects/run.png"/></td>
	</tr>
	<tr>
		<td colspan="2">
		<table style="font-size: small">
			<s:iterator id="stageIter" value="projectStages" var="prStage">
			<tr>

				<td>
					<s:url action="ManageStage" id="viewStage">
						<s:param name="operation">edit</s:param>
						<s:param name="projectStageId">
							<s:property value="uniqueId" />
						</s:param>
						<s:param name="projectId">
							<s:property value="projectId" />
						</s:param>
					</s:url>
					<s:a href="%{viewStage}"><img src="gfx/files/document-preview.png" border="0" /></s:a>
				</td>
				<td><s:property value="title" /></td>

			</tr>
			</s:iterator>
		</table>
		</td>
	</tr>
	
	<tr>
		<td colspan="3" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>
		<td><b>Postep projektu:</b></td>
		<td colspan="2">
			<s:hidden name="projectProgress" id="projectProgress"/>
			<div id="progressbar" style="width:100%;height:20px"></div>
		</td>
	<tr>

	<tr>
		<td colspan="3" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>

	<tr>
		<s:url action="ProjectItems" id="viewLink">
			<s:param name="projectId"><s:property value="projectId"/></s:param>
		</s:url>
		<td><b>Elementy projektu:</b></td>
		<td><s:a href="%{viewLink}"><img src="gfx/filesave.png" border="0"/></s:a></td>
	</tr>
	
	

	<tr>
		<td colspan="3" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>

	<tr>
	<s:if test="warnings.size > 0">
		<td colspan="3" bgcolor="#ffd6d6">

			<table style="font-size:small">
				<tr>
					<td><img src="gfx/projects/stock_dialog_warning.png" border="0"/></td>
					<td>
						<ol>
						<s:iterator id="iter" value="warnings" var="warning">
							<li><s:property value="warning"/></li>
						</s:iterator>
						</ol>
					</td>
				</tr>
			</table>
		</td>
		</s:if>
	</tr>

	<tr>
		<td colspan="3" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>

	<tr>
		<s:url action="ProjectStageEdit" id="newProjectStage">
			<s:param name="operation">new</s:param>
			<s:param name="projectId" value="projectId"/>
		</s:url>

		<s:url action="ManageProject" id="acceptProject">
			<s:param name="operation">acceptProject</s:param>
			<s:param name="projectId" value="projectId"/>
		</s:url>
		
		<s:url action="ManageProject" id="closeProject">
			<s:param name="operation">closeProject</s:param>
			<s:param name="projectId" value="projectId"/>
		</s:url>
		
		<td colspan="3" align="center">
			<s:if test="project.currentProjectStatus == 'ACCEPTED' && #session.sessionData.lecturer">
				<s:a href="%{newProjectStage}">Nowy etap</s:a>
			</s:if>
			
			<s:if test="project.currentProjectStatus == 'REQUESTED' && #session.sessionData.lecturer">
				<s:a href="%{acceptProject}">Akceptuj projekt</s:a>
			</s:if>
			<s:if test="project.currentProjectStatus == 'ACCEPTED' && #session.sessionData.lecturer">
				<s:a href="%{closeProject}">Zakoncz projekt</s:a>
			</s:if>
		</td>

	</tr>


</table>