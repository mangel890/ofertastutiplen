


    //
    // As mentioned at http://en.wikipedia.org/wiki/XMLHttpRequest
    //
    if( !window.XMLHttpRequest ) XMLHttpRequest = function()
    {
      try{ return new ActiveXObject("Msxml2.XMLHTTP.6.0") }catch(e){}
      try{ return new ActiveXObject("Msxml2.XMLHTTP.3.0") }catch(e){}
      try{ return new ActiveXObject("Msxml2.XMLHTTP") }catch(e){}
      try{ return new ActiveXObject("Microsoft.XMLHTTP") }catch(e){}
      throw new Error("Could not find an XMLHttpRequest alternative.")
    };

    //
    // Makes an AJAX request to a local server function w/ optional arguments
    //
    // functionName: the name of the server's AJAX function to call
    // opt_argv: an Array of arguments for the AJAX function
    //
    function Request(function_name, opt_argv) {

    	  if (!opt_argv)
    	    opt_argv = new Array();
    	 
    	  // Find if the last arg is a callback function; save it
    	  var callback = null;
    	  var len = opt_argv.length;
    	  if (len > 0 && typeof opt_argv[len-1] == 'function') {
    	    callback = opt_argv[len-1];
    	    opt_argv.length--;
    	  }
    	  var async = (callback != null);
    	 
    	  // Build an Array of parameters, w/ function_name being the first
			// parameter
    	  var params = new Array(function_name);
    	  for (var i = 0; i < opt_argv.length; i++) {
    	    params.push(opt_argv[i]);
    	  }
    	  var body = JSON.stringify(params);

    	  // Create an XMLHttpRequest 'POST' request w/ an optional callback
			// handler
    	  var req = new XMLHttpRequest();
    	  req.open('POST', '/asyncCall', async);
    	 
    	  //req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    	  //req.setRequestHeader("Content-length", body.length);
    	  //req.setRequestHeader("Connection", "close");

    	  if (async) {
    	    req.onreadystatechange = function() {
    	      if(req.readyState == 4 && req.status == 200) {
    	        var response = null;
    	        try {
    	         response = JSON.parse(req.responseText);
    	        } catch (e) {
    	         response = req.responseText;
    	        }
    	        callback(response);
    	      }
    	    }
    	  }
    	 
    	  // Make the actual request
    	  req.send(body);
    	}
    
    // Adds a stub function that will pass the arguments to the AJAX call
    function InstallFunction(obj, functionName) {
      obj[functionName] = function() { Request(functionName, arguments); }
    }

    
    // Server object that will contain the callable methods
    var server = {};

    // Insert 'Add' as the name of a callable method
    InstallFunction(server, 'addVote');


    // Handy "macro"
    function $(id){
      return document.getElementById(id);
    }

    // Client function that calls a server rpc and provides a callback
    function addVote(key, value) {
      server.addVote(key, value, function (response) {onAddVoteSuccess(response, key)});
    }

    // Callback for after a successful doAdd
    function onAddVoteSuccess(response,key) {
    	 
    	var object = eval(response);

    	if (object[2]!=null)
    	{
    		alert(object[2]);
    		return;
    	}
    	
    	
    	str = "positiveVotes"+key;
    	document.getElementById(str).innerHTML = object[0];
    	str = "negativeVotes"+key;
    	document.getElementById(str).innerHTML = object[1];
    }

