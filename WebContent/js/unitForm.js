// Here's defined the formId - implementation is in common.js

document.write("<script type='text/javascript' src='js/common.js'></script>");

var formId = "unitForm";



function onLoadUnitForm(){
	if (isComingFromReport()){
		disableForm();
		$(".datePicker").datepicker('disable');
	    document.getElementById('cancelUnit').disabled=false;
	    document.getElementById('mode').disabled=false;
	    document.getElementById('origin').disabled=false;
	} else {
		toggleTypeRelatedWidgets();
	}
}


function isComingFromReport(){
	var origin = document.getElementById('origin').value;
	return origin == "unitReport";
}


function toggleTypeRelatedWidgets(typeId){
	var typeId = document.getElementById('typeId').value;

	var isDisabled = (typeId) ? false : true;

    document.getElementById('bossId').disabled=isDisabled;
    document.getElementById('addSubOridinateUnit').disabled=isDisabled;
}



function disableForm(){ 
	var i;
    for (i=0;i<document.forms.unitForm.elements.length;i++){ 
        document.forms.unitForm[i].disabled=true; 
    } 
}  




function changeType(id){
	storeAndSubmit(id, "changeType");
}



