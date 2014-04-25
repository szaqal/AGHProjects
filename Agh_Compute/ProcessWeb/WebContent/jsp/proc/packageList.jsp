<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
jQuery(document).ready(function(){
	
	
	  jQuery("#list").jqGrid({
	    url:'AjaxPackagesList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Poprawny','Tytul','Data dodania','contentId'],
	    colModel :[    
		  {name:'validated', index:'validated', width:40, formatter: validationFormatter},
	      {name:'title', index:'title', width:120}, 
	      {name:'addDate', index:'addDate', width:120},
	      {name:'contentId', index:'contentId', width:120, hidden:true}],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'id',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Pakiety obliczen',
	    imgpath: 'css/steel/images',
	    height: 335,
		width:525,
		onSelectRow: function(id){ 
	      loadDetails(id);
	    },
	    gridComplete: function(){ 
		   var ids = jQuery("#list").jqGrid('getDataIDs'); 
		   for(var i=0;i<ids.length;i++){ 
		   	jQuery("#list").jqGrid('setRowData',ids[i],{});
		   }
	    }    
	  });
	  jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});

	  function validationFormatter(cellval, options, rowObject) {
		  	var val = cellval.toString();
			var formatted;
		    if (val === 'true')
		    	formatted =  "<img src='/procc/gfx/apply.png' alt='true'/>";
		    else
		    	formatted =  "<img src='/procc/gfx/cancel.png' alt='false'/>";
		   	return formatted;
		  }
	  	  
	  
	  function loadDetails(id) {
			$.ajax({
				  url: "AjaxPackagesDetails.action",
				  data: "id="+id,
				  cache: false,
				  success: function(response){
						var pkg=$.parseJSON(response);
						addInfo(pkg, id);
				  }
			});
						  
		  }

	  function addInfo(pkg, id) {
		  $("#details").empty();
		  $("#details").append(
				  "<b>Nazwa : </b>"+pkg[0]+"<BR/><b>Data dodania : </b>"+pkg[1]
			);

		  var url = $("#downloadLink").attr("href");
		  var createUrl = $("#createProcessLink").attr("href");
		  var validateUrl = $("#validateLink").attr("href");

		  if(url.indexOf('fileId')!==-1) {
			  url=url.substring(0, url.indexOf('fileId'));
		  } 

		  if(createUrl.indexOf('computationPackageId')!==-1) {
			  createUrl=createUrl.substring(0, createUrl.indexOf('computationPackageId'));
		  } 

		  if(validateUrl.indexOf('computationPackageId')!==-1) {
			  validateUrl=validateUrl.substring(0, validateUrl.indexOf('computationPackageId'));
		  }

		  $("#downloadLink").attr("href", url+"fileId="+pkg[3]);
		  $("#createProcessLink").attr("href", createUrl +"computationPackageId="+id);
		  $("#validateLink").attr("href", validateUrl +"computationPackageId="+id);
  
	  }

	    
}); 
</script>	


<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="process.packages.list"/>
	</div>
	<div class="content-panel-list">
		<div id="gridDiv" style="font-size: small">		
			<table id="list" class="scroll"></table>
		<div id="pager" class="scroll" style="text-align: center;"></div>
		</div>
	</div>
</div>


<div class="details-panel">
	<div class="title-panel">
	 	<s:text name="process.detail"/>
	</div>
	<div class="content-panel-list">
		<a href="Download.action?fileId=" id="downloadLink" onclick="return checkLink(this, 'Nie wybrano pakietu','fileId');">
			<s:text name="common.get"/>
		</a>|
		<a href="CreateProcess.action?computationPackageId=" id="createProcessLink" onclick="return checkLink(this, 'Nie wybrano pakietu','computationPackageId');">
			<s:text name="process.create"/>
		</a> |

		<a href=ValidateProcess?computationPackageId=" id="validateLink" onclick="return checkLink(this, 'Nie wybrano konfiguracji','computationPackageId');">
			<s:text name="common.validate"/>
		</a>
		
		<div id="details">
		</div>
	</div>

</div>
