var AjaxTreeView={
		config:{
			classLoading: "loading",//ajax loading css
			ontoggle	: null,//function(o,b) when toggle folder.o:li,b:is open
			onclick	: null,//function(o,a) when click text.o: li;a:a
			url:"ajaxTreeService.ajax",//ajax load url
			attrs:["treeId","treeParentId"]
		},
		toggle:function(o){//li
			var $o=$(o),c="expandable",b=$o.hasClass(c),$div=$o.children("div").first();
			if(b){//open
				var isl=!o.loading && !o.loaded && ($o.attr("treeId")!=null||$o.attr("treeParentId")!=null);
				if(isl){
					o.loading=true;
					AjaxTreeView.load(o);
				}
				$div.removeClass("expandable-hitarea");
				$o.removeClass(c);
				
				$div.addClass("collapsable-hitarea");
				$o.addClass("collapsable");
				if($o.hasClass("lastExpandable")){
					$o.removeClass("lastExpandable");
					$o.addClass("lastCollapsable");
				}
			}else{//close
				$div.removeClass("collapsable-hitarea");
				$o.removeClass("collapsable");
				
				$div.addClass("expandable-hitarea");
				$o.addClass(c);
				if($o.hasClass("lastCollapsable")){
					$o.removeClass("lastCollapsable");
					$o.addClass("lastExpandable");
				}
			}
			
			if(typeof(AjaxTreeView.config.ontoggle)=="function"){//call ontoggle
				return AjaxTreeView.config.ontoggle(o,b);
			}else{
				return false;
			}
		},
		load:function(o,func){//li,func
			if(o.loaded){
				return;
			}
			var $o=$(o),$span = $o.children("span").first();
			$span.attr("class",AjaxTreeView.config.classLoading);
			
			var pa=AjaxTreeView.getParams(o);
			$.post(AjaxTreeView.config.url,pa,
				function(d){
					if(d.length>0){
						//$(o.children[o.children.length-1]).after(d);//not good
						$o.append(d);
					}
					o.loaded=true;
					o.loading=false;
					$span.attr("class","folder");
					if(typeof(func)=="function"){
						func(o,d);
					}
				}
			,"html");
		},
		getParams:function(o){//eg. {suggest:txt}
			var n, v;
			var r={};
			for (var i = 0; i < AjaxTreeView.config.attrs.length; i++) {
				n = AjaxTreeView.config.attrs[i];
				v = o.getAttribute(n);
				if (v) {
					r[AjaxTreeView.config.attrs[i]]=v;
				}
			}
			var p=o.getAttribute("param");//eg a=1&b=2
			if(p!=null&&p!=""){
				var ar = p.split("&");
				for(var j=0;j<ar.length;j++){
					var nvr = ar[j].split("=");
					r[nvr[0]]=nvr[1];
				}
			}
			//console.log(r);
			return r;
		},
		click:function(a){
			if(typeof(AjaxTreeView.config.onclick)=="function"){//call onclick
				return AjaxTreeView.config.onclick(a.parentNode.parentNode,a);
			}
		},

		end:""
	};
	var $att = function(o){AjaxTreeView.toggle(o.parentNode);};
	var $atc = AjaxTreeView.click;