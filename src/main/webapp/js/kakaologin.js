const kakao = document.getElementById("kakao");
kakao.addEventListener("click", (e) => {
    e.preventDefault();
    kakao.setAttribute("href", "kauth.kakao.com/oauth/authorize?client_id=47aebbe977c3814499235ae9556522cc&redirect_uri=http://localhost:8081/kakaologin&response_type=code HTTP/1.1");
    kakao.click();
    console.log("aa");
})