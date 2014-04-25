<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
jQuery(document).ready(function(){
	
	  jQuery("#list").jqGrid({
	    url:'AjaxNodesList.action',
	    datatype: 'json',
	    mtype: 'GET',
	    colNames:['Adres','port','JMX port','OS','Arch'],
	    colModel :[    
		  {name:'inetAddr', index:'inetAddr', width:120},
		  {name:'operationPort', index:'operationPort', width:120},
		  {name:'port', index:'port', width:120},
	      {name:'host', index:'host', width:120},
		  {name:'arch', index:'arch', width:120}
		],
	    rowNum:20,
		rowList:[10,20,30,50],    
	    pager: jQuery('#pager'),
	    sortname: 'inetAddr',
	    sortorder: "desc",
	    viewrecords: true,
	    caption: 'Uzytkownicy',
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

	  function loadDetails(id) {
		$.ajax({
			  url: "NodeDetails.action",
			  data: "id="+id,
			  cache: false,
			  success: function(response){
				var node=$.parseJSON(response);
		  		addInfo(node,id);
			  }
		});
					  
	  }

	  function addInfo(node,id) {
		  $("#details").empty();
		  $("#details").append("<a href=\"#\" id=\"showDetails\">Historia obciazenia procesora</a>");
		  $("#detailsModal").jqm({ajax: 'NodeLoadChart.action?type=process&nodeIp='+id, trigger: '#showDetails'});
		  $("#details").append(" | ");
		  $("#details").append("<a href=\"#\" id=\"showMemDetails\">Historia uzycia pamieci</a>");
		  $("#detailsModalMem").jqm({ajax: 'NodeLoadChart.action?type=memory&nodeIp='+id, trigger: '#showMemDetails'});
		  $("#details").append(" | ");
		  $("#details").append("<a href=\"#\" id=\"showOperations\">Dostepne operacje</a><br/>");
		  $("#operationsModal").jqm({ajax: 'NodeOperations.action?nodeIp='+id, trigger: '#showOperations'});
		  $("#details").append("<b>IP : </b>"+node[0]+"<br/>");
		  $("#details").append("<b>JMX port : </b>"+node[1]+"<br/>");
		  $("#details").append("<b>OS : <b>"+node[2]+"<br/>");
		  $("#details").append("<b>Architektura : </b>"+node[3]+"<br/>");
		  $("#details").append("<b>Wirtualna maszyna : </b>"+node[4]+"<br/>");
		  $("#details").append("<b>Dostawca maszyny : </b>"+node[5]+"<br/>");
		  $("#details").append("<b>Liczba procesorow : </b>"+node[6]+"<br/>");
		  $("#details").append("<b>Pamiec calkowita : </b>"+node[7]+"<br/>");
		  $("#details").append("<b>Pamiec wolna : </b>"+node[8]+"<br/>");
		  $("#details").append("<b>Ilosc watkow : </b>"+node[9]+"<br/>");
		  $("#details").append("<b>Obciazenie systemu : </b>"+node[10]+"<br/>");
	  }

	    
}); 
</script>


<div class="list-panel">
	<div class="title-panel">
	 	<s:text name="nodes.list"/>
	</div>
	<div class="content-panel-list">
		<div id="gridDiv" style="font-size: small">		
			<table id="list" class="scroll"></table>
		<div id="pager" class="scroll" style="text-align: center;"></div>
		</div>
	</div>
</div>

<div class="jqmWindow" id="detailsModal"></div>
<div class="jqmWindow" id="detailsModalMem"></div>
<div class="jqmWindow" id="operationsModal"></div>
<div class="details-panel">
	<div class="title-panel">
	 	<s:text name="node.detail"/>
	</div>
	<div class="content-panel-list">
		<div id="details">
			
		</div>
	</div>
</div>

