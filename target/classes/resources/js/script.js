
$(function() {
	$(PrimeFaces.escapeClientId('FolgaFormRelatorio:dataInicial')).datetimepicker({
		format:'d/m/Y',

    	
    	onShow:function( ct ){
 		   this.setOptions({
 			   maxDate:$(PrimeFaces.escapeClientId('FolgaFormRelatorio:dataFinal')).val()?
 					   $(PrimeFaces.escapeClientId('FolgaFormRelatorio:dataFinal')).val():false
 		   });
     	} 	

    	});
	
	$(PrimeFaces.escapeClientId('FolgaFormRelatorio:dataFinal')).datetimepicker({
		format:'d/m/Y',
		

    	onShow:function( ct ){
 		   this.setOptions({
 			minDate:$(PrimeFaces.escapeClientId('FolgaFormRelatorio:dataInicial')).val()?
 		    		$(PrimeFaces.escapeClientId('FolgaFormRelatorio:dataInicial')).val():false
 		   });
     	}

    	});
	
 });
