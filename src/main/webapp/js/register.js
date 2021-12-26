async function checkEmailExist(email) {
    try {
        const resp = await fetch(`/userCtrl/checkEmail?email=${email}`);
        return await resp.text();
    } catch (error) {
        console.log(error);
    }
}

document.getElementById("checkEmail").addEventListener("click", ()=> {
    let email = document.getElementById("email").value;
    
    checkEmailExist(email).then(result => {
        if (result > 0) {
            alert("이미 존재하는 이메일입니다.")
        } else {
            alert("사용가능한 이메일입니다.")
        }
    })
})