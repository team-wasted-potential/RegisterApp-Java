function SignInValidation() {
  // Storing field values in variables
  var employeeID = document.getElementById("employeeID").value;
  var employeePassword = document.getElementById("employeePassword").value;

  // Conditions to check user input on sign in page
  // Checks if employeeID is number and not blank AND checks if employeePassword is not blank
  if ( ( (isNaN(employeeID) == false) && (employeeID != '') ) && (employeePassword != '') ) {
      alert("All type of validation has done on OnSubmit event.:");
      return true;
  }

  // If password was blank
  else if (employeePassword == '') {
      alert("Password was left empty.");
      return false;
  }  

  // If ID was blank
  else if (employeeID == '') {
      alert("ID was left empty.");
      return false;
  }

  // If ID was not a number
  else if (isNaN(employeeID) == true)
  {
      alert("ID was not a number.");
      return false;
  }
}
