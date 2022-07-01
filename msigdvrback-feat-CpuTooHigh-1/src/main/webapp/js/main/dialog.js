/*
 *   version 1.0
 *    author derek_chang
 *      time 2007/12/03  
 */
function openWindow(url,title,width,height,topPix,leftPix){    

    var parentWin = parent.window;
    var cur=0;
	while( cur < 10 && !parentWin.getTitle ){
		parentWin = parentWin.parent.window;
		cur++;
	}
    parentWin.setTitle(title);
    //var dialogFrame = parentWin.document.getElementById('dialogFrame');
    var dialogFrame = parentWin.createIFrame();
    var maskFrame = parentWin.document.getElementById('maskFrame');   
    //parentWin.frames['dialogFrame'].location.href=url;
    //parentWin.document.getElementById("test").innerHTML+=parentWin.frames['dialogFrame'].location.href+"<BR/>"
    //add url judge
    var maxIndex = url.length;
    if(url.substring(maxIndex-5,maxIndex)=='.html' || url.substring(maxIndex-4,maxIndex)=='.jsp'){
	 dialogFrame.src=[url,"?curTimeStamp="+new Date().getTime()].join('');
    }else{
      	dialogFrame.src=[url,"&curTimeStamp="+new Date().getTime()].join('');
    }
    //dialogFrame.src=[url,"&dialogTime=",new Date().getTime()].join('');  
    maskFrame.style.zIndex= 99;
    maskFrame.style.top= 0+"px";
    maskFrame.style.left= 0+"px";
    maskFrame.style.width= screen.width-20+"px";
    maskFrame.style.height= screen.height-50+"px";
    maskFrame.style.display="block";
    dialogFrame.style.display="block";    
    dialogFrame.style.zIndex=100;   
    if(leftPix || leftPix==0){
    	dialogFrame.style.left= leftPix+'px';
    }else{  
        dialogFrame.style.left= screen.width/2-width/2-15+"px";   
    } 
    if(topPix || topPix==0 ){
    	dialogFrame.style.top= topPix+'px';
    }else{  
        dialogFrame.style.top= 100+"px";   
    }     
    dialogFrame.style.width= width+"px";
    dialogFrame.style.height= height+"px";      
}

function openSubWindow(url,title,width,height,topPix,leftPix){
	  var parentWin = parent.window;
    var cur=0;
	while( cur < 10 && !parentWin.getTitle ){
		parentWin = parentWin.parent.window;
		cur++;
	}
    parentWin.setTitle(title);
    var dialogFrame = parentWin.createSubIFrame();  
    var maxIndex = url.length;
    if(url.substring(maxIndex-5,maxIndex)=='.html' || url.substring(maxIndex-4,maxIndex)=='.jsp'){
	 dialogFrame.src=[url,"?curTimeStamp="+new Date().getTime()].join('');
    }else{
      	dialogFrame.src=[url,"&curTimeStamp="+new Date().getTime()].join('');
    }
    dialogFrame.style.display="block";    
    dialogFrame.style.zIndex=105;   
    if(leftPix || leftPix==0){
    	dialogFrame.style.left= leftPix+'px';
    }else{  
        dialogFrame.style.left= screen.width/2-width/2-15+"px";   
    } 
    if(topPix || topPix==0 ){
    	dialogFrame.style.top= topPix+'px';
    }else{  
        dialogFrame.style.top= 100+"px";   
    }     
    dialogFrame.style.width= width+"px";
    dialogFrame.style.height= height+"px";  
}

function closeSubWindow(){	 
	window.onbeforeunload = null;
	var parentWin = parent.window;
	var cur=0;
	while( cur < 10 && !parentWin.getTitle ){
		parentWin = parentWin.parent.window;
		cur++;
	}
	parentWin.removeSubIFrame();
}

function closeWindow(){	 
	window.onbeforeunload = null;
	var parentWin = parent.window;
	var cur=0;
	while( cur < 10 && !parentWin.getTitle ){
		parentWin = parentWin.parent.window;
		cur++;
	}
	parentWin.removeIFrame();
}
function getParentWindow(){
    var parentWin = parent.window;
	var cur=0;
	while( cur < 10 && !parentWin.getTitle ){
		parentWin = parentWin.parent.window;
		cur++;
	}
    return parentWin.document.getElementById('workAreaFrame').contentWindow;
}
// get parent window of second subwin
function getSubParentWindow(){ 
	var parentWin = parent.window;
	var cur=0;
	while( cur < 10 && !parentWin.getTitle ){
		parentWin = parentWin.parent.window;
		cur++;
	}   
    return parentWin.document.getElementById('dialogFrame').contentWindow;
}

function initFrame(){
   addHandle(document.getElementById ('headerList'), window);     
   document.getElementById('headerList').style.width=document.body.offsetWidth+'px';    
   document.getElementById('toolBarTitle').innerHTML=parent.window.getTitle();	
}
