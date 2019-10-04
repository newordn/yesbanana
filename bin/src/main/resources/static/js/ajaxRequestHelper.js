// get Data from the form and return a json object of that data
let getData = (form)=>
{
	let jsonData={};
	if(form!=null)
	{ 
		for(let input of form.elements)
		{
			let attribute = input.getAttribute("name");
			let value = input.value; 
			if(input!=null&&attribute!=null)
			jsonData[attribute] = value;

		}
	}
	return jsonData;
};

// to make a request to this url with this data(data in json format) with this method
let request = (url,method,data) =>
{
  // Return a new promise.
  return new Promise((resolve, reject)=>
   {
    // instanciation of the httpRequest object 
    var req = new XMLHttpRequest();
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    // receiving the response
    req.onload = () =>
     {
	      // so check the status
	      if (req.status == 200) {
	        // Resolve the promise with the response text
	        resolve(req.response);
	      }
	      else {
	        // Otherwise reject with the status text
	        // which will hopefully be a meaningful error
	        reject(Error(req.statusText));
	      }
    };
    // Handle network errors
    req.onerror=()=> {
      reject(Error("Network Error"));
    };
    console.log(JSON.stringify(data));
    // Make the request
    req.send(JSON.stringify(data));
  });
};
export {getData,request};