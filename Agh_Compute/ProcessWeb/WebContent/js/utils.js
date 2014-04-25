var messageUtil = {
	"user_admin":"Administracja użytkownikami",
	"group_admin":"Administracja grupami użytkowników",
	"process_admin":"Administracja procesami"
}

function checkLink(link, message) {
	  return checkLink(link, message, 'uniqueId');
}

function checkLink(link, message, searchFor) {
	  var result = link.getAttribute('href').indexOf('&'+searchFor)!==-1 
	  			|| link.getAttribute('href').indexOf('?'+searchFor);
	  if(!result) {
		  alert(message);
	  }
	  return result;
}