async function checkPwd(pwdData) {
    try {
        const url = "/userCtrl/checkPwd";
        const config = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(pwdData)
        };
        const resp = await fetch(url, config);
        return await resp.text();
    } catch (error) {
        console.log(error);
    }
}

async function changePwd(pwdData) {
    try {
        const url = "/userCtrl/checkPwd";
        const config = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
            },
            body: JSON.stringify(pwdData)
        };
        const resp = await fetch(url, config);
        return await resp.text();
    } catch (error) {
        console.log(error);
    }
}

document.querySelector(".submit_btn").addEventListener("click", (e) => {
    e.preventDefault();
    if (document.getElementById("newPwd").value != document.getElementById("newPwdConfirm").value) {
        alert("새로운 비밀번호가 일치하지 않습니다.");
        return false;
    } else {
        let pwdData = {
            email: document.getElementById("email").value,
            pwd: document.getElementById("pwd").value,
        };
        checkPwd(pwdData).then(result => {
            if (parseInt(result) > 0) {
                document.getElementById("form").submit();
                // let pwdData = {
                //     email: document.getElementById("email").value,
                //     newPwd: document.getElementById("newPwd").value,
                // };
                // changePwd(pwdData)
            } else {
                alert("기존 비밀번호가 틀렸습니다.")
            }
        })
    }
    
})