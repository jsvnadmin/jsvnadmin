function del(act){
	var ind = act.indexOf("?");
	if(ind == -1){
		go(act+"?act=del");
	}else{
		go(act+"&act=del");
	}
}
function go(act,target){
	var fm =document.getElementById("goForm");
	//clear
	var eles = fm.elements;
	if(eles != null && eles.length > 0){
		for(var i=0;i<eles.length;i++){
			fm.removeChild(eles[i]);
			i--;
		}
	}
	fm.target = "_self";
	//action?a=1&b=2
	var ind = act.indexOf("?");
	if(ind == -1){
		fm.action = act;
	}else{
		fm.action = act.substring(0,ind);
		var parms = act.substring(ind+1);
		if(parms !=null && parms.length > 0){
			var arr=parms.split("&");
			if(arr!=null && arr.length>0){
				for(var i=0;i<arr.length;i++){
					if(arr[i] == null || arr[i].length == 0){
						continue;
					}
					var pv = arr[i].split("=");
					if(pv == null || pv.length==0 || pv[0] == null){
						continue;
					}
					var inp = document.createElement("input");
					inp.setAttribute("type","hidden");
					inp.setAttribute("name",pv[0]);
					if(pv.length == 2){
						inp.setAttribute("value",pv[1]);
					}
					fm.appendChild(inp);
				}
			}
		}
	}
	//submit
	if(target){
		fm.target = target;
	}
	fm.submit();
}

document.write("<form id='goForm' name='goForm' action='#' method='post'></form>");