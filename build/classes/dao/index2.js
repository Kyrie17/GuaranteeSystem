	function $(id) {
		return document.getElementById(id);
	}
	window.onload = showSlides;

	// 播放幻灯片
	var slideIndex = 0;

	function showSlides() {
		var i;
		var slides = document.getElementsByClassName("p");
		for (i = 0; i < slides.length; i++) {
			slides[i].style.display = "none";
		}
		slideIndex++;
		if (slideIndex > slides.length) {
			slideIndex = 1
		}
		slides[slideIndex - 1].style.display = "block";
		setTimeout(showSlides, 2000);
}