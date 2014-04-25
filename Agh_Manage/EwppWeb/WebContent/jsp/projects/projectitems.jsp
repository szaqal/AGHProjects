<%@taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function(){ 
	  jQuery("#noteslist").jqGrid({
	    url:'AjaxProjectsNotesList.action?projectId='+$("#projectId").val(),
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Akcja','Tytul'],
	    colModel :[ 	
		  {name:'act',index:'act', width:40,sortable:false},     
	      {name:'title', index:'title', width:120}],
	    pager: jQuery('#notespager'),
	    rowNum:10,
	    rowList:[10,20,30,50],
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    imgpath: 'css/basic/images',
	    caption: 'Notatki',
	    width: 450,
	    height: 200,
	    loadComplete: function(){ 
		    var ids = jQuery("#noteslist").getDataIDs(); 
		    for(var i=0;i<ids.length;i++){ 
			    var cl = ids[i];
			    be = "<a href=\"NoteView.action?operation=view&noteId="+ids[i]+"\"><img src=\"gfx/find.png\" border=\"0\"/></a>";
			    jQuery("#noteslist").setRowData(ids[i],{act:be}) 
			} 
		}
	    })
	}); 


	jQuery(document).ready(function(){ 
	  jQuery("#fileslist").jqGrid({
	    url:'AjaxProjectsFilesList.action?projectId='+$("#projectId").val(),
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Akcja','Tytul','Mime'],
	    colModel :[ 	
		  {name:'act',index:'act', width:40,sortable:false},     
	      {name:'title', index:'title', width:120},
	      {name:'mime',index:'mime', width:40, formatter: mimeTypeFormatter, align: 'center'}],
	    pager: jQuery('#filespager'),
	    rowNum:10,
	    rowList:[10,20,30,50],
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    imgpath: 'css/basic/images',
	    caption: 'Pliki',
	    width: 450,
	    height: 200,
	    loadComplete: function(){ 
		    var ids = jQuery("#fileslist").getDataIDs(); 
		    for(var i=0;i<ids.length;i++){ 
			    var cl = ids[i];
			    be = "<a href=\"Metadata.action?operation=view&fileId="+ids[i]+"\"><img src=\"gfx/find.png\" border=\"0\"/></a>";
			    jQuery("#fileslist").setRowData(ids[i],{act:be}) 
			} 
		}
	    })
	});

	mimeTypeFormatter = function(el, cellval, opts){ 
		var val = cellval.toString();
		var formatted = "<img src=\"gfx/mimetypes/"+val.replace("/","-")+".png\" border=\"0\" title=\"Nowy\"/>";
		
		
		$(el).html(formatted); 
	};

</script>

<s:url action="ProjectItems" id="newNote">
	<s:param name="operation">newnote</s:param>
	<s:param name="projectId">
		<s:property value="projectId" />
	</s:param>
</s:url>
<s:url action="ProjectItems" id="importFile">
	<s:param name="operation">importProjectFile</s:param>
	<s:param name="projectId">
		<s:property value="projectId" />
	</s:param>
</s:url>


<table width="100%" border="0">
	<s:hidden name="projectId"/>

	<tr>
		<td colspan="2" align="center" style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px">
			<s:if test="project.currentProjectStatus == 'ACCEPTED'">
				<s:a href="%{newNote}"><img src="gfx/projectitems/document-new.png" border="0" title="Nowa notatka"/></s:a>
				<s:a href="%{importFile}"><img src="gfx/projectitems/document-import.png" border="0" title="Importuj plik"/></s:a>
			</s:if>
		</td>
	</tr>

	<tr>
		<td colspan="2" style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px">
			<b>Notatki:</b>
		</td>
	</tr>
	
	<tr>
		<td colspan="2">
		<table id="noteslist" class="scroll" width="100%"></table>
		<div id="notespager" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
	
	<tr>
		<td colspan="2" style="border-bottom-style:solid;border-bottom-color:black;border-bottom-width:1px;border-top-style:solid;border-top-color:black;border-top-width:1px">
			<b>Pliki:</b>
		</td>
	</tr>
	
	
	<tr>
		<td colspan="2">
		<table id="fileslist" class="scroll" width="100%"></table>
		<div id="filespager" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>