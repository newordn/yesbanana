
	// for the name
	let name = (nameVal) =>
	{
		let regEx = new RegExp("^.{2,}$");
		return regEx.test(nameVal);	
	}

 	// for the email
	let email = (emailVal)=>
	{
		let regEx = new RegExp("^[a-z0-9_\.-]+@[\da-z\.-]+[\.][a-z\.]{2,6}$");
		return regEx.test(emailVal);
	}

/*
	// for the number
	number()
	{
		let regEx = new RegExp("^[0-9]{8,9}$");
	
		if(this.numberInput!=null)
		return this.isOk[2]=regEx.test(this.numberInput.value);
		return true;
	}
	// for the password(at least one lowerCase,one UpperCase,one digit and 8 lenght min)
	passwordStrong()
	{
		let regEx = new RegExp("^((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]))(?=.{8,})");
		
		if(this.passwordInput!=null)
		return this.isOk[3]=regEx.test(this.passwordInput.value);
		return true;
	}
*/
// for the password(at least 6 lenghts min)
let password = (passwordVal)=>
{
	let regEx = new RegExp("^.{6,}$");
	return regEx.test(passwordVal);

}
 export {name,email,password};