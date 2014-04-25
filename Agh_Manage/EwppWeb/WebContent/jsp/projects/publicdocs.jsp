<script type="text/javascript">
jQuery(document).ready(function(){ 
	  jQuery("#fileslist").jqGrid({
	    url:'AjaxPublicDocs.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Akcja','Tytul','Mime'],
	    colModel :[ 	
		  {name:'act',index:'act', width:40,sortable:false},     
	      {name:'title', index:'title', width:120},
	      {name:'mime',index:'mime', width:40, formatter: mimeTypeFormatter, align: 'center'}],
	    pager: jQuery('#filespager'),
	    rowNum:30,
	    rowList:[10,20,30,50],
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    imgpath: 'css/basic/images',
	    caption: 'Pliki',
	    width: 450,
	    height: 400,
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


	var timeoutHnd; 
	var flAuto = false; 
	function doSearch(ev){ 
	
		if(!flAuto) return; 
		if(timeoutHnd) 
			clearTimeout(timeoutHnd) ;
		timeoutHnd = setTimeout(gridReload,500);
	} 

	function gridReload() {
		var title = $("#txtTitle").val();
		$("#fileslist").setGridParam( {
			url : "AjaxPublicDocs.action?title=" + title,
			page : 1
		}).trigger("reloadGrid");
	}


	function enableAutosubmit(state) {
		flAuto = state;
		$("#submitButton").attr("disabled", state);
	}

</script>

<table width="100%" border="0">
	<tr>
		<td colspan="2">
		<div>
			<input type="checkbox" id="autosearch" onclick="enableAutosubmit(this.checked)"> Autowyszukiwanie <br/>  
			Tytul: <input type="text" id="txtTitle" onkeydown="doSearch(arguments[0]||event)" /> 
			<button onclick="gridReload()" id="submitButton" style="margin-left:30px;">Szukaj</button>
		</div>
		<table id="fileslist" class="scroll" width="100%"></table>
		<div id="filespager" class="scroll" style="text-align: center;"></div>
		</td>
	</tr>
</table>