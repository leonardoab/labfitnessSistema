var campos = new Array();// Função que adiciona os campos que vão receber o focofunction 
addCampos(nome){campos[campos.length] = nome;}// Função que trata o evento do teclado.(Quando se clica no enter)
function enter(evt){var ret = new Boolean(true);   
var tecla = (navigator.appName == 'Netscape') ? evt.keyCode : window.event.keyCode;   
var nome = (navigator.appName == 'Netscape')?evt.target.name: event.srcElement.name;   
var type = (navigator.appName == 'Netscape')?evt.target.type: event.srcElement.type;   
if(tecla == 13){	   
	if(type == "button") return true;	   
	ret = nextCampo(nome);	   
	return ret;   
	}   
	return ret;}// Função que passa o foco para o próximo campo.
	function nextCampo(nome){
		for(i=0; i<campos.length;i++){
			if(campos[i]== nome){		   
			if(i==campos.length-1){			   
				obj = eval('document.forms[0].'+campos[0]);			   
				obj.focus();			   break;		   
				} else {			   
					obj = eval('document.forms[0].'+campos[i+1]);			   
					obj.focus();			   break;		   
					}	   
			}   
		}   
		return false;}// atribuição do manipulador ao evento
		if(navigator.appName=="Netscape") document.onkeypress = enter;
		else document.onkeydown = enter;