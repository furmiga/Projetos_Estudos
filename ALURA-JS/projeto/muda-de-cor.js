var trs = document.getElementsByTagName("tr");
percorreArray(trs , function(tr){
	tr.addEventListener("dblclick" , function(){
		this.setAttribute("bgcolor" , "blue")
	});
});