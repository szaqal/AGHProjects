<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="img" uri="/struts-images"%>

<s:url action="ManageStage" id="acceptProjectStage">
	<s:param name="operation">acceptProjectStage</s:param>
	<s:param name="projectStageId" value="projectStageId" />
	<s:param name="projectId" value="projectId" />
</s:url>
<s:hidden name="projectStageId"/>


<table width="100%">
	
	<tr>
		<td><b>Tytul:</b></td>
		<td style="font-size:small"><s:property value="projectStage.title"/></td>
	</tr>
	
	<tr>
		<td><b>Opis:</b></td>
		<td style="font-size:small"><s:property value="projectStage.description"/></td>
	</tr>
	
	<tr>
		<td colspan="2" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>

	<s:iterator id="stageIter" value="stageIterations" var="iter">
		<tr>
			<td colspan="2" align="center">
			
				<s:if test="projectItem.uniqueId!=null">
					<s:url id="downloadLink" action="Download">
						<s:param name="fileId"><s:property value="projectItem.uniqueId"/></s:param>
					</s:url>
					<img:image src="gfx/mimetypes/%{projectItem.contentTypeForImage}.png"/>&nbsp;
					<s:a href="%{downloadLink}"><s:property value="projectItem.fileName"/></s:a>&nbsp;
					<s:date name="projectItem.projectItem.createDate" format="yyyy-MM-dd"/>&nbsp;
				</s:if>
				<s:else>
					<s:if test="#session.sessionData.student">
						<s:form action="ManageStage" method="POST" enctype="multipart/form-data">
						<s:file name="file" label="Plik" />
						<s:hidden name="operation" value="upload" />
						<s:hidden name="stageIterationId" value="%{uniqueId}"></s:hidden>
						<s:hidden name="projectStageId" />
						<s:hidden name="projectId" />
						<s:submit value="Wczytaj" />
						</s:form>
					</s:if>
			</s:else>
			
			</td>
		</tr>
		
		
		
		<tr>
			<td colspan="2"  align="center">
				<s:url action="Correction" id="correct">
					<s:param name="projectStageId" value="projectStageId" />
					<s:param name="projectId" value="projectId" />
					<s:param name="projectStageIterationId" value="#iter.uniqueId"/>
				</s:url>
				
				<s:if test="comment!=null && comment!=''">
					<table style="width:100%;background-color:#ffc9c9;">
						<tr>
							<td width="10%" align="center" valign="middle">
								<img src="gfx/gtk-info.png"/>
							</td>
							<td align="center"><s:property value="comment"/></td>
						</tr>
					</table>
				</s:if>
				
				
				<s:if test="projectItem.uniqueId!=null && comment==null 
							&& projectStage.currentStageStatus == 'OPENED' && #session.sessionData.lecturer">
					<s:a href="%{correct}">Do poprawy</s:a>
				</s:if>
				
			</td>
		</tr>
		
	</s:iterator>
	
	
	<tr>
		<td colspan="2" style="border-bottom-style:solid; border-bottom-color:black; border-bottom-width:1px"></td>
	</tr>
	
	<tr>
		<td align="center" colspan="2" >
			<s:if test="projectStage.currentStageStatus == 'OPENED' && #session.sessionData.lecturer">
				<s:a href="%{acceptProjectStage}">Zakoncz Etap</s:a>
			</s:if>	
		</td>
	</tr>

</table>