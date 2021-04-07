function showElevatedOptions(elevated) {
	if(elevated == 0) {
		document.getElementById("createEmployee").style.visibility = 'hidden';
		document.getElementById("salesReport").style.display = 'hidden';
		document.getElementById("cashierReport").style.display = 'hidden';
	}
	if(elevated == 1) {
		document.getElementById("createEmployee").style.visibility = 'visible';
		document.getElementById("salesReport").style.visibility = 'visible';
		document.getElementById("cashierReport").style.visibility = 'visible';
	}
}