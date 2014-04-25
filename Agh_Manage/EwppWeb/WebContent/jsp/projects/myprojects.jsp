<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	jQuery(document).ready(function(){ 

		$("#tabs").tabs({
			collapsible: true
		});

	    $("#activelist").jqGrid({
	    url:'AjaxMyProjectsList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Akcja','Tytul','Status'],
	    colModel :[ 	
		  {name:'act',index:'act', width:75,sortable:false},     
		  {name:'title',index:'title', width:400},
		  {name:'status',index:'status', width:100, formatter:projectStatusFormatter}],
	    pager: jQuery('#activepager'),
	    rowNum:30,
	    rowList:[10,20,30,50],
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    imgpath: 'css/basic/images',
	    caption: 'Projekty',
	    height: 400,
	    width: 450,
	    loadComplete: function(){ 
		    var ids = jQuery("#activelist").getDataIDs(); 
		    for(var i=0;i<ids.length;i++){ 
			    var cl = ids[i];
			    be = "<a href=\"ManageProject.action?operation=edit&projectId="+ids[i]+"\"><img src=\"gfx/edit-find-replace.png\" border=\"0\"/></a>";
			    jQuery("#activelist").setRowData(ids[i],{act:be}) 
			} 
		}
		    
	  }); 


	    $("#closedlist").jqGrid({
		    url:'AjaxMyClosedProjectsList.action',
		    datatype: 'json',
		    mtype: 'GET',
		    colNames:['Akcja','Tytul','Status'],
		    colModel :[ 	
			  {name:'act',index:'act', width:75,sortable:false},     
			  {name:'title',index:'title', width:400},
			  {name:'status',index:'status', width:100, formatter:projectStatusFormatter}],
		    pager: jQuery('#closedpager'),
		    rowNum:30,
		    rowList:[10,20,30,50],
		    sortname: 'id',
		    sortorder: "desc",
		    viewrecords: true,
		    imgpath: 'css/basic/images',
		    caption: 'Projekty',
		    height: 400,
		    width: 450,
		    loadComplete: function(){ 
			    var ids = jQuery("#closedlist").getDataIDs(); 
			    for(var i=0;i<ids.length;i++){ 
				    var cl = ids[i];
				    be = "<a href=\"ManageProject.action?operation=edit&projectId="+ids[i]+"\"><img src=\"gfx/edit-find-replace.png\" border=\"0\"/></a>";
				    jQuery("#closedlist").setRowData(ids[i],{act:be}) 
				} 
			}
			    
		  }); 	  

	  
	});

	 
	projectStatusFormatter = function(el, cellval, opts){ 
		var val = cellval.toString();
		var formatted;
		if ( val == "REQUESTED" ) {
			formatted = "<img src=\"gfx/projects/system-hybernate.png\" border=\"0\" title=\"Nowy\"/>";
		} else if(val == "ACCEPTED") {
			formatted =	"<img src=\"gfx/projects/system-switch-user.png\" border=\"0\" title=\"Zaakceptowany\"/>";
		} else if(val == "FINISHED") {
			formatted =	"<img src=\"gfx/projects/stop.png\" border=\"0\" title=\"Zakonczony\"/>";
		}
		
		$(el).html(formatted); 
	};


	var timeoutHnd; 
	var flAuto = false; 
	function doSearch(ev, txtField){ 
		if(!flAuto) return; 
		if(timeoutHnd) 
			clearTimeout(timeoutHnd) ;



		var typedTextField = $(txtField).attr('id');
		if(typedTextField == "txtActiveTitle") {
			timeoutHnd = setTimeout(gridRealoadActive,500);
		} else if (typedTextField == "txtClosedTitle") {
			timeoutHnd = setTimeout(gridReloadClosed,500);
		}
		timeoutHnd = setTimeout(gridReload,500);
	} 
	
	function gridReloadClosed() {
		var title = $("#txtClosedTitle").val();
		$("#closedlist").setGridParam( {
			url : "AjaxMyClosedProjectsList.action?title=" + title,
			page : 1
		}).trigger("reloadGrid");
	}

	function gridRealoadActive() {
		var title = $("#txtActiveTitle").val();
		$("#activelist").setGridParam( {
			url : "AjaxMyProjectsList.action?title=" + title,		
			page : 1
		}).trigger("reloadGrid");
	}

	
	function enableAutosubmit(state, senderChbox) {
		var selectedCheckbox = $(senderChbox).attr('id');
		if(selectedCheckbox == "activeTitle") {
			flAuto = state;
			$("#submitButtonActive").attr("disabled", state);
		} else if (selectedCheckbox == "closedTitle") {
			flAuto = state;
			$("#submitButtonClosed").attr("disabled", state);
		}
		
	}
	
</script>
<table width="100%">
	<tr>
		<td>
		<h3>Moje projekty</h3>
		</td>
		<td><img src="gfx/applications-science.png" /></td>
	</tr>
	<tr>
		<s:url value="EditProject?operation=new" id="editproject"/>
		<td colspan="2" style="border-bottom-style:solid; border-bottom-width:1px" align="center"><s:a href="%{editproject}"><img border="0" src="gfx/stock_convert.png"/>Nowy projekt</s:a></td>
	</tr>
	
	<tr>
		<td colspan="2" style="font-size:small">
		
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">Projekty aktywne</a></li>
						<li><a href="#tabs-2">Projekty zakonczone</a></li>
					</ul>
					
					<div id="tabs-1">
						<div>  
							<input type="checkbox" id="activeTitle" onclick="enableAutosubmit(this.checked,this)"> Autowyszukiwanie <br/> 
							Tytul : 
							<input type="text" id="txtActiveTitle" onkeydown="doSearch(arguments[0]||event, this)" /> 
			 				<button onclick="gridRealoadActive()" id="submitButtonActive" style="margin-left:30px;">Szukaj</button> 
						</div> 
						<table id="activelist" class="scroll"></table>
						<div id="activepager" class="scroll" style="text-align: center;"></div>
					</div>
					
					<div id="tabs-2">
						<div>  
							<input type="checkbox" id="closedTitle" onclick="enableAutosubmit(this.checked,this)"> Autowyszukiwanie <br/> 
							Tytul :  
							<input type="text" id="txtClosedTitle" onkeydown="doSearch(arguments[0]||event, this)" /> 
			 				<button onclick="gridReloadClosed()" id="submitButtonClosed" style="margin-left:30px;">Szukaj</button> 
						</div>
						<table id="closedlist" class="scroll"></table>
						<div id="closedpager" class="scroll" style="text-align: center;"></div>
					</div>
				
				</div>
		
		</td>
	</tr>

</table>